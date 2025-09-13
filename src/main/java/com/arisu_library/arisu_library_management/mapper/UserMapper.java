package com.arisu_library.arisu_library_management.mapper;

import org.springframework.stereotype.Component;

import com.arisu_library.arisu_library_management.entity.User;
import com.arisu_library.arisu_library_management.model.request.UserRequestRecord;

@Component
public class UserMapper {

    public User requestToEntity(UserRequestRecord req) {
        return User.builder()
            .username(req.username())
            .email(req.email())
            .status(req.status())
            .role(req.role())
            .build();
    }

}
