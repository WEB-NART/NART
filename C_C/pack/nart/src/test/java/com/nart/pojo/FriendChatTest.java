package com.nart.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FriendChatTest {

    private FriendChat friendChatUnderTest;

    @BeforeEach
    void setUp() {
        friendChatUnderTest = new FriendChat();
    }

    @Test
    void testEquals() {
        assertThat(friendChatUnderTest.equals("o")).isFalse();
        assertThat(friendChatUnderTest.equals(new FriendChat())).isTrue();
    }

    @Test
    void testCanEqual() {
        assertThat(friendChatUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(friendChatUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() {
        assertThat(friendChatUnderTest.toString()).isEqualTo("FriendChat(id=null, receiverId=null, senderId=null, level=0, msg=null, type=null, date=null)");
    }
}
