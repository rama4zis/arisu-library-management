package com.arisu_library.arisu_library_management.model.enums;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String label;

    Status(String label) {
        this.label = label;
    }
}
