package com.arisu_library.arisu_library_management.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.arisu_library.arisu_library_management.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> {
    Optional<Category> findByName(String name);
}