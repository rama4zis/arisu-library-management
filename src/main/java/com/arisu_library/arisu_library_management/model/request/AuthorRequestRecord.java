package com.arisu_library.arisu_library_management.model.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record AuthorRequestRecord(
    String id,
    @NotBlank(message = "Name is required")
    String name,
    String biography,
    LocalDate dob,
    String country,
    String website
) {}
