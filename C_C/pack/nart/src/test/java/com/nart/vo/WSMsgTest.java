package com.nart.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WSMsgTest {

    private WSMsg wsMsgUnderTest;

    @BeforeEach
    void setUp() throws Exception {
        wsMsgUnderTest = new WSMsg();
    }

    @Test
    void testEquals() throws Exception {
        assertThat(wsMsgUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() throws Exception {
        assertThat(wsMsgUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() throws Exception {
        assertThat(wsMsgUnderTest.hashCode()).isNotZero();;
    }

    @Test
    void testToString() throws Exception {
        assertThat(wsMsgUnderTest.toString()).isEqualTo("WSMsg(msg=null, sender=null, senderName=null, senderAvatar=null, msgType=null, receiver=null, receiverType=null)");
    }
}
