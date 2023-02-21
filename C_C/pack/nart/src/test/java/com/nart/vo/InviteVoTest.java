package com.nart.vo;

import com.nart.pojo.GroupInvite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InviteVoTest {

    private InviteVo inviteVoUnderTest;

    @BeforeEach
    void setUp() {
        inviteVoUnderTest = new InviteVo();
    }

    @Test
    void testTransfer() {
        // Setup
        final GroupInvite groupInvite = new GroupInvite();
        groupInvite.setId("id");
        groupInvite.setGroupId("groupId");
        groupInvite.setMsg("msg");

        final InviteVo expectedResult = new InviteVo();
        expectedResult.setId("id");
        expectedResult.setGroupId("groupId");
        expectedResult.setMsg("msg");
        // Run the test
        final InviteVo result = inviteVoUnderTest.transfer(groupInvite);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testToGI() {
        // Setup
        final GroupInvite expectedResult = new GroupInvite();
        expectedResult.setId("id");
        expectedResult.setGroupId("groupId");
        expectedResult.setMsg("msg");


        inviteVoUnderTest.setId("id");
        inviteVoUnderTest.setGroupId("groupId");
        inviteVoUnderTest.setMsg("msg");

        // Run the test
        final GroupInvite result = inviteVoUnderTest.toGI();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testEquals() {
        assertThat(inviteVoUnderTest.equals("o")).isFalse();
        assertThat(inviteVoUnderTest.equals(new InviteVo())).isTrue();
    }

    @Test
    void testCanEqual() {
        assertThat(inviteVoUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(inviteVoUnderTest.hashCode()).isNotZero();;
    }

    @Test
    void testToString() {
        assertThat(inviteVoUnderTest.toString()).isEqualTo("InviteVo(id=null, groupId=null, msg=null, groupName=null, groupAvatar=null, senderName=null, receiverId=null)");
    }
}
