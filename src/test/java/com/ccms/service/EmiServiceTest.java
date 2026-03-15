package com.ccms.service;

import com.ccms.dao.EmiDAO;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EmiServiceTest {

    @Test
    public void testEmiConversionSuccess() {

        EmiDAO mockDAO = mock(EmiDAO.class);

        when(mockDAO.convertToEmi(101, 6, 1000)).thenReturn(true);

        EmiService service = new EmiService(mockDAO);

        boolean result = service.convert(101, 6, 6000);

        assertTrue(result);

        verify(mockDAO).convertToEmi(101, 6, 1000);
    }

    @Test
    public void testEmiInvalidMonths() {

        EmiDAO mockDAO = mock(EmiDAO.class);

        EmiService service = new EmiService(mockDAO);

        boolean result = service.convert(101, 0, 6000);

        assertFalse(result);
    }
}