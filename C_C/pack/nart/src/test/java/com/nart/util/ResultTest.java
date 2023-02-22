package com.nart.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResultTest {

    private Result resultUnderTest;

    @BeforeEach
    void setUp() {
        resultUnderTest = new Result(false, 0, "msg", "data");
    }

    @Test
    void testEquals() {
        Result result = new Result(true, 10, "smg", "String");
        Result result2 = new Result(true, 10, "smg", "String");
        assertThat(result.equals(result2)).isTrue();
        assertThat(resultUnderTest.equals(result2)).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(resultUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(resultUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() {
        assertThat(resultUnderTest.toString()).isEqualTo("Result(success=false, code=0, msg=msg, data=data)");
    }

    @Test
    void testSuccess() {
        // Run the test
        final Result result = Result.success("data");
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getMsg()).isEqualTo("success");
        assertThat(result.getData()).isEqualTo("data");
        assertThat(result.equals("o")).isFalse();
        assertThat(result.hashCode()).isNotZero();
        assertThat(result.toString()).isEqualTo("Result(success=true, code=200, msg=success, data=data)");
    }

    @Test
    void testFail1() {
        // Run the test
        final Result result = Result.fail(0, "msg");
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo(0);
        assertThat(result.getMsg()).isEqualTo("msg");
        assertThat(result.getData()).isEqualTo(null);
        assertThat(result.equals("o")).isFalse();
        assertThat(result.hashCode()).isNotZero();
        assertThat(result.toString()).isEqualTo("Result(success=false, code=0, msg=msg, data=null)");
    }

    @Test
    void testFail2() {
        // Run the test
        final Result result = Result.fail(ErrorCode.UNDEFINED);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo(99999);
        assertThat(result.getMsg()).isEqualTo("undefined error");
        assertThat(result.getData()).isEqualTo(null);
        assertThat(result.equals("o")).isFalse();
        assertThat(result.hashCode()).isNotZero();
        assertThat(result.toString()).isEqualTo("Result(success=false, code=99999, msg=undefined error, data=null)");
    }

    @Test
    void testSet() {
        Result result = Result.success(null);
        result.setCode(1);
        result.setData("data");
        result.setMsg("msg");
        result.setSuccess(false);

        assertThat(result.toString()).isEqualTo("Result(success=false, code=1, msg=msg, data=data)");
    }
}
