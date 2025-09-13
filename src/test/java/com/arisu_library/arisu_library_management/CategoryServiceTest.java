package com.arisu_library.arisu_library_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.arisu_library.arisu_library_management.model.request.CategoryRequestRecord;
import com.arisu_library.arisu_library_management.repository.UserRepository;
import com.arisu_library.arisu_library_management.service.CategoryService;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testCreateCategory() {
        CategoryRequestRecord newCategory = new CategoryRequestRecord(
                null,
                "Isekai",
                "Books that explore futuristic concepts and advanced technology");
        categoryService.create(newCategory);
    }
}
