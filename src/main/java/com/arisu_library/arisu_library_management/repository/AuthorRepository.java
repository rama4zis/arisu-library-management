package com.arisu_library.arisu_library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.arisu_library.arisu_library_management.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, String>, JpaSpecificationExecutor<Author> {}