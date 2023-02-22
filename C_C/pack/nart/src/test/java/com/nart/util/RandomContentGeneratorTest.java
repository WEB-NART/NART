package com.nart.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class RandomContentGeneratorTest {

    private RandomContentGenerator rcg;

    @BeforeEach
    void setup() {
        rcg = new RandomContentGenerator();
    }

    @Test
    void testGetRandomId1() {
        assertThat(RandomContentGenerator
                .getRandomId(Arrays.asList("value"))).isEqualTo("value");
    }

    @Test
    void testGetRandomId2() {
        assertThat(RandomContentGenerator
                .getRandomId(Arrays.asList("value", "diff"), "diff")).isEqualTo("value");
        assertThat(RandomContentGenerator
                .getRandomId(Arrays.asList("diff", "value"), "diff")).isEqualTo("value");
        assertThat(RandomContentGenerator
                .getRandomId(Arrays.asList("value", "diff"), "diff")).isEqualTo("value");
        assertThat(RandomContentGenerator
                .getRandomId(Arrays.asList("diff", "value"), "diff")).isEqualTo("value");
        assertThat(RandomContentGenerator
                .getRandomId(Arrays.asList("diff"), "diff")).isEqualTo(null);
        assertThat(RandomContentGenerator
                .getRandomId(Arrays.asList("not"), "diff")).isEqualTo("not");
    }

    @Test
    void testGetRandomId3() {
        assertThat(RandomContentGenerator
                .getRandomId(Arrays.asList("value"), Arrays.asList("value")))
                .isEqualTo(null);
        assertThat(RandomContentGenerator
                .getRandomId(Arrays.asList("value", "value2"), Arrays.asList("value")))
                .isEqualTo("value2");
        assertThat(RandomContentGenerator
                .getRandomId(Arrays.asList("value2", "value"), Arrays.asList("value")))
                .isEqualTo("value2");
        assertThat(RandomContentGenerator
                .getRandomId(Arrays.asList("value", "value2"), Arrays.asList("value")))
                .isEqualTo("value2");
        assertThat(RandomContentGenerator
                .getRandomId(Arrays.asList("value2", "value"), Arrays.asList("value")))
                .isEqualTo("value2");
        assertThat(RandomContentGenerator
                .getRandomId(Collections.emptyList(),new ArrayList<>()))
                .isEqualTo(null);
    }

    @Test
    void testGetRandomId4() {
        assertThat(RandomContentGenerator
                        .getRandomId(Arrays.asList("value"), "diff1", Arrays.asList("value")))
                .isEqualTo(null);
        assertThat(RandomContentGenerator
                        .getRandomId(Arrays.asList("value1", "value2"), "value2", Arrays.asList("value1")))
                .isEqualTo(null);
        assertThat(RandomContentGenerator
                        .getRandomId(Arrays.asList("value1", "value3"), "value2", Arrays.asList("value1")))
                .isEqualTo("value3");
        assertThat(RandomContentGenerator
                        .getRandomId(Arrays.asList("value2", "value1", "value3"), "value2", Arrays.asList("value1")))
                .isEqualTo("value3");
        assertThat(RandomContentGenerator
                        .getRandomId(Arrays.asList("value2", "value1", "value3"), "value2", Arrays.asList("value1")))
                .isEqualTo("value3");
    }

    @Test
    void testGetRandPics() {
        assertThat(RandomContentGenerator
                .getRandPics(AlbumType.USER_AVATAR)).contains("https://picsum.photos/400/400?random=");
        assertThat(RandomContentGenerator
                .getRandPics(AlbumType.GROUP_AVATAR)).contains("https://picsum.photos/400/400?random=");
        assertThat(RandomContentGenerator
                .getRandPics(AlbumType.STATUS_PICS)).contains("https://picsum.photos/400/400?random=");
        assertThat(RandomContentGenerator
                .getRandPics(AlbumType.CHAT_PICS)).contains("https://picsum.photos/400/400?random=");
        assertThat(RandomContentGenerator
                .getRandPics(AlbumType.OTHER)).isEqualTo("");

    }
}
