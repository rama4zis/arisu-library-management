package com.arisu_library.arisu_library_management.model.enums;

import lombok.Getter;

@Getter
public enum BookStatus {
    AVAILABLE("Available"),
    BORROWED("Borrowed"),
    LOST("Lost"),
    DAMAGED("Damaged");

    private final String label;

    BookStatus(String label) {
        this.label = label;
    }
}
