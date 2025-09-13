package com.arisu_library.arisu_library_management.model.request;

import java.time.LocalDate;

import com.arisu_library.arisu_library_management.model.enums.BookStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequestRecord(
    String id,
    @NotBlank(message = "Title is required")
    String title,
    String summary,
    String coverImage,
    @NotBlank(message = "Author id is required")
    String authorId,
    String categoryId,
    Integer pages,
    String language,
    LocalDate publishedDate,
    @NotNull(message = "Status is required")
    BookStatus status
) {}
