package com.nart.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;

class WebSocketListenerTest {

    private WebSocketListener webSocketListenerUnderTest;

    @BeforeEach
    void setUp() {
        webSocketListenerUnderTest = new WebSocketListener();
    }

    @Test
    void testRequestInitialized() {
        // Setup
        ServletRequestEvent sre = mock(ServletRequestEvent.class);
        HttpServletRequest requestMock = Mockito.mock(HttpServletRequest.class);
        HttpSession sessionMock = Mockito.mock(HttpSession.class);
        Mockito.when(sre.getServletRequest()).thenReturn(requestMock);
        Mockito.when(requestMock.getSession()).thenReturn(sessionMock);

        // Run the test
        webSocketListenerUnderTest.requestInitialized(sre);

        // Verify the results
    }

    @Test
    void testRequestDestroyed() {
        // Setup
        ServletRequestEvent sre = mock(ServletRequestEvent.class);

        // Run the test
        webSocketListenerUnderTest.requestDestroyed(sre);

        // Verify the results
    }
    
}
