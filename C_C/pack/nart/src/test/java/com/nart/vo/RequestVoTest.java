package com.nart.vo;

import com.nart.pojo.FriendReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestVoTest {

    private RequestVo requestVoUnderTest;

    @BeforeEach
    void setUp() {
        requestVoUnderTest = new RequestVo();
    }

    @Test
    void testTransfer() {
        // Setup
        final FriendReq friendReq = new FriendReq();
        friendReq.setId("id");
        friendReq.setSenderId("friendId");
        friendReq.setReceiverId("receiverId");
        friendReq.setMsg("msg");
        friendReq.setDate(0L);

        final RequestVo expectedResult = new RequestVo();
        expectedResult.setId("id");
        expectedResult.setFriendId("friendId");
        expectedResult.setMsg("msg");

        // Run the test
        final RequestVo result = requestVoUnderTest.transfer(friendReq);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testTofRequest() {
        // Setup
        final FriendReq expectedResult = new FriendReq();
        expectedResult.setId("id");
        expectedResult.setSenderId("friendId");
        expectedResult.setMsg("msg");

        requestVoUnderTest.setId("id");
        requestVoUnderTest.setFriendId("friendId");
        requestVoUnderTest.setMsg("msg");

        // Run the test
        final FriendReq result = requestVoUnderTest.tofRequest();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testEquals() {
        assertThat(requestVoUnderTest.equals("o")).isFalse();
        assertThat(requestVoUnderTest.equals(new RequestVo())).isTrue();
    }

    @Test
    void testCanEqual() {
        assertThat(requestVoUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(requestVoUnderTest.hashCode()).isNotZero();;
    }

    @Test
    void testToString() {
        assertThat(requestVoUnderTest.toString()).isEqualTo("RequestVo(id=null, friendId=null, msg=null, friendName=null, friendAvatar=null, senderName=null)");
    }
}
