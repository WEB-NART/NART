package com.nart.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DateVoTest {

    private DateVo dateVoUnderTest;

    @BeforeEach
    void setUp() {
        DateVo dateVo = new DateVo();
        dateVo.setYear(1970);
        dateVo.setMonth(1);
        dateVo.setDay(1);
        dateVo.setHour(0);
        dateVo.setMin(0);
        dateVoUnderTest = dateVo;
    }

    @Test
    void testGetDateToString() {
        // Setup
        final DateVo dateVo = new DateVo();
        dateVo.setYear(1970);
        dateVo.setMonth(1);
        dateVo.setDay(1);
        dateVo.setHour(0);
        dateVo.setMin(0);

        // Run the test
        final DateVo result = dateVoUnderTest.getDateToString(dateVo.toLong());

        // Verify the results
        assertThat(result).isEqualTo(dateVo);
    }

    @Test
    void testToLong() {
        final DateVo dateVo = new DateVo();
        dateVo.setYear(1970);
        dateVo.setMonth(1);
        dateVo.setDay(1);
        dateVo.setHour(0);
        dateVo.setMin(0);

        assertThat(dateVoUnderTest.toLong()).isEqualTo(dateVo.toLong());
    }

    @Test
    void testCreateDateVo() {
        // Setup
        final DateVo dateVo = new DateVo();
        dateVo.setYear(1970);
        dateVo.setMonth(1);
        dateVo.setDay(1);
        dateVo.setHour(0);
        dateVo.setMin(0);

        final DateVo createDate = new DateVo();
        createDate.setYear(1970);
        createDate.setMonth(1);
        createDate.setDay(1);
        createDate.setHour(0);
        createDate.setMin(0);

        // Run the test
        final DateVo result = dateVoUnderTest.createDateVo("1970-01-01 00:00:56Z", dateVo);

        // Verify the results
        assertThat(result).isEqualTo(createDate);
    }

    @Test
    void testEquals() {
        DateVo dateVo = new DateVo();
        dateVo.setYear(1970);
        dateVo.setMonth(1);
        dateVo.setDay(1);
        dateVo.setHour(0);
        dateVo.setMin(0);
        assertThat(dateVoUnderTest.equals("o")).isFalse();
        assertThat(dateVoUnderTest.equals(dateVo)).isTrue();
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
        assertThat(dateVoUnderTest.toString()).isEqualTo("DateVo(year=1970, month=1, day=1, hour=0, min=0)");
    }
}
