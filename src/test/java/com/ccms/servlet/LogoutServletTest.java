package com.ccms.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LogoutServletTest {

    @InjectMocks
    private LogoutServlet logoutServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private StringWriter stringWriter;
    private PrintWriter writer;

    @BeforeEach
    void setup() throws Exception {

        MockitoAnnotations.openMocks(this);

        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void testLogoutWithSession() throws Exception {

        when(request.getSession(false)).thenReturn(session);

        logoutServlet.doGet(request, response);

        verify(session).invalidate();

        writer.flush();

        assertEquals("Logout successful", stringWriter.toString());
    }

    @Test
    void testLogoutWithoutSession() throws Exception {

        when(request.getSession(false)).thenReturn(null);

        logoutServlet.doGet(request, response);

        writer.flush();

        assertEquals("Logout successful", stringWriter.toString());
    }
}