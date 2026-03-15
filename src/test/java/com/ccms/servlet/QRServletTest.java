package com.ccms.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class QRServletTest {

    @InjectMocks
    private QRServlet qrServlet;

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

        when(response.getOutputStream()).thenReturn(new ServletOutputStream() {

            @Override
            public void write(int b) {
                // no operation needed
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
                // not needed for test
            }
        });
    }

    @Test
    void testTxnIdMissing() throws Exception {

        when(request.getParameter("txnId")).thenReturn(null);

        qrServlet.doGet(request, response);

        writer.flush();

        assertEquals("Transaction ID required", stringWriter.toString());
    }

    @Test
    void testQRGeneration() throws Exception {

        QRServlet spyServlet = Mockito.spy(qrServlet);

        when(request.getParameter("txnId")).thenReturn("123");

        doNothing().when(spyServlet).generateQR(anyString(), any());

        spyServlet.doGet(request, response);

        verify(spyServlet).generateQR(eq("Transaction ID: 123"), any());
    }
}