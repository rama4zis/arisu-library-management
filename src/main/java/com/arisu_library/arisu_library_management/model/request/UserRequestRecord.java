package com.arisu_library.arisu_library_management.model.request;

import com.arisu_library.arisu_library_management.model.enums.Role;
import com.arisu_library.arisu_library_management.model.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestRecord(
    String id,

    @NotBlank(message = "Username is required")
    String username, 

    @NotBlank(message = "Email is required")
    String email, 

    @NotBlank(message = "Password is required")
    String password,

    @NotNull(message = "Status is required")
    Status status,

    @NotNull(message = "Role is required")
    Role role
    ) {
}
