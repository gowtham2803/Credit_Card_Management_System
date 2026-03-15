package com.ccms.servlet;

import com.ccms.service.UserService;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SignupServletTest {

    @Test
    public void testSignupSuccess() throws Exception {

        SignupServlet servlet = new SignupServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        when(request.getParameter("name")).thenReturn("Tony Stark");
        when(request.getParameter("email")).thenReturn("tony@gmail.com");
        when(request.getParameter("password")).thenReturn("Password123");

        UserService mockService = mock(UserService.class);
        when(mockService.signup(anyString(), anyString(), anyString())).thenReturn(true);

        servlet.setUserService(mockService);

        servlet.doPost(request, response);

        writer.flush();

        String result = stringWriter.toString();

        assertTrue(result.contains("Signup Successful"));
    }

    @Test
    public void testInvalidEmail() throws Exception {

        SignupServlet servlet = new SignupServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        when(request.getParameter("name")).thenReturn("Tony Stark");
        when(request.getParameter("email")).thenReturn("invalid-email");
        when(request.getParameter("password")).thenReturn("Password123");

        servlet.doPost(request, response);

        writer.flush();

        String result = stringWriter.toString();

        assertTrue(result.contains("Invalid email format"));
    }
}
