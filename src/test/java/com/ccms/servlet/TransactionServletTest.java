package com.ccms.servlet;

import com.ccms.model.Transaction;
import com.ccms.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TransactionServletTest {

    @InjectMocks
    private TransactionServlet servlet;

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
    void testUserNotLoggedIn() throws Exception {

        when(request.getSession(false)).thenReturn(null);

        servlet.doGet(request, response);

        writer.flush();

        assertEquals("Please login first", stringWriter.toString());
    }

    @Test
    void testNoTransactions() throws Exception {

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(1);

        when(request.getParameter("page")).thenReturn(null);
        when(request.getParameter("size")).thenReturn(null);

        when(transactionService.getPaginatedTransactions(1,1,5))
                .thenReturn(Collections.emptyList());

        servlet.doGet(request, response);

        writer.flush();

        assertTrue(stringWriter.toString().contains("No transactions found."));
    }

    @Test
    void testTransactionsFound() throws Exception {

        Transaction tx = new Transaction();
        tx.setId(101);
        tx.setAmount(5000);

        List<Transaction> list = Arrays.asList(tx);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(1);

        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("size")).thenReturn("5");

        when(transactionService.getPaginatedTransactions(1,1,5))
                .thenReturn(list);

        servlet.doGet(request, response);

        writer.flush();

        assertTrue(stringWriter.toString().contains("Txn ID"));
    }
}