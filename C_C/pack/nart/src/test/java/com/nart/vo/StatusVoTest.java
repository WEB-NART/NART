package com.nart.vo;

import com.nart.pojo.Comment;
import com.nart.pojo.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class StatusVoTest {

    private StatusVo statusVoUnderTest;

    @BeforeEach
    void setUp() {
        statusVoUnderTest = new StatusVo();
    }

    @Test
    void testTransfer() {
        // Setup
        final DateVo createDate = new DateVo();
        createDate.setYear(1970);
        createDate.setMonth(1);
        createDate.setDay(1);
        createDate.setHour(0);
        createDate.setMin(0);
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("msg");
        status.setPics("value");
        status.setLikes(0);
        status.setCreateDate(createDate.toLong());
        status.setUserLike(false);
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(createDate.toLong());
        comment.setUserId("userId");
        comment.setUname("uname");
        status.setCommentList(Arrays.asList(comment));

        final StatusVo expectedResult = new StatusVo();
        expectedResult.setUid("senderId");
        expectedResult.setStatusId("id");
        expectedResult.setCreateDate(createDate);
        expectedResult.setLikes(0);
        expectedResult.setLiked(false);
        expectedResult.setMsg("msg");
        expectedResult.setPics(Arrays.asList("value"));
        final CommentVo commentVo = new CommentVo();
        commentVo.setStatusId("statusId");
        commentVo.setUname("uname");
        commentVo.setMsg("msg");
        final DateVo createDate1 = new DateVo();
        createDate1.setYear(1970);
        createDate1.setMonth(1);
        createDate1.setDay(1);
        createDate1.setHour(0);
        createDate1.setMin(0);
        commentVo.setCreateDate(createDate1);
        expectedResult.setComments(Arrays.asList(commentVo));

        // Run the test
        final StatusVo result = statusVoUnderTest.transfer(status);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetDateToString() {
        final DateVo createDate = new DateVo();
        createDate.setYear(1970);
        createDate.setMonth(1);
        createDate.setDay(1);
        createDate.setHour(0);
        createDate.setMin(0);
        assertThat(statusVoUnderTest.getDateToString(createDate.toLong())).isEqualTo("1970-01-01 00:00");
    }

    @Test
    void testCreateDateVo() {
        // Setup
        final DateVo dateVo = new DateVo();
        dateVo.setYear(2020);
        dateVo.setMonth(1);
        dateVo.setDay(1);
        dateVo.setHour(0);
        dateVo.setMin(0);

        final DateVo expectedResult = new DateVo();
        expectedResult.setYear(2020);
        expectedResult.setMonth(1);
        expectedResult.setDay(1);
        expectedResult.setHour(0);
        expectedResult.setMin(0);

        // Run the test
        final DateVo result = statusVoUnderTest.createDateVo("2020-01-01 00:00", dateVo);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testEquals() {
        assertThat(statusVoUnderTest.equals("o")).isFalse();
        assertThat(statusVoUnderTest.equals(new StatusVo())).isTrue();
    }

    @Test
    void testCanEqual() {
        assertThat(statusVoUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(statusVoUnderTest.hashCode()).isNotZero();;
    }

    @Test
    void testToString() {
        assertThat(statusVoUnderTest.toString()).isEqualTo("StatusVo(uid=null, uname=null, avatar=null, statusId=null, createDate=null, likes=0, liked=null, msg=null, pics=null, comments=null)");
    }
}
