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
}
