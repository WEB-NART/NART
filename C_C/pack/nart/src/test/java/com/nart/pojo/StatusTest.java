package com.nart.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusTest {

    private Status statusUnderTest;

    @BeforeEach
    void setUp() throws Exception {
        statusUnderTest = new Status();
    }

    @Test
    void testEquals() throws Exception {
        assertThat(statusUnderTest.equals("o")).isFalse();
        assertThat(statusUnderTest.equals(new Status())).isTrue();
    }

    @Test
    void testCanEqual() throws Exception {
        assertThat(statusUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() throws Exception {
        assertThat(statusUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() throws Exception {
        assertThat(statusUnderTest.toString()).isEqualTo("Status(id=null, senderId=null, text=null, pics=null, likes=0, createDate=null, userLike=null, commentList=null)");
    }
}
