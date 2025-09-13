package com.arisu_library.arisu_library_management.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
import com.arisu_library.arisu_library_management.service.ValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final ValidationService validationService;

    @Override
    public SimpleMap create(BookRequestRecord req) {
        validationService.validate(req);

        Author author = authorRepository.findById(req.authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Category category = null;
        if (req.categoryId() != null) {
            category = categoryRepository.findById(req.categoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }

        var entity = Book.builder()
                .title(req.title())
                .summary(req.summary())
                .coverImage(req.coverImage())
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
    public SimpleMap update(BookRequestRecord req) {
        validationService.validate(req);
        var entity = bookRepository.findById(req.id())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Author author = authorRepository.findById(req.authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Category category = null;
        if (req.categoryId() != null) {
            category = categoryRepository.findById(req.categoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }

        entity.setTitle(req.title());
        entity.setSummary(req.summary());
        entity.setCoverImage(req.coverImage());
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
                .add("id", author != null ? author.getId() : null)
                .add("name", author != null ? author.getName() : null)
                .add("biography", author != null ? author.getBiography() : null)
                .add("dob", author != null ? author.getDob() : null)
                .add("country", author != null ? author.getCountry() : null)
                .add("website", author != null ? author.getWebsite() : null);
        Category category = this.categoryRepository.findById(b.getCategory().getId()).orElse(null);
        SimpleMap categoryMap = SimpleMap.createMap()
                .add("id", category != null ? category.getId() : null)
                .add("name", category != null ? category.getName() : null)
                .add("description", category != null ? category.getDescription() : null);
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
