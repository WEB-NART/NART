package com.nart.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DateVoTest {

    private DateVo dateVoUnderTest;

    @BeforeEach
    void setUp() {
        DateVo dateVo = new DateVo();
        dateVo.setYear(1969);
        dateVo.setMonth(12);
        dateVo.setDay(31);
        dateVo.setHour(19);
        dateVo.setMin(0);
        dateVoUnderTest = dateVo;
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
        final DateVo result = dateVoUnderTest.getDateToString(dateVo.toLong());

        // Verify the results
        assertThat(result).isEqualTo(dateVo);
    }

    @Test
    void testToLong() {
        final DateVo dateVo = new DateVo();
        dateVo.setYear(1969);
        dateVo.setMonth(12);
        dateVo.setDay(31);
        dateVo.setHour(19);
        dateVo.setMin(0);

        assertThat(dateVoUnderTest.toLong()).isEqualTo(dateVo.toLong());
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
        DateVo dateVo = new DateVo();
        dateVo.setYear(1969);
        dateVo.setMonth(12);
        dateVo.setDay(31);
        dateVo.setHour(19);
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
        assertThat(dateVoUnderTest.toString()).isEqualTo("DateVo(year=1969, month=12, day=31, hour=19, min=0)");
    }
}
