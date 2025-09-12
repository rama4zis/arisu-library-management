package com.arisu_library.arisu_library_management.model.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("Admin"),
    USER("User");

    private final String label;

    Role(String label) {
        this.label = label;
    }
}
