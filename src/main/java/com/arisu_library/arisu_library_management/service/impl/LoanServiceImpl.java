package com.arisu_library.arisu_library_management.service.impl;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.arisu_library.arisu_library_management.entity.Book;
import com.arisu_library.arisu_library_management.entity.Loan;
import com.arisu_library.arisu_library_management.entity.Profile;
import com.arisu_library.arisu_library_management.entity.User;
import com.arisu_library.arisu_library_management.model.app.SimpleMap;
import com.arisu_library.arisu_library_management.model.enums.BookStatus;
import com.arisu_library.arisu_library_management.model.enums.LoanStatus;
import com.arisu_library.arisu_library_management.model.request.LoanRequestRecord;
import com.arisu_library.arisu_library_management.repository.BookRepository;
import com.arisu_library.arisu_library_management.repository.LoanRepository;
import com.arisu_library.arisu_library_management.repository.UserRepository;
import com.arisu_library.arisu_library_management.service.LoanService;
import com.arisu_library.arisu_library_management.service.ValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ValidationService validationService;

    @Override
    public SimpleMap create(LoanRequestRecord req) {
        validationService.validate(req);

        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getProfile() == null) {
            throw new RuntimeException("User must complete profile before loaning a book");
        }
        User currentUser = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("Current user not found"));

        // Ensure the user id is equal to the authenticated user's id or current user ADMIN Role
        Boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!currentUser.getId().equals(req.userId()) && !isAdmin) {
            throw new RuntimeException("You can only create loans for yourself");
        }

        Book book = bookRepository.findById(req.bookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Check book status
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new RuntimeException("Book is not available for loan");
        }

        LocalDate maxDue = req.loanDate().plusDays(7);
        if (req.dueDate().isAfter(maxDue)) {
            throw new RuntimeException("Due date exceeds maximum loan period of 7 days");
        }
        if (req.dueDate().isBefore(req.loanDate())) {
            throw new RuntimeException("Due date cannot be before loan date");
        }

        var entity = Loan.builder()
                .book(book)
                .user(user)
                .loanDate(req.loanDate())
                .dueDate(req.dueDate())
                .status(req.status())
                .build();
        entity = loanRepository.save(entity);

        // change book status 
        book.setStatus(BookStatus.BORROWED);
        bookRepository.save(book);

        return toMap(entity);
    }

    @Override
    public SimpleMap markReturned(String id) {
        var entity = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        if (entity.getReturnDate() == null) {
            entity.setReturnDate(LocalDate.now());
        }
        entity.setStatus(LoanStatus.RETURNED);
        loanRepository.save(entity);
        // change book status
        Book book = entity.getBook();
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);
        return toMap(entity);
    }

    @Override
    public Page<SimpleMap> findAll(String username, String title, LoanStatus status, Pageable pageable) {
        Specification<Loan> spec = (root, q, cb) -> null;
        if (username != null && !username.isBlank()) {
            spec = spec.and((root, q, cb) -> cb.like(cb.lower(root.join("user").get("username")), "%" + username.toLowerCase() + "%"));
        }
        if (title != null && !title.isBlank()) {
            spec = spec.and((root, q, cb) -> cb.like(cb.lower(root.join("book").get("title")), "%" + title.toLowerCase() + "%"));
        }
        if (status != null) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("status"), status));
        }
        return loanRepository.findAll(spec, pageable).map(this::toMap);
    }

    @Override
    public SimpleMap findById(String id) {
        var entity = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        return toMap(entity);
    }

    private SimpleMap toMap(Loan l) {
        Profile userProfile = l.getUser().getProfile();
        String fullName = userProfile.getFirstName() + " " + userProfile.getLastName();
        String email = l.getUser().getEmail();
        SimpleMap userProfileMap = SimpleMap.createMap()
                .add("id", userProfile.getId())
                .add("fullName", fullName)
                .add("email", email)
                .add("phone", userProfile.getPhone())
                .add("address", userProfile.getAddress());

        Book book = l.getBook();
        SimpleMap bookMap = SimpleMap.createMap()
                .add("id", book.getId())
                .add("title", book.getTitle())
                .add("author", book.getAuthor().getName())
                .add("publishedDate", book.getPublishedDate());

        return SimpleMap.createMap()
                .add("id", l.getId())
                .add("book", bookMap)
                .add("userProfile", userProfileMap)
                .add("username", l.getUser().getUsername())
                .add("loanDate", l.getLoanDate())
                .add("dueDate", l.getDueDate())
                .add("returnDate", l.getReturnDate())
                .add("status", l.getStatus().getLabel());
    }
}
