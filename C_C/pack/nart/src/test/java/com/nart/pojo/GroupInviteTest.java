package com.nart.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GroupInviteTest {

    private GroupInvite groupInviteUnderTest;

    @BeforeEach
    void setUp() {
        groupInviteUnderTest = new GroupInvite();
    }

    @Test
    void testEquals() {
        assertThat(groupInviteUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(groupInviteUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(groupInviteUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() {
        assertThat(groupInviteUnderTest.toString()).isEqualTo("GroupInvite(id=null, groupId=null, senderId=null, receiverId=null, msg=null, date=null)");
    }
}
