package com.arisu_library.arisu_library_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.arisu_library.arisu_library_management.model.app.SimpleMap;
import com.arisu_library.arisu_library_management.model.request.AuthorRequestRecord;

public interface AuthorService {
    SimpleMap create(AuthorRequestRecord req);
    SimpleMap update(AuthorRequestRecord req);
    Page<SimpleMap> findAll(String name, String country, Pageable pageable);
    SimpleMap findById(String id);
    void deleteById(String id);
}
