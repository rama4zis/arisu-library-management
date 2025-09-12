package com.arisu_library.arisu_library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.arisu_library.arisu_library_management.entity.User;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    
}
