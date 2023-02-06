package com.nart.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DateVoTest {

    private DateVo dateVoUnderTest;

    @BeforeEach
    void setUp() {
        dateVoUnderTest = new DateVo();
    }

    @Test
    void testGetDateToString() {
        // Setup
        final DateVo dateVo = new DateVo();
        dateVo.setYear(1969);
        dateVo.setMonth(12);
        dateVo.setDay(31);
        dateVo.setHour(19);
        dateVo.setMin(0);

        // Run the test
        final DateVo result = dateVoUnderTest.getDateToString(0L);

        // Verify the results
        assertThat(result).isEqualTo(dateVo);
    }

    @Test
    void testToLong() {
        assertThat(dateVoUnderTest.toLong()).isEqualTo(-62170138800000L);
    }

    @Test
    void testCreateDateVo() {
        // Setup
        final DateVo dateVo = new DateVo();
        dateVo.setYear(1969);
        dateVo.setMonth(12);
        dateVo.setDay(31);
        dateVo.setHour(19);
        dateVo.setMin(0);

        final DateVo createDate = new DateVo();
        createDate.setYear(1969);
        createDate.setMonth(12);
        createDate.setDay(31);
        createDate.setHour(19);
        createDate.setMin(0);

        // Run the test
        final DateVo result = dateVoUnderTest.createDateVo("1969-12-31 19:00:56Z", dateVo);

        // Verify the results
        assertThat(result).isEqualTo(createDate);
    }

    @Test
    void testEquals() {
        assertThat(dateVoUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(dateVoUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(dateVoUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() {
        assertThat(dateVoUnderTest.toString()).isEqualTo("DateVo(year=0, month=0, day=0, hour=0, min=0)");
    }
}
