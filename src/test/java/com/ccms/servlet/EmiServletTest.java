package com.ccms.servlet;

import com.ccms.service.EmiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EmiServletTest {

    @InjectMocks
    private EmiServlet emiServlet;

    @Mock
    private EmiService emiService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

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
    void testEmiSuccess() throws Exception {

        when(request.getParameter("txId")).thenReturn("10");
        when(request.getParameter("months")).thenReturn("12");
        when(request.getParameter("amount")).thenReturn("12000");

        when(emiService.convert(10,12,12000)).thenReturn(true);

        emiServlet.doPost(request, response);

        writer.flush();

        assertEquals("EMI Converted Successfully", stringWriter.toString());
    }

    @Test
    void testEmiFailure() throws Exception {

        when(request.getParameter("txId")).thenReturn("10");
        when(request.getParameter("months")).thenReturn("12");
        when(request.getParameter("amount")).thenReturn("12000");

        when(emiService.convert(10,12,12000)).thenReturn(false);

        emiServlet.doPost(request, response);

        writer.flush();

        assertEquals("EMI Failed", stringWriter.toString());
    }
}