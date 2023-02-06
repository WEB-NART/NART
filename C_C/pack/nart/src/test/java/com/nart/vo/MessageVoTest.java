package com.nart.vo;

import com.nart.pojo.FriendChat;
import com.nart.pojo.GroupChat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageVoTest {

    private MessageVo messageVoUnderTest;

    @BeforeEach
    void setUp() {
        messageVoUnderTest = new MessageVo();
    }

    @Test
    void testFtransfer() {
        // Setup
        final FriendChat friendChat = new FriendChat();
        friendChat.setId(0L);
        friendChat.setReceiverId("chatId");
        friendChat.setSenderId("senderId");
        friendChat.setLevel(0);
        friendChat.setMsg("msg");
        friendChat.setType("msgType");
        friendChat.setDate(0L);

        final MessageVo expectedResult = new MessageVo();
        expectedResult.setMsgId("0");
        expectedResult.setMsgType("msgType");
        expectedResult.setChatId("chatId");
        expectedResult.setSenderId("senderId");
        final DateVo sentDate = new DateVo();
        final DateVo createDate = new DateVo();
        createDate.setYear(1969);
        createDate.setMonth(12);
        createDate.setDay(31);
        createDate.setHour(19);
        createDate.setMin(0);
        expectedResult.setSentDate(createDate);
        expectedResult.setMsg("msg");

        // Run the test
        final MessageVo result = messageVoUnderTest.Ftransfer(friendChat);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGtransfer() {
        // Setup
        final GroupChat groupChat = new GroupChat();
        groupChat.setId(0L);
        groupChat.setGroupId("chatId");
        groupChat.setSenderId("senderId");
        groupChat.setMsg("msg");
        groupChat.setType("msgType");
        groupChat.setDate(0L);
        groupChat.setLevel(0);

        final MessageVo expectedResult = new MessageVo();
        expectedResult.setMsgId("0");
        expectedResult.setMsgType("msgType");
        expectedResult.setChatId("chatId");
        expectedResult.setSenderId("senderId");
        final DateVo createDate = new DateVo();
        createDate.setYear(1969);
        createDate.setMonth(12);
        createDate.setDay(31);
        createDate.setHour(19);
        createDate.setMin(0);
        expectedResult.setSentDate(createDate);
        expectedResult.setMsg("msg");

        // Run the test
        final MessageVo result = messageVoUnderTest.Gtransfer(groupChat);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testMtoG() {
        // Setup
        final GroupChat expectedResult = new GroupChat();
        expectedResult.setId(0L);
        expectedResult.setGroupId("chatId");
        expectedResult.setSenderId("senderId");
        expectedResult.setMsg("msg");
        expectedResult.setDate(0L);

        final DateVo createDate = new DateVo();
        createDate.setYear(1969);
        createDate.setMonth(12);
        createDate.setDay(31);
        createDate.setHour(19);
        createDate.setMin(0);
        messageVoUnderTest.setMsgId(String.valueOf(0L));
        messageVoUnderTest.setChatId("chatId");
        messageVoUnderTest.setSenderId("senderId");
        messageVoUnderTest.setMsg("msg");
        messageVoUnderTest.setType("msgType");
        messageVoUnderTest.setSentDate(createDate);

        // Run the test
        final GroupChat result = messageVoUnderTest.MtoG();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testMtoF() {
        // Setup
        final FriendChat expectedResult = new FriendChat();
        expectedResult.setId(0L);
        expectedResult.setReceiverId("chatId");
        expectedResult.setSenderId("senderId");
        expectedResult.setMsg("msg");
        expectedResult.setDate(0L);

        final DateVo createDate = new DateVo();
        createDate.setYear(1969);
        createDate.setMonth(12);
        createDate.setDay(31);
        createDate.setHour(19);
        createDate.setMin(0);
        messageVoUnderTest.setMsgId(String.valueOf(0L));
        messageVoUnderTest.setChatId("chatId");
        messageVoUnderTest.setSenderId("senderId");
        messageVoUnderTest.setMsg("msg");
        messageVoUnderTest.setType("msgType");
        messageVoUnderTest.setSentDate(createDate);

        // Run the test
        final FriendChat result = messageVoUnderTest.MtoF();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testEquals() {
        assertThat(messageVoUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(messageVoUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(messageVoUnderTest.hashCode()).isNotZero();;
    }

    @Test
    void testToString() {
        assertThat(messageVoUnderTest.toString()).isEqualTo("MessageVo(msgId=null, msgType=null, type=null, chatId=null, senderId=null, senderName=null, senderAvatar=null, sentDate=null, msg=null)");
    }
}