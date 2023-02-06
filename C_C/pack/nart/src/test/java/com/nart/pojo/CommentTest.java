package com.nart.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommentTest {

    private Comment commentUnderTest;

    @BeforeEach
    void setUp() {
        commentUnderTest = new Comment();
    }

    @Test
    void testEquals() {
        assertThat(commentUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(commentUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(commentUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() {
        assertThat(commentUnderTest.toString()).isEqualTo("Comment(id=null, msg=null, statusId=null, createDate=null, userId=null, uname=null)");
    }
}
