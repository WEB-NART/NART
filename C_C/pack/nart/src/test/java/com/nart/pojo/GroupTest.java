package com.nart.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class GroupTest {

    private Group groupUnderTest;

    @BeforeEach
    void setUp() {
        groupUnderTest = new Group("id");
    }

    @Test
    void testEquals() {
        assertThat(groupUnderTest.equals("o")).isFalse();
        assertThat(groupUnderTest.equals(new Group("id"))).isTrue();
    }

    @Test
    void testCanEqual() {
        assertThat(groupUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(groupUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() {
        assertThat(groupUnderTest.toString()).isEqualTo("Group(id=id, groupName=null, notice=null, avatar=null, userLevel=0, userList=null, state=0, chatHistory=null, newMessage=null)");
    }

    @Test
    void testSet() {
        groupUnderTest.setState(0);
        groupUnderTest.setChatHistory(Collections.emptyList());
        assertThat(groupUnderTest.getState()).isEqualTo(0);
        assertThat(groupUnderTest.getChatHistory().size()).isEqualTo(0);
    }
}
