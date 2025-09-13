package com.arisu_library.arisu_library_management.model.request;

import java.time.LocalDate;

import com.arisu_library.arisu_library_management.model.enums.LoanStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoanRequestRecord(
    String id,
    @NotBlank(message = "Book id is required")
    String bookId,
    @NotBlank(message = "User id is required")
    String userId,
    @NotNull(message = "Loan date is required")
    LocalDate loanDate,
    @NotNull(message = "Due date is required")
    LocalDate dueDate,
    @NotNull(message = "Status is required")
    LoanStatus status
) {}
