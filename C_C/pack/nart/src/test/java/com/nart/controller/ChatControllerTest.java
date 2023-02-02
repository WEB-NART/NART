package com.nart.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nart.pojo.FriendChat;
import com.nart.pojo.GroupChat;
import com.nart.service.ChatService;
import com.nart.vo.DateVo;
import com.nart.vo.MessageVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService mockChatService;

    private MockMvc mvc;

    private ChatController controller;

    @BeforeEach
    void setup() {
        //初始化
        MockitoAnnotations.initMocks(this);
        //构建mvc环境
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testShowHistory() throws Exception {
        // Setup
        // Configure ChatService.showFriendHistory(...).
        final MessageVo messageVo = new MessageVo();
        messageVo.setMsgId("msgId");
        messageVo.setMsgType("msgType");
        messageVo.setType("type");
        messageVo.setChatId("chatId");
        messageVo.setSenderId("senderId");
        messageVo.setSenderName("senderName");
        messageVo.setSenderAvatar("senderAvatar");
        final DateVo sentDate = new DateVo();
        sentDate.setYear(2020);
        sentDate.setMonth(1);
        sentDate.setDay(1);
        sentDate.setHour(0);
        sentDate.setMin(0);
        messageVo.setSentDate(sentDate);
        messageVo.setMsg("msg");
        final List<MessageVo> messageVos = Arrays.asList(messageVo);
        when(mockChatService.showFriendHistory(eq("chatId"), any(IPage.class))).thenReturn(messageVos);

        // Configure ChatService.showGroupHistory(...).
        final MessageVo messageVo1 = new MessageVo();
        messageVo1.setMsgId("msgId");
        messageVo1.setMsgType("msgType");
        messageVo1.setType("type");
        messageVo1.setChatId("chatId");
        messageVo1.setSenderId("senderId");
        messageVo1.setSenderName("senderName");
        messageVo1.setSenderAvatar("senderAvatar");
        final DateVo sentDate1 = new DateVo();
        sentDate1.setYear(2020);
        sentDate1.setMonth(1);
        sentDate1.setDay(1);
        sentDate1.setHour(0);
        sentDate1.setMin(0);
        messageVo1.setSentDate(sentDate1);
        messageVo1.setMsg("msg");
        final List<MessageVo> messageVos1 = Arrays.asList(messageVo1);
        when(mockChatService.showGroupHistory(eq("chatId"), any(IPage.class))).thenReturn(messageVos1);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("chat/history/{type}/{chatId}", "type", "chatId")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testShowHistory_ChatServiceShowFriendHistoryReturnsNull() throws Exception {
        // Setup
        when(mockChatService.showFriendHistory(eq("chatId"), any(IPage.class))).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("chat/history/{type}/{chatId}", "type", "chatId")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testShowHistory_ChatServiceShowFriendHistoryReturnsNoItems() throws Exception {
        // Setup
        when(mockChatService.showFriendHistory(eq("chatId"), any(IPage.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("chat/history/{type}/{chatId}", "type", "chatId")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testShowHistory_ChatServiceShowGroupHistoryReturnsNull() throws Exception {
        // Setup
        // Configure ChatService.showFriendHistory(...).
        final MessageVo messageVo = new MessageVo();
        messageVo.setMsgId("msgId");
        messageVo.setMsgType("msgType");
        messageVo.setType("type");
        messageVo.setChatId("chatId");
        messageVo.setSenderId("senderId");
        messageVo.setSenderName("senderName");
        messageVo.setSenderAvatar("senderAvatar");
        final DateVo sentDate = new DateVo();
        sentDate.setYear(2020);
        sentDate.setMonth(1);
        sentDate.setDay(1);
        sentDate.setHour(0);
        sentDate.setMin(0);
        messageVo.setSentDate(sentDate);
        messageVo.setMsg("msg");
        final List<MessageVo> messageVos = Arrays.asList(messageVo);
        when(mockChatService.showFriendHistory(eq("chatId"), any(IPage.class))).thenReturn(messageVos);

        when(mockChatService.showGroupHistory(eq("chatId"), any(IPage.class))).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("chat/history/{type}/{chatId}", "type", "chatId")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testShowHistory_ChatServiceShowGroupHistoryReturnsNoItems() throws Exception {
        // Setup
        // Configure ChatService.showFriendHistory(...).
        final MessageVo messageVo = new MessageVo();
        messageVo.setMsgId("msgId");
        messageVo.setMsgType("msgType");
        messageVo.setType("type");
        messageVo.setChatId("chatId");
        messageVo.setSenderId("senderId");
        messageVo.setSenderName("senderName");
        messageVo.setSenderAvatar("senderAvatar");
        final DateVo sentDate = new DateVo();
        sentDate.setYear(2020);
        sentDate.setMonth(1);
        sentDate.setDay(1);
        sentDate.setHour(0);
        sentDate.setMin(0);
        messageVo.setSentDate(sentDate);
        messageVo.setMsg("msg");
        final List<MessageVo> messageVos = Arrays.asList(messageVo);
        when(mockChatService.showFriendHistory(eq("chatId"), any(IPage.class))).thenReturn(messageVos);

        when(mockChatService.showGroupHistory(eq("chatId"), any(IPage.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("chat/history/{type}/{chatId}", "type", "chatId")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testSendMsg() throws Exception {
        // Setup
        when(mockChatService.sendFriendMsg(new FriendChat())).thenReturn(false);
        when(mockChatService.sendGroupMsg(new GroupChat())).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("chat/send")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testLeaveRoom() throws Exception {
        // Setup
        when(mockChatService.leaveRoom("roomId", false)).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        put("chat/leaveRoom/{roomId}/{isFriend}", "roomId", false)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }
}
