package com.arisu_library.arisu_library_management.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arisu_library.arisu_library_management.dto.UserDto;
import com.arisu_library.arisu_library_management.entity.User;
import com.arisu_library.arisu_library_management.model.filter.UserFilterRecord;
import com.arisu_library.arisu_library_management.model.request.ProfileRequestRecord;
import com.arisu_library.arisu_library_management.model.request.UserRequestRecord;
import com.arisu_library.arisu_library_management.model.response.BaseResponse;
import com.arisu_library.arisu_library_management.service.ProfileService;
import com.arisu_library.arisu_library_management.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ProfileService profileService;

    @PostMapping("/register")
    public BaseResponse<?> registerUser(@RequestBody UserRequestRecord req) {
        User data = userService.registerUser(req);
        UserDto userDto = UserDto.fromEntity(data);
        return BaseResponse.ok("success", userDto);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BaseResponse<?> editUser(@RequestBody UserRequestRecord req) {
        User data = userService.editUser(req);
        UserDto userDto = UserDto.fromEntity(data);
        return BaseResponse.ok("success", userDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BaseResponse<?> getUserById(@PathVariable String id) {
        return BaseResponse.ok("success", userService.findById(id));
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> getAllUsers(@PageableDefault(direction = Sort.Direction.DESC, sort = "modifiedDate") Pageable pageable,
                                   @RequestBody UserFilterRecord filterRequest) {
        return BaseResponse.ok("success", userService.findAll(filterRequest, pageable));
    }

    // Edit own profile (admins can edit any by providing id in body)
    @PostMapping("/profile/edit")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BaseResponse<?> editProfile(@RequestBody ProfileRequestRecord req) {
        profileService.editProfile(req);
        return BaseResponse.ok("success", null);
    }

    // Get profile by id (USER/ADMIN)
    @GetMapping("/{id}/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BaseResponse<?> getProfile(@PathVariable String id) {
        return BaseResponse.ok("success", profileService.findByUserId(id));
    }
}
