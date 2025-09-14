package com.arisu_library.arisu_library_management;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.arisu_library.arisu_library_management.entity.Author;
import com.arisu_library.arisu_library_management.entity.Category;
import com.arisu_library.arisu_library_management.entity.User;
import com.arisu_library.arisu_library_management.entity.Profile;
import com.arisu_library.arisu_library_management.model.enums.BookStatus;
import com.arisu_library.arisu_library_management.model.enums.Status;
import com.arisu_library.arisu_library_management.model.enums.Role;
import com.arisu_library.arisu_library_management.model.request.BookRequestRecord;
import com.arisu_library.arisu_library_management.model.request.LoanRequestRecord;
import com.arisu_library.arisu_library_management.repository.AuthorRepository;
import com.arisu_library.arisu_library_management.repository.CategoryRepository;
import com.arisu_library.arisu_library_management.repository.ProfileRepository;
import com.arisu_library.arisu_library_management.repository.UserRepository;
import com.arisu_library.arisu_library_management.service.BookService;
import com.arisu_library.arisu_library_management.service.LoanService;

import jakarta.transaction.Transactional;

@SpringBootTest
public class LoanServiceTest {

    @Autowired
    private LoanService loanService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    public void testCreateLoan() {
        var user = userRepository.save(User.builder()
                .username("borrower")
                .password("secret")
                .email("borrower@example.com")
                .role(Role.USER)
                .status(Status.ACTIVE)
                .build());

        // Create profile for user
        var profile = profileRepository.save(Profile.builder()
                .user(user)
                .firstName("Loan")
                .lastName("User")
                .address("Jalan Test")
                .city("Test c")
                .dob(LocalDate.of(1990, 1, 1))
                .phone("000")
                .build());
        user.setProfile(profile);
        userRepository.save(user);

        var author = authorRepository.save(Author.builder().name("Jane Author").build());

        // check category
        Category category = null;
        if (categoryRepository.findByName("Novel").isPresent()) {
            category = categoryRepository.findByName("Novel").get();
        } else {
            category = categoryRepository.save(Category.builder().name("Novel").build());
        }
        var bookMap = bookService.create(new BookRequestRecord(
                null,
                "Loanable Book",
                null,
                null,
                author.getId(),
                category.getId(),
                200,
                "EN",
                LocalDate.of(2023, 6, 1),
                BookStatus.AVAILABLE), null);
        String bookId = bookMap.get("id", String.class);

        var loanReq = new LoanRequestRecord(
                null,
                bookId,
                user.getId(),
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                com.arisu_library.arisu_library_management.model.enums.LoanStatus.ONGOING);

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        var loan = loanService.create(loanReq);
        assertNotNull(loan.get("id", String.class));
        assertEquals("Ongoing", loan.get("status", String.class));
    }
}
