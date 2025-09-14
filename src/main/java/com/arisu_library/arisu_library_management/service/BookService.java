package com.arisu_library.arisu_library_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.arisu_library.arisu_library_management.model.app.SimpleMap;
import com.arisu_library.arisu_library_management.model.enums.BookStatus;
import com.arisu_library.arisu_library_management.model.request.BookRequestRecord;

public interface BookService {
    SimpleMap create(BookRequestRecord req, MultipartFile coverImage);
    SimpleMap update(BookRequestRecord req, MultipartFile coverImage);
    Page<SimpleMap> findAll(String title, String authorName, String categoryName, BookStatus status, Pageable pageable);
    SimpleMap findById(String id);
    void deleteById(String id);
}
