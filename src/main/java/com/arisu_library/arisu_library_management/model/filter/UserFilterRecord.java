package com.arisu_library.arisu_library_management.model.filter;

import com.arisu_library.arisu_library_management.model.enums.Role;
import com.arisu_library.arisu_library_management.model.enums.Status;

public record UserFilterRecord(String nama,
                               String email,
                               String username,
                               Status status,
                               Role role) {
}