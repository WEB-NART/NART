package com.nart.vo;

import com.nart.pojo.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommentVoTest {

    private CommentVo commentVoUnderTest;

    @BeforeEach
    void setUp() {
        commentVoUnderTest = new CommentVo();
    }

    @Test
    void testTransfer() {
        // Setup
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(0L);
        comment.setUserId("userId");
        comment.setUname("uname");

        final CommentVo expectedResult = new CommentVo();
        expectedResult.setStatusId("statusId");
        expectedResult.setUname("uname");
        expectedResult.setMsg("msg");
        final DateVo createDate = new DateVo();
        createDate.setYear(1969);
        createDate.setMonth(12);
        createDate.setDay(31);
        createDate.setHour(19);
        createDate.setMin(0);
        expectedResult.setCreateDate(createDate);

        // Run the test
        final CommentVo result = commentVoUnderTest.transfer(comment);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);

    }

    @Test
    void testEquals() {
        assertThat(commentVoUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(commentVoUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(commentVoUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() {
        assertThat(commentVoUnderTest.toString()).isEqualTo("CommentVo(statusId=null, uname=null, msg=null, createDate=null)");
    }
}