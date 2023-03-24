package com.nart.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.HashMap;

class WebSocketConfigTest {

    private WebSocketConfig webSocketConfigUnderTest;

    @BeforeEach
    void setUp() {
        webSocketConfigUnderTest = new WebSocketConfig();
    }

    @Test
    void testModifyHandshake() {
        // Setup
        final ServerEndpointConfig sec = Mockito.mock(ServerEndpointConfig.class);
        final HandshakeRequest request = Mockito.mock(HandshakeRequest.class);
        final HandshakeResponse response = null;
        HttpSession sessionMock = Mockito.mock(HttpSession.class);

        Mockito.when(request.getHttpSession()).thenReturn(sessionMock);
        Mockito.when(sec.getUserProperties()).thenReturn(new HashMap<>());
        // Run the test
        webSocketConfigUnderTest.modifyHandshake(sec, request, response);


        Mockito.when(request.getHttpSession()).thenReturn(null);
        // Run the test
        webSocketConfigUnderTest.modifyHandshake(sec, request, response);

    }

//    @Test
//    void testServerEndpointExporter() {
//        // Setup
//        // Run the test
//        final ServerEndpointExporter result = webSocketConfigUnderTest.serverEndpointExporter();
//
//        // Verify the results
//    }
}
