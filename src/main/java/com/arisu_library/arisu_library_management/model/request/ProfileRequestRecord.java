package com.arisu_library.arisu_library_management.model.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record ProfileRequestRecord(
    String id,

    @NotBlank(message = "First name is required")
    String firstName,

    String lastName,

    @NotBlank(message = "Date of birth is required")
    LocalDate dob,

    @NotBlank(message = "Address is required")
    String address,

    @NotBlank(message = "City is required")
    String city,

    @NotBlank(message = "Phone number is required")
    String phone
    ) {
}
