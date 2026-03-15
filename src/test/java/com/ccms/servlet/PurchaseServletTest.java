package com.ccms.servlet;

import com.ccms.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PurchaseServletTest {

    @InjectMocks
    private PurchaseServlet purchaseServlet;

    @Mock
    private TransactionService transactionService;

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
    void testPurchaseSuccess() throws Exception {

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(1);
        when(request.getParameter("amount")).thenReturn("1000");

        when(transactionService.purchase(1, 1000)).thenReturn(true);

        purchaseServlet.doPost(request, response);

        writer.flush();

        assertEquals("Purchase Successful", stringWriter.toString());
    }

    @Test
    void testPurchaseFailure() throws Exception {

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(1);
        when(request.getParameter("amount")).thenReturn("1000");

        when(transactionService.purchase(1, 1000)).thenReturn(false);

        purchaseServlet.doPost(request, response);

        writer.flush();

        assertEquals("Purchase Failed", stringWriter.toString());
    }
}