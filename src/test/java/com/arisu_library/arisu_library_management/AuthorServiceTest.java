package com.arisu_library.arisu_library_management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.arisu_library.arisu_library_management.model.request.AuthorRequestRecord;
import com.arisu_library.arisu_library_management.service.AuthorService;

@SpringBootTest
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;


    @Test
    public void testCreateAuthor() {
        AuthorRequestRecord newAuthor = new AuthorRequestRecord(
                null,
                "Asep",
                "An acclaimed author of fantasy novels",
                null,
                "INDONESIA",
                null
                );
        authorService.create(newAuthor);
    }
}
