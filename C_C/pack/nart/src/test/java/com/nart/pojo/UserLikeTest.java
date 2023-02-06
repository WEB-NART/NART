package com.nart.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserLikeTest {

    private UserLike userLikeUnderTest;

    @BeforeEach
    void setUp() throws Exception {
        userLikeUnderTest = new UserLike();
    }

    @Test
    void testEquals() throws Exception {
        assertThat(userLikeUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() throws Exception {
        assertThat(userLikeUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() throws Exception {
        assertThat(userLikeUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() throws Exception {
        assertThat(userLikeUnderTest.toString()).isEqualTo("UserLike(id=null, uid=null, statusId=null)");
    }
}
