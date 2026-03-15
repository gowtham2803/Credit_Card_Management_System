package com.ccms.servlet;

import com.ccms.model.User;
import com.ccms.service.UserService;
import org.junit.jupiter.api.Test;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginServletTest {

    @Test
    public void testLoginSuccess() throws Exception {

        LoginServlet servlet = new LoginServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        when(request.getParameter("email")).thenReturn("test@gmail.com");
        when(request.getParameter("password")).thenReturn("Password123");

        when(request.getSession(true)).thenReturn(session);

        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setRole("USER");

        UserService mockService = mock(UserService.class);

        // IMPORTANT: password will be hashed, so we match any string
        when(mockService.login(eq("test@gmail.com"), anyString()))
                .thenReturn(mockUser);

        servlet.setUserService(mockService);

        servlet.doPost(request, response);

        writer.flush();

        String result = stringWriter.toString();

        assertTrue(result.contains("Login Successful"));

        verify(session).setAttribute("userId", 1);
        verify(session).setAttribute("role", "USER");
    }

    @Test
    public void testLoginFailure() throws Exception {

        LoginServlet servlet = new LoginServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        when(request.getParameter("email")).thenReturn("wrong@gmail.com");
        when(request.getParameter("password")).thenReturn("wrongpass");

        UserService mockService = mock(UserService.class);

        when(mockService.login(eq("wrong@gmail.com"), anyString()))
                .thenReturn(null);

        servlet.setUserService(mockService);

        servlet.doPost(request, response);

        writer.flush();

        String result = stringWriter.toString();

        assertTrue(result.contains("Invalid credentials"));
    }
}