package com.arisu_library.arisu_library_management;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.arisu_library.arisu_library_management.entity.Author;
import com.arisu_library.arisu_library_management.entity.Category;
import com.arisu_library.arisu_library_management.model.enums.BookStatus;
import com.arisu_library.arisu_library_management.model.request.BookRequestRecord;
import com.arisu_library.arisu_library_management.repository.AuthorRepository;
import com.arisu_library.arisu_library_management.repository.CategoryRepository;
import com.arisu_library.arisu_library_management.service.BookService;

@SpringBootTest
public class BookServiceTest {

    @Autowired private BookService bookService;
    @Autowired private AuthorRepository authorRepository;
    @Autowired private CategoryRepository categoryRepository;

    @Test
    public void testCreateBook() {
        var author = authorRepository.save(Author.builder().name("John Doe").build());

        Category category = null;
        // Check if category is created
        if (categoryRepository.findByName("Fiction").isPresent()) {
            category = categoryRepository.findByName("Fiction").get();
        } else {
            category = categoryRepository.save(Category.builder().name("Fiction").build());
        }

        var req = new BookRequestRecord(
            null,
            "Sample Book",
            "A summary",
            null,
            author.getId(),
            category.getId(),
            123,
            "EN",
            LocalDate.of(2024, 1, 1),
            BookStatus.AVAILABLE
        );

        bookService.create(req, null);
    }
}
