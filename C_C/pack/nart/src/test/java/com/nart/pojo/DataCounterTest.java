package com.nart.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DataCounterTest {

    private DataCounter dataCounterUnderTest;

    @BeforeEach
    void setUp() {
        dataCounterUnderTest = new DataCounter();
    }

    @Test
    void testEquals() {
        assertThat(dataCounterUnderTest.equals("o")).isFalse();
        assertThat(dataCounterUnderTest.equals(new DataCounter())).isTrue();
    }

    @Test
    void testCanEqual() {
        assertThat(dataCounterUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(dataCounterUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() {
        assertThat(dataCounterUnderTest.toString()).isEqualTo("DataCounter(id=null, userAmount=0, userOnline=0, statusAmount=0, commentAmount=0, msgAmount=0)");
    }

    @Test
    void testSet() {
        dataCounterUnderTest.setId("id");
        dataCounterUnderTest.setUserAmount(1);
        dataCounterUnderTest.setUserOnline(1);
        dataCounterUnderTest.setStatusAmount(1);
        dataCounterUnderTest.setCommentAmount(1);
        dataCounterUnderTest.setMsgAmount(1);
        assertThat(dataCounterUnderTest.getUserAmount()).isEqualTo(1);
        assertThat(dataCounterUnderTest.getId()).isEqualTo("id");
        assertThat(dataCounterUnderTest.getUserOnline()).isEqualTo(1);
        assertThat(dataCounterUnderTest.getCommentAmount()).isEqualTo(1);
        assertThat(dataCounterUnderTest.getStatusAmount()).isEqualTo(1);
        assertThat(dataCounterUnderTest.getMsgAmount()).isEqualTo(1);
    }
}
