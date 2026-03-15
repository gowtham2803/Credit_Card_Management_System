package com.ccms.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BillingServletTest {

    @InjectMocks
    private BillingServlet billingServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

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
    void testBillingSuccess() throws Exception {

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(1);

        BillingServlet spyServlet = Mockito.spy(billingServlet);

        doReturn(connection).when(spyServlet).getConnection();

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getDouble("used_amount")).thenReturn(5000.0);

        spyServlet.doGet(request, response);

        writer.flush();

        String output = stringWriter.toString();

        assertTrue(output.contains("Monthly Bill Generated"));
        assertTrue(output.contains("Total Due Amount"));
    }
}