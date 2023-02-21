package com.nart.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class FriendTest {

    private Friend friendUnderTest;

    @BeforeEach
    void setUp() {
        friendUnderTest = new Friend();
    }

    @Test
    void testEquals() {
        assertThat(friendUnderTest.equals("o")).isFalse();
        assertThat(friendUnderTest.equals(new Friend())).isTrue();
    }

    @Test
    void testCanEqual() {
        assertThat(friendUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(friendUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() {
        assertThat(friendUnderTest.toString()).isEqualTo("Friend(id=null, uid=null, fid=null, state=null, leaveTime=null, name=null, email=null, avatar=null, online=null, statusList=null, chatHistory=null, newMessage=null)");
    }

    @Test
    void testSet() {
        friendUnderTest.setChatHistory(Collections.emptyList());
        assertThat(friendUnderTest.getChatHistory().size()).isEqualTo(0);
    }
}
