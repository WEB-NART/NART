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
        assertThat(messageUnderTest.equals(new Message())).isTrue();
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

    @Test
    void testSet() {
        messageUnderTest.setReceiverId("id");
        messageUnderTest.setSenderId("id");
        messageUnderTest.setMsg("id");
        messageUnderTest.setType("id");
        messageUnderTest.setDate(0L);
        messageUnderTest.setLevel(0);
        assertThat(messageUnderTest.getReceiverId()).isEqualTo("id");
        assertThat(messageUnderTest.getSenderId()).isEqualTo("id");
        assertThat(messageUnderTest.getMsg()).isEqualTo("id");
        assertThat(messageUnderTest.getType()).isEqualTo("id");
        assertThat(messageUnderTest.getDate()).isEqualTo(0L);
        assertThat(messageUnderTest.getLevel()).isEqualTo(0);
    }
}
