package com.nart.service.impl;

import com.nart.dao.DataCounterDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataCounterServiceImplTest {

    @Mock
    private DataCounterDao mockDataCounterDao;

    @InjectMocks
    private DataCounterServiceImpl dataCounterServiceImplUnderTest;

    @Test
    public void testUpdateUserAmount() {
        // Setup
        when(mockDataCounterDao.updateUserAmount(1)).thenReturn(0);
        when(mockDataCounterDao.updateUserAmountm(1)).thenReturn(0);

        // Run the test
        final int result = dataCounterServiceImplUnderTest.updateUserAmount(false);

        // Verify the results
        assertThat(result).isEqualTo(2);

        final int r = dataCounterServiceImplUnderTest.updateUserAmount(true);
        verify(mockDataCounterDao).updateUserAmount(1);
        verify(mockDataCounterDao).updateUserAmountm(1);
    }

    @Test
    public void testUpdateOnlineUserAmount() {
        // Setup
        when(mockDataCounterDao.updateOnlineUserAmount(1)).thenReturn(0);
        when(mockDataCounterDao.updateOnlineUserAmountm(1)).thenReturn(0);

        // Run the test
        final int result = dataCounterServiceImplUnderTest.updateOnlineUserAmount(false);

        // Verify the results
        assertThat(result).isEqualTo(2);
        final int r = dataCounterServiceImplUnderTest.updateOnlineUserAmount(true);
        verify(mockDataCounterDao).updateOnlineUserAmount(1);
        verify(mockDataCounterDao).updateOnlineUserAmountm(1);
    }

    @Test
    public void testUpdateStatusAmount() {
        // Setup
        when(mockDataCounterDao.updateStatusAmount(1)).thenReturn(0);
        when(mockDataCounterDao.updateStatusAmountm(1)).thenReturn(0);

        // Run the test
        final int result = dataCounterServiceImplUnderTest.updateStatusAmount(false);

        // Verify the results
        assertThat(result).isEqualTo(2);
        final int r = dataCounterServiceImplUnderTest.updateStatusAmount(true);
        verify(mockDataCounterDao).updateStatusAmount(1);
        verify(mockDataCounterDao).updateStatusAmountm(1);
    }

    @Test
    public void testUpdateCommentAmount() {
        // Setup
        when(mockDataCounterDao.updateCommentAmount(1)).thenReturn(0);
        when(mockDataCounterDao.updateCommentAmountm(1)).thenReturn(0);

        // Run the test
        final int result = dataCounterServiceImplUnderTest.updateCommentAmount(false);

        // Verify the results
        assertThat(result).isEqualTo(2);
        final int r = dataCounterServiceImplUnderTest.updateCommentAmount(true);
        verify(mockDataCounterDao).updateCommentAmount(1);
        verify(mockDataCounterDao).updateCommentAmountm(1);
    }

    @Test
    public void testUpdateMessageAmount() {
        // Setup
        when(mockDataCounterDao.updateMessageAmount(1)).thenReturn(0);
        when(mockDataCounterDao.updateMessageAmountm(1)).thenReturn(0);

        // Run the test
        final int result = dataCounterServiceImplUnderTest.updateMessageAmount(false);

        // Verify the results
        assertThat(result).isEqualTo(2);
        final int r = dataCounterServiceImplUnderTest.updateMessageAmount(true);
        verify(mockDataCounterDao).updateMessageAmount(1);
        verify(mockDataCounterDao).updateMessageAmountm(1);
    }
}
