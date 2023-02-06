
package com.nart.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GroupChatTest {

    private GroupChat groupChatUnderTest;

    @BeforeEach
    void setUp() {
        groupChatUnderTest = new GroupChat();
    }

    @Test
    void testEquals() {
        assertThat(groupChatUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(groupChatUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(groupChatUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() {
        assertThat(groupChatUnderTest.toString()).isEqualTo("GroupChat(id=null, groupId=null, senderId=null, msg=null, type=null, date=0, level=0)");
    }
}
