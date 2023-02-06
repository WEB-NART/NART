package com.nart.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageTest {

    private Message messageUnderTest;

    @BeforeEach
    void setUp() {
        messageUnderTest = new Message();
    }

    @Test
    void testEquals() {
        assertThat(messageUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(messageUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(messageUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() {
        assertThat(messageUnderTest.toString()).isEqualTo("Message(receiverId=null, senderId=null, msg=null, type=null, date=null, level=0)");
    }
}
