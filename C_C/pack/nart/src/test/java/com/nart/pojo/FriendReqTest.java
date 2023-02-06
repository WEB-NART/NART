package com.nart.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FriendReqTest {

    private FriendReq friendReqUnderTest;

    @BeforeEach
    void setUp() {
        friendReqUnderTest = new FriendReq();
    }

    @Test
    void testEquals() {
        assertThat(friendReqUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(friendReqUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(friendReqUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() {
        assertThat(friendReqUnderTest.toString()).isEqualTo("FriendReq(id=null, senderId=null, receiverId=null, msg=null, date=null)");
    }
}
