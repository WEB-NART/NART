package com.nart.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class RandomContentGeneratorTest {

    @Test
    void testGetRandomId1() {
        assertThat(RandomContentGenerator.getRandomId(Arrays.asList("value"))).isEqualTo("value");
    }

    @Test
    void testGetRandomId2() {
        assertThat(RandomContentGenerator.getRandomId(Arrays.asList("value"), "diff")).isEqualTo("value");
    }

    @Test
    void testGetRandomId3() {
        assertThat(RandomContentGenerator.getRandomId(Arrays.asList("value"), Arrays.asList("value")))
                .isEqualTo(null);
        assertThat(RandomContentGenerator.getRandomId(Arrays.asList("value", "value2"), Arrays.asList("value")))
                .isEqualTo("value2");
    }

    @Test
    void testGetRandomId4() {
        assertThat(
                RandomContentGenerator.getRandomId(Arrays.asList("value"), "diff1", Arrays.asList("value")))
                .isEqualTo(null);
        assertThat(
                RandomContentGenerator.getRandomId(Arrays.asList("value1", "value2"), "value2", Arrays.asList("value1")))
                .isEqualTo(null);
        assertThat(
                RandomContentGenerator.getRandomId(Arrays.asList("value1", "value3"), "value2", Arrays.asList("value1")))
                .isEqualTo("value3");
    }

    @Test
    void testGetRandPics() {
        assertThat(RandomContentGenerator.getRandPics(AlbumType.USER_AVATAR)).isEqualTo("https://picsum.photos/400/400?random=0");
    }
}
