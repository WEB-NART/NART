package com.nart.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorCodeTest {

    @Test
    void testGetSet() {
        ErrorCode undefined = ErrorCode.UNDEFINED;
        undefined.setCode(1000);
        undefined.setMsg("what?");

        assertThat(undefined.getCode()).isEqualTo(1000);
        assertThat(undefined.getMsg()).isEqualTo("what?");

        undefined.setCode(99999);
        undefined.setMsg("undefined error");
    }
}
