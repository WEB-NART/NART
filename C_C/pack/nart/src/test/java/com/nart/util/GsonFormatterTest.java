package com.nart.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GsonFormatterTest {

    @Test
    void initialize() {
        GsonFormatter gsonFormatter = new GsonFormatter();
    }

    @Test
    void testToJsonString() {
        assertThat(GsonFormatter.toJsonString("obj")).isEqualTo("\"obj\"");
    }

    @Test
    void testFromJsonToObj() {
        assertThat(GsonFormatter.fromJsonToObj("result", String.class)).isEqualTo("result");
    }
}
