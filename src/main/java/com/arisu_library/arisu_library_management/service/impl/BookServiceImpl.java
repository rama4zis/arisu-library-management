package com.arisu_library.arisu_library_management.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.arisu_library.arisu_library_management.entity.Author;
import com.arisu_library.arisu_library_management.entity.Book;
import com.arisu_library.arisu_library_management.entity.Category;
import com.arisu_library.arisu_library_management.model.app.SimpleMap;
import com.arisu_library.arisu_library_management.model.enums.BookStatus;
import com.arisu_library.arisu_library_management.model.request.BookRequestRecord;
import com.arisu_library.arisu_library_management.repository.AuthorRepository;
import com.arisu_library.arisu_library_management.repository.BookRepository;
import com.arisu_library.arisu_library_management.repository.CategoryRepository;
import com.arisu_library.arisu_library_management.service.BookService;
import com.arisu_library.arisu_library_management.service.FileService;
import com.arisu_library.arisu_library_management.service.ValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final ValidationService validationService;
    private final FileService fileService;

    @Override
    public SimpleMap create(BookRequestRecord req, MultipartFile coverImage) {
        validationService.validate(req);

        // Check if book name already exists
        Boolean existingBook = bookRepository.existsByTitleAndAuthor_Id(req.title(), req.authorId());
        if (existingBook) {
            throw new RuntimeException("Book title already exists");
        }

        Author author = authorRepository.findById(req.authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Category category = null;
        if (req.categoryId() != null) {
            category = categoryRepository.findById(req.categoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }
        String fileName = req.title().trim().toLowerCase()+ "_cover_image";

        String pathCoverImage = null;
        if (coverImage != null) {
            pathCoverImage = fileService.uploadFile(fileName, coverImage);
        }

        var entity = Book.builder()
                .title(req.title())
                .summary(req.summary())
                .coverImage(pathCoverImage)
                .author(author)
                .category(category)
                .pages(req.pages())
                .language(req.language())
                .publishedDate(req.publishedDate())
                .status(req.status())
                .build();
        entity = bookRepository.save(entity);
        return toMap(entity);
    }

    @Override
    public SimpleMap update(BookRequestRecord req, MultipartFile coverImage) {
        validationService.validate(req);

        Boolean existingBook = bookRepository.existsByTitleAndAuthor_Id(req.title(), req.authorId());
        Book currentBook = bookRepository.findById(req.id())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (existingBook && !currentBook.getId().equals(req.id())) {
            throw new RuntimeException("Book title already exists");
        }

        var entity = bookRepository.findById(req.id())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Author author = authorRepository.findById(req.authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Category category = null;
        if (req.categoryId() != null) {
            category = categoryRepository.findById(req.categoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }
        String fileName = req.title().trim().toLowerCase()+ "_cover_image";

        String pathCoverImage = null;
        if (coverImage != null) {
            pathCoverImage = fileService.uploadFile(fileName, coverImage);
        }

        entity.setTitle(req.title());
        entity.setSummary(req.summary());
        entity.setCoverImage(pathCoverImage);
        entity.setAuthor(author);
        entity.setCategory(category);
        entity.setPages(req.pages());
        entity.setLanguage(req.language());
        entity.setPublishedDate(req.publishedDate());
        entity.setStatus(req.status());

        bookRepository.save(entity);
        return toMap(entity);
    }

    @Override
    public Page<SimpleMap> findAll(String title, String authorName, String categoryName, BookStatus status,
            Pageable pageable) {
        Specification<Book> spec = (root, q, cb) -> null;
        if (title != null && !title.isBlank()) {
            spec = spec.and((root, q, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if (authorName != null && !authorName.isBlank()) {
            spec = spec.and((root, q, cb) -> cb.like(cb.lower(root.join("author").get("name")),
                    "%" + authorName.toLowerCase() + "%"));
        }
        if (categoryName != null && !categoryName.isBlank()) {
            spec = spec.and((root, q, cb) -> cb.like(cb.lower(root.join("category").get("name")),
                    "%" + categoryName.toLowerCase() + "%"));
        }
        if (status != null) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("status"), status));
        }
        return bookRepository.findAll(spec, pageable).map(this::toMap);
    }

    @Override
    public SimpleMap findById(String id) {
        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return toMap(entity);
    }

    @Override
    public void deleteById(String id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found");
        }
        bookRepository.deleteById(id);
    }

    private SimpleMap toMap(Book b) {
        Author author = this.authorRepository.findById(b.getAuthor().getId()).orElse(null);
        SimpleMap authorMap = SimpleMap.createMap()
                .add("id", author.getId())
                .add("name", author.getName())
                .add("biography", author.getBiography())
                .add("dob", author.getDob())
                .add("country", author.getCountry())
                .add("website", author.getWebsite());
        Category category = this.categoryRepository.findById(b.getCategory().getId()).orElse(null);
        SimpleMap categoryMap = SimpleMap.createMap()
                .add("id", category.getId())
                .add("name", category.getName())
                .add("description", category.getDescription());
        return SimpleMap.createMap()
                .add("id", b.getId())
                .add("title", b.getTitle())
                .add("summary", b.getSummary())
                .add("coverImage", b.getCoverImage())
                .add("categoryId", b.getCategory().getId())
                .add("pages", b.getPages())
                .add("language", b.getLanguage())
                .add("publishedDate", b.getPublishedDate())
                .add("status", b.getStatus())
                .add("author", authorMap)
                .add("category", categoryMap);
    }
}
