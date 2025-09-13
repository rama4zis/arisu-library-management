package com.arisu_library.arisu_library_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.arisu_library.arisu_library_management.entity.User;
import com.arisu_library.arisu_library_management.model.app.SimpleMap;
import com.arisu_library.arisu_library_management.model.filter.UserFilterRecord;
import com.arisu_library.arisu_library_management.model.request.UserRequestRecord;

public interface UserService {
    User registerUser(UserRequestRecord req);
    User editUser(UserRequestRecord req);

    Page<SimpleMap> findAll(UserFilterRecord filterReq, Pageable pageable);
    SimpleMap findById(String id);
}
