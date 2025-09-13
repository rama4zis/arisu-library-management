package com.arisu_library.arisu_library_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.arisu_library.arisu_library_management.model.app.SimpleMap;
import com.arisu_library.arisu_library_management.model.request.CategoryRequestRecord;

public interface CategoryService {
    SimpleMap create(CategoryRequestRecord req);
    SimpleMap update(CategoryRequestRecord req);
    Page<SimpleMap> findAll(String name, Pageable pageable);
    SimpleMap findById(String id);
    void deleteById(String id);
}
