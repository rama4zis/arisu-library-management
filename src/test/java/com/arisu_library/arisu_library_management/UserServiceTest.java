package com.arisu_library.arisu_library_management;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.arisu_library.arisu_library_management.model.enums.Role;
import com.arisu_library.arisu_library_management.model.enums.Status;
import com.arisu_library.arisu_library_management.model.request.ProfileRequestRecord;
import com.arisu_library.arisu_library_management.model.request.UserRequestRecord;
import com.arisu_library.arisu_library_management.repository.UserRepository;
import com.arisu_library.arisu_library_management.service.ProfileService;
import com.arisu_library.arisu_library_management.service.UserService;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void ensureAdmin() {
        SecurityContextHolder.clearContext();
        userRepository.findByUsername("rama").orElseGet(() -> {
            var newUser = new UserRequestRecord(
                null,
                "rama",
                "rama@example.com",
                "secret",
                Status.ACTIVE,
                Role.ADMIN
            );
            userService.registerUser(newUser);
            return userRepository.findByUsername("rama").orElseThrow();
        });
    }

    @Test
    public void testCreateUser() {
        var newUser = new UserRequestRecord(
            null,
            "user",
            "user@example.com",
            "user",
            Status.ACTIVE,
            Role.USER
        );
        userService.registerUser(newUser);
        assertTrue(userRepository.findByUsername("user").isPresent());
    }

    @Test
    public void testCreateUserProfile() {
        var newProfile = new ProfileRequestRecord(
            null,
            "Rama",
            "Sinta",
            LocalDate.of(1999, 10, 1),
            "Jl. Merdeka 123",
            "Surabaya",
            "123"
        );

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                "rama",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            )
        );

        profileService.editProfile(newProfile);

        SecurityContextHolder.clearContext();
    }
}
