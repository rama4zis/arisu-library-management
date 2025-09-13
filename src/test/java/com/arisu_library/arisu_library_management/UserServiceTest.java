package com.arisu_library.arisu_library_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.arisu_library.arisu_library_management.model.enums.Role;
import com.arisu_library.arisu_library_management.model.enums.Status;
import com.arisu_library.arisu_library_management.model.request.UserRequestRecord;
import com.arisu_library.arisu_library_management.repository.UserRepository;
import com.arisu_library.arisu_library_management.service.UserService;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testCreateUser() {
        UserRequestRecord newUser = new UserRequestRecord(
                null,
                "rama",
                "rama@example.com",
                "secret",
                Status.ACTIVE,
                Role.ADMIN);
        userService.registerUser(newUser);
    }
}
