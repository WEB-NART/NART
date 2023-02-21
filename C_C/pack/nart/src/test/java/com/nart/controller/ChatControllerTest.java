package com.nart.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nart.pojo.FriendChat;
import com.nart.pojo.GroupChat;
import com.nart.pojo.User;
import com.nart.service.ChatService;
import com.nart.service.LoginService;
import com.nart.util.GsonFormatter;
import com.nart.vo.DateVo;
import com.nart.vo.MessageVo;
import com.nart.vo.PageVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

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
@AutoConfigureMybatis
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService mockChatService;

    @MockBean
    private LoginService mockLoginService;

    private PageVo page;
    private final String successListToString = "{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":[]}";
    private final String successNullToString = "{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":null}";

    @BeforeEach
    void setup() {
        User user = new User();
        user.setId("id");
        when(mockLoginService.checkToken("token")).thenReturn(user);
        page = new PageVo(1, 1);
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
        final MockHttpServletResponse response = mockMvc.perform(
                post("/chat/history/{type}/{chatId}", "type", "chatId")
                        .content(GsonFormatter.toJsonString(page))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo("{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":" +
                        "[{\"msgId\":\"msgId\",\"msgType\":\"msgType\",\"type\":\"type\",\"chatId\":\"chatId\"," +
                        "\"senderId\":\"senderId\",\"senderName\":\"senderName\",\"senderAvatar\":\"senderAvatar\"," +
                        "\"sentDate\":{\"year\":2020,\"month\":1,\"day\":1,\"hour\":0,\"min\":0},\"msg\":\"msg\"}]}");

        final MockHttpServletResponse response2 = mockMvc.perform(
                        post("/chat/history/{type}/{chatId}", "friend", "chatId")
                                .content(GsonFormatter.toJsonString(page))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString())
                .isEqualTo("{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":" +
                "[{\"msgId\":\"msgId\",\"msgType\":\"msgType\",\"type\":\"type\",\"chatId\":\"chatId\"," +
                "\"senderId\":\"senderId\",\"senderName\":\"senderName\",\"senderAvatar\":\"senderAvatar\"," +
                "\"sentDate\":{\"year\":2020,\"month\":1,\"day\":1,\"hour\":0,\"min\":0},\"msg\":\"msg\"}]}");
    }

    @Test
    void testShowHistory_ChatServiceShowFriendHistoryReturnsNull() throws Exception {
        // Setup
        when(mockChatService.showFriendHistory(eq("chatId"), any(IPage.class))).thenReturn(null);
        when(mockChatService.showGroupHistory(eq("chatId"), any(IPage.class))).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                post("/chat/history/{type}/{chatId}", "type", "chatId")
                        .content(GsonFormatter.toJsonString(page))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":70901," +
                "\"msg\":\"show group chat history failed: unknown reason\",\"data\":null}");

        final MockHttpServletResponse response2 = mockMvc.perform(
                        post("/chat/history/{type}/{chatId}", "friend", "chatId")
                                .content(GsonFormatter.toJsonString(page))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo("{\"success\":false,\"code\":60901," +
                "\"msg\":\"show friend chat history failed: unknown reason\",\"data\":null}");
    }

    @Test
    void testShowHistory_ChatServiceShowFriendHistoryReturnsNoItems() throws Exception {
        // Setup
        when(mockChatService.showFriendHistory(eq("chatId"), any(IPage.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                post("/chat/history/{type}/{chatId}", "type", "chatId")
                        .content(GsonFormatter.toJsonString(page))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successListToString);
    }

    @Test
    void testSendMsg() throws Exception {
        // Setup


        MessageVo messageVo = new MessageVo();
        messageVo.setChatId("chatId");
        messageVo.setMsg("msg");
        messageVo.setMsgType("msgType");
        messageVo.setType("type");

        when(mockChatService.sendGroupMsg(any(GroupChat.class))).thenReturn(false, true);

        // Run the test
        MockHttpServletResponse response;
        response = mockMvc.perform(post("/chat/send")
                        .header("Authorization", "token")
                        .content(GsonFormatter.toJsonString(messageVo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":70902," +
                "\"msg\":\"send group chat failed: unknown reason\",\"data\":null}");

        response = mockMvc.perform(post("/chat/send")
                        .header("Authorization", "token")
                        .content(GsonFormatter.toJsonString(messageVo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successNullToString);

        messageVo.setType("friend");
        when(mockChatService.sendFriendMsg(any(FriendChat.class))).thenReturn(false, true);

        response = mockMvc.perform(post("/chat/send")
                        .header("Authorization", "token")
                        .content(GsonFormatter.toJsonString(messageVo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":60902," +
                "\"msg\":\"send friend chat failed: unknown reason\",\"data\":null}");

        response = mockMvc.perform(post("/chat/send")
                        .header("Authorization", "token")
                        .content(GsonFormatter.toJsonString(messageVo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successNullToString);

    }

    @Test
    void testLeaveRoom() throws Exception {
        // Setup
        when(mockChatService.leaveRoom("roomId", false)).thenReturn(false);
        when(mockChatService.leaveRoom("roomId", true)).thenReturn(true, false);

        // Run the test
        MockHttpServletResponse response;
        response = mockMvc.perform(
                        put("/chat/leaveRoom/{roomId}/{isFriend}", "roomId", false)
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":70903," +
                "\"msg\":\"update leave group chatRoom time failed: unknown reason\",\"data\":null}");

        response = mockMvc.perform(
                        put("/chat/leaveRoom/{roomId}/{isFriend}", "roomId", true)
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successNullToString);

        response = mockMvc.perform(
                        put("/chat/leaveRoom/{roomId}/{isFriend}", "roomId", true)
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":60903," +
                "\"msg\":\"update leave friend chatRoom time failed: unknown reason\",\"data\":null}");
    }
}
