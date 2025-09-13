package com.arisu_library.arisu_library_management.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arisu_library.arisu_library_management.builder.CustomBuilder;
import com.arisu_library.arisu_library_management.entity.User;
import com.arisu_library.arisu_library_management.mapper.UserMapper;
import com.arisu_library.arisu_library_management.model.app.AppPage;
import com.arisu_library.arisu_library_management.model.app.SimpleMap;
import com.arisu_library.arisu_library_management.model.filter.UserFilterRecord;
import com.arisu_library.arisu_library_management.model.request.UserRequestRecord;
import com.arisu_library.arisu_library_management.repository.UserRepository;
import com.arisu_library.arisu_library_management.service.UserService;
import com.arisu_library.arisu_library_management.service.ValidationService;
import com.arisu_library.arisu_library_management.util.FilterUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ValidationService validationService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserRequestRecord req) {
        validationService.validate(req);

        if (userRepository.existsByUsername(req.username())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(req.email())) {
            throw new RuntimeException("Email already exists");
        }

        User user = userMapper.requestToEntity(req);
        user.setPassword(passwordEncoder.encode(req.password()));
        userRepository.save(user);
    }

    @Override
    public void editUser(UserRequestRecord req) {
        validationService.validate(req);

        User user = userRepository.findById(req.id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getUsername().equals(req.username()) && userRepository.existsByUsername(req.username())) {
            throw new RuntimeException("Username already exists");
        }

        if (!user.getEmail().equals(req.email()) && userRepository.existsByEmail(req.email())) {
            throw new RuntimeException("Email already exists");
        }

        user.setUsername(req.username());
        user.setEmail(req.email());
        if (req.password() != null && !req.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(req.password()));
        }
        user.setStatus(req.status());
        user.setRole(req.role());

        userRepository.save(user);
    }

    @Override
    public Page<SimpleMap> findAll(UserFilterRecord filterReq, Pageable pageable) {
        CustomBuilder<User> builder = new CustomBuilder<>();

        FilterUtil.builderConditionNotBlankLike("username", filterReq.username(), builder);
        FilterUtil.builderConditionNotBlankLike("email", filterReq.email(), builder);
        FilterUtil.builderConditionNotNullEqual("status", filterReq.status(), builder);
        FilterUtil.builderConditionNotNullEqual("role", filterReq.role(), builder);

        Page<User> listUser = userRepository.findAll(builder.build(), pageable);
        List<SimpleMap> listData = listUser.stream().map(user -> {
            SimpleMap data = new SimpleMap();
            data.put("id", user.getId());
            data.put("username", user.getUsername());
            data.put("email", user.getEmail());
            data.put("role", user.getStatus().getLabel());
            data.put("status", user.getRole().getLabel());
            return data;
        }).toList();

        return AppPage.create(listData, pageable, listUser.getTotalElements());
    }

    @Override
    public SimpleMap findById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        SimpleMap data = new SimpleMap();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("email", user.getEmail());
        data.put("status", user.getStatus().getLabel());
        data.put("role", user.getRole().getLabel());
        return data;
    }

}
