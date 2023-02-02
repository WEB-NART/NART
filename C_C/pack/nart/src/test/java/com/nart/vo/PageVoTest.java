package com.nart.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PageVoTest {

    private PageVo pageVoUnderTest;

    @BeforeEach
    void setUp() {
        pageVoUnderTest = new PageVo(0, 0);
    }

    @Test
    void testToIPage() {
        // Setup
        // Run the test
        final IPage<String> result = pageVoUnderTest.toIPage(String.class);

        // Verify the results
    }

    @Test
    void testEquals() {
        assertThat(pageVoUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(pageVoUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(pageVoUnderTest.hashCode()).isNotZero();;
    }

    @Test
    void testToString() {
        assertThat(pageVoUnderTest.toString()).isEqualTo("PageVo(pageSize=0, pageNum=0)");
    }
}
