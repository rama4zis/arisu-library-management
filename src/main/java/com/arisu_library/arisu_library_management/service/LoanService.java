package com.arisu_library.arisu_library_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.arisu_library.arisu_library_management.model.app.SimpleMap;
import com.arisu_library.arisu_library_management.model.enums.LoanStatus;
import com.arisu_library.arisu_library_management.model.request.LoanRequestRecord;

public interface LoanService {
    SimpleMap create(LoanRequestRecord req);
    SimpleMap markReturned(String id);
    Page<SimpleMap> findAll(String username, String title, LoanStatus status, Pageable pageable);
    SimpleMap findById(String id);
}
