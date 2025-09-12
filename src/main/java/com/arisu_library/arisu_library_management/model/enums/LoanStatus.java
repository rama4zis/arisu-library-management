package com.arisu_library.arisu_library_management.model.enums;

import lombok.Getter;

@Getter
public enum LoanStatus {
    ONGOING("Ongoing"),
    RETURNED("Returned"),
    OVERDUE("Overdue");

    private final String label;

    LoanStatus(String label) {
        this.label = label;
    }
}
