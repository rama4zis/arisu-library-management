package com.arisu_library.arisu_library_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.arisu_library.arisu_library_management.entity.Book;

public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {

    Optional<Book> findByTitle(String title);

	Boolean existsByTitleAndAuthor_Id(String title,
			String authorId);
}