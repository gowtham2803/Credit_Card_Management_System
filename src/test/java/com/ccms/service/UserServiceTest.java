package com.ccms.service;

import com.ccms.dao.UserDAO;
import com.ccms.model.User;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    public void testSignupSuccess() {

        UserDAO mockDAO = mock(UserDAO.class);

        when(mockDAO.registerUser(
                "TestUser",
                "test@gmail.com",
                "Password123"
        )).thenReturn(true);

        UserService service = new UserService(mockDAO);

        boolean result = service.signup(
                "TestUser",
                "test@gmail.com",
                "Password123"
        );

        assertTrue(result);
    }

    @Test
    public void testLoginSuccess() {

        UserDAO mockDAO = mock(UserDAO.class);

        User mockUser = new User();
        mockUser.setEmail("test@gmail.com");

        when(mockDAO.login("test@gmail.com", "Password123"))
                .thenReturn(mockUser);

        UserService service = new UserService(mockDAO);

        User result = service.login("test@gmail.com", "Password123");

        assertNotNull(result);
    }
}