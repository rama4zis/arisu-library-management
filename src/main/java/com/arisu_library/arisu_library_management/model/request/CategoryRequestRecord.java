package com.arisu_library.arisu_library_management.model.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestRecord(
    String id,

    @NotBlank(message = "Name is required")
    String name,
    
    String description
) {}
