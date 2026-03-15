package com.ccms.service;

import com.ccms.dao.CardDAO;
import com.ccms.dao.TransactionDAO;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    @Test
    public void testPurchaseSuccess() {

        CardDAO mockCardDAO = mock(CardDAO.class);
        TransactionDAO mockTxDAO = mock(TransactionDAO.class);

        when(mockCardDAO.getCardIdByUser(1)).thenReturn(10);
        when(mockCardDAO.hasLimit(10, 5000)).thenReturn(true);
        when(mockTxDAO.addTransaction(10, 5000)).thenReturn(100);
        when(mockCardDAO.updateUsedAmount(10, 5000)).thenReturn(true);

        TransactionService service = new TransactionService(mockCardDAO, mockTxDAO);

        boolean result = service.purchase(1, 5000);

        assertTrue(result);

        verify(mockCardDAO).getCardIdByUser(1);
        verify(mockCardDAO).hasLimit(10, 5000);
        verify(mockTxDAO).addTransaction(10, 5000);
    }

    @Test
    public void testPurchaseCardNotFound() {

        CardDAO mockCardDAO = mock(CardDAO.class);
        TransactionDAO mockTxDAO = mock(TransactionDAO.class);

        when(mockCardDAO.getCardIdByUser(1)).thenReturn(-1);

        TransactionService service = new TransactionService(mockCardDAO, mockTxDAO);

        boolean result = service.purchase(1, 5000);

        assertFalse(result);
    }

    @Test
    public void testPurchaseLimitExceeded() {

        CardDAO mockCardDAO = mock(CardDAO.class);
        TransactionDAO mockTxDAO = mock(TransactionDAO.class);

        when(mockCardDAO.getCardIdByUser(1)).thenReturn(10);
        when(mockCardDAO.hasLimit(10, 5000)).thenReturn(false);

        TransactionService service = new TransactionService(mockCardDAO, mockTxDAO);

        boolean result = service.purchase(1, 5000);

        assertFalse(result);
    }

    @Test
    public void testPurchaseInvalidAmount() {

        CardDAO mockCardDAO = mock(CardDAO.class);
        TransactionDAO mockTxDAO = mock(TransactionDAO.class);

        TransactionService service = new TransactionService(mockCardDAO, mockTxDAO);

        boolean result = service.purchase(1, -500);

        assertFalse(result);
    }
}