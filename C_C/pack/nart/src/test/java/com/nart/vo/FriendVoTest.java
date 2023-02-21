package com.nart.vo;

import com.nart.pojo.Friend;
import com.nart.pojo.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class FriendVoTest {

    private FriendVo friendVoUnderTest;

    @BeforeEach
    void setUp() {
        friendVoUnderTest = new FriendVo();
    }

    @Test
    void testTransfer() {
        // Setup
        final Friend friend = new Friend();
        friend.setId("id");
        friend.setUid("uid");
        friend.setFid("fid");
        friend.setState(0);
        friend.setLeaveTime(0L);
        friend.setName("name");
        friend.setEmail("email");
        friend.setAvatar("avatar");
        friend.setOnline(false);
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        friend.setStatusList(Arrays.asList(status));
        friend.setNewMessage(false);

        final FriendVo expectedResult = new FriendVo();
        expectedResult.setId("fid");
        expectedResult.setName("name");
        expectedResult.setAvatar("avatar");
        expectedResult.setState(0);
        expectedResult.setNewMsg(false);

        // Run the test
        final FriendVo result = friendVoUnderTest.transfer(friend);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testToFriend() {
        // Setup
        final Friend expectedResult = new Friend();
        expectedResult.setFid("fid");
        expectedResult.setState(0);
        expectedResult.setName("name");
        expectedResult.setAvatar("avatar");
        expectedResult.setNewMessage(false);


        friendVoUnderTest.setId("fid");
        friendVoUnderTest.setState(0);
        friendVoUnderTest.setName("name");
        friendVoUnderTest.setAvatar("avatar");
        friendVoUnderTest.setNewMsg(false);
        // Run the test
        final Friend result = friendVoUnderTest.toFriend();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testEquals() {
        assertThat(friendVoUnderTest.equals("o")).isFalse();
        assertThat(friendVoUnderTest.equals(new FriendVo())).isTrue();
    }

    @Test
    void testCanEqual() {
        assertThat(friendVoUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(friendVoUnderTest.hashCode()).isNotZero();;
    }

    @Test
    void testToString() {
        assertThat(friendVoUnderTest.toString()).isEqualTo("FriendVo(id=null, name=null, avatar=null, state=null, newMsg=null)");
    }
}
