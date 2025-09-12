package com.arisu_library.arisu_library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.arisu_library.arisu_library_management.entity.Book;

public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {}