package com.nart.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nart.pojo.FriendReq;
import com.nart.pojo.User;
import com.nart.service.FriendService;
import com.nart.service.LoginService;
import com.nart.util.GsonFormatter;
import com.nart.util.Result;
import com.nart.vo.FriendVo;
import com.nart.vo.PageVo;
import com.nart.vo.RequestVo;
import com.nart.vo.UserVo;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FriendController.class)
@AutoConfigureMybatis
class FriendControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FriendService mockFriendService;

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
    void testShowFriendList() throws Exception {
        // Setup
        // Configure FriendService.showFriendList(...).
        final FriendVo friendVo = new FriendVo();
        friendVo.setId("id");
        friendVo.setName("name");
        friendVo.setAvatar("avatar");
        friendVo.setState(0);
        friendVo.setNewMsg(false);
        final List<FriendVo> friendVos = Arrays.asList(friendVo);
        when(mockFriendService.showFriendList(any(IPage.class), eq("id"))).thenReturn(friendVos);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/friend/list")
                        .content(GsonFormatter.toJsonString(page))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":true,\"code\":200," +
                "\"msg\":\"success\",\"data\":[{\"id\":\"id\",\"name\":\"name\",\"avatar\":\"avatar\"," +
                "\"state\":0,\"newMsg\":false}]}");
    }

    @Test
    void testShowFriendList_FriendServiceReturnsNull() throws Exception {
        // Setup
        when(mockFriendService.showFriendList(any(IPage.class), eq("id"))).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/friend/list")
                        .content(GsonFormatter.toJsonString(page))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":20901," +
                "\"msg\":\"show friends list failed: unknown reason\",\"data\":null}");
    }

    @Test
    void testShowFriendList_FriendServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockFriendService.showFriendList(any(IPage.class), eq("id"))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/friend/list")
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
    void testSearchFriend() throws Exception {
        // Setup
        // Configure FriendService.showFriendList(...).
        final FriendVo friendVo = new FriendVo();
        friendVo.setId("id");
        friendVo.setName("name");
        friendVo.setAvatar("avatar");
        friendVo.setState(0);
        friendVo.setNewMsg(false);
        final List<FriendVo> friendVos = Arrays.asList(friendVo);
        when(mockFriendService.showFriendList(any(IPage.class), eq("id"))).thenReturn(friendVos);

        // Configure FriendService.searchFriend(...).
        final UserVo userVo = new UserVo();
        userVo.setId("id");
        userVo.setAvatar("avatar");
        userVo.setUname("uname");
        userVo.setPwd("pwd");
        userVo.setEmail("email");
        userVo.setBirthday("birthday");
        userVo.setPhone("phone");
        userVo.setAddress("address");
        userVo.setPower(0);
        userVo.setLock(0);
        final List<UserVo> userVos = Arrays.asList(userVo);
        when(mockFriendService.searchFriend(eq("input"), any(IPage.class))).thenReturn(userVos);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                post("/friend/search/{input}", "input")
                        .content(GsonFormatter.toJsonString(page))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(GsonFormatter.toJsonString(Result.success(userVos)));

        final MockHttpServletResponse response2 = mockMvc.perform(
                        post("/friend/search/{input}", "-1")
                                .content(GsonFormatter.toJsonString(page))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString())
                .isEqualTo("{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":" +
                        "[{\"id\":\"id\",\"name\":\"name\",\"avatar\":\"avatar\",\"state\":0,\"newMsg\":false}]}");
    }

    @Test
    void testSearchFriend_FriendServiceShowFriendListReturnsNull() throws Exception {
        // Setup
        when(mockFriendService.showFriendList(any(IPage.class), eq("id"))).thenReturn(null);
        when(mockFriendService.searchFriend(eq("input"), any(IPage.class))).thenReturn(null);

        final MockHttpServletResponse response = mockMvc.perform(
                post("/friend/search/{input}", "input")
                        .content(GsonFormatter.toJsonString(page))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":20902," +
                "\"msg\":\"search friends failed: unknown reason\",\"data\":null}");

        final MockHttpServletResponse response2 = mockMvc.perform(
                        post("/friend/search/{input}", "-1")
                                .content(GsonFormatter.toJsonString(page))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo("{\"success\":false,\"code\":20902," +
                "\"msg\":\"search friends failed: unknown reason\",\"data\":null}");
    }

    @Test
    void testSearchFriend_FriendServiceShowFriendListReturnsNoItems() throws Exception {
        // Setup
        when(mockFriendService.showFriendList(any(IPage.class), eq("id"))).thenReturn(Collections.emptyList());
        when(mockFriendService.searchFriend(eq("input"), any(IPage.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                post("/friend/search/{input}", "input")
                        .content(GsonFormatter.toJsonString(page))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successListToString);

        final MockHttpServletResponse response2 = mockMvc.perform(
                        post("/friend/search/{input}", "-1")
                                .content(GsonFormatter.toJsonString(page))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo(successListToString);
    }

    @Test
    void testDelFriend() throws Exception {
        // Setup
        when(mockFriendService.delFriend("fid", "id")).thenReturn(false, true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                delete("/friend/del/{friendId}", "fid")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":20903," +
                "\"msg\":\"delete Friend failed: unknown reason\",\"data\":null}");

        final MockHttpServletResponse response2 = mockMvc.perform(
                        delete("/friend/del/{friendId}", "fid")
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo(successNullToString);
    }

    @Test
    void testChangeFriendState() throws Exception {
        // Setup
        when(mockFriendService.changeFriendState("fid", "id", 0)).thenReturn(false, true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                put("/friend/state/{friendId}/{state}", "fid", 0)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":20904," +
                "\"msg\":\"change Friend state failed: unknown reason\",\"data\":null}");

        final MockHttpServletResponse response2 = mockMvc.perform(
                        put("/friend/state/{friendId}/{state}", "fid", 0)
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo(successNullToString);
    }

    @Test
    void testShowReqList() throws Exception {
        // Setup
        // Configure FriendService.showReqList(...).
        final RequestVo requestVo = new RequestVo();
        requestVo.setId("id");
        requestVo.setFriendId("friendId");
        requestVo.setMsg("msg");
        requestVo.setFriendName("friendName");
        requestVo.setFriendAvatar("friendAvatar");
        requestVo.setSenderName("senderName");
        final List<RequestVo> requestVos = Arrays.asList(requestVo);
        when(mockFriendService.showReqList(any(IPage.class), eq("id"))).thenReturn(requestVos);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/friend/reqlist")
                        .header("Authorization", "token")
                        .content(GsonFormatter.toJsonString(page))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo("{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":" +
                        "[{\"id\":\"id\",\"friendId\":\"friendId\",\"msg\":\"msg\",\"friendName\":" +
                        "\"friendName\",\"friendAvatar\":\"friendAvatar\",\"senderName\":\"senderName\"}]}");
    }

    @Test
    void testShowReqList_FriendServiceReturnsNull() throws Exception {
        // Setup
        when(mockFriendService.showReqList(any(IPage.class), eq("id"))).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/friend/reqlist")
                        .header("Authorization", "token")
                        .content(GsonFormatter.toJsonString(page))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":20905," +
                "\"msg\":\"show friends requests failed: unknown reason\",\"data\":null}");
    }

    @Test
    void testShowReqList_FriendServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockFriendService.showReqList(any(IPage.class), eq("id"))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/friend/reqlist")
                        .header("Authorization", "token")
                        .content(GsonFormatter.toJsonString(page))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successListToString);

    }

    @Test
    void testSendFriendReq() throws Exception {
        // Setup
        when(mockFriendService.sendFriendReq("rid", "id", "msg")).thenReturn(false, true);

        FriendReq friendReq = new FriendReq();
        friendReq.setMsg("msg");
        friendReq.setReceiverId("rid");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/friend/send")
                        .header("Authorization", "token")
                        .content(GsonFormatter.toJsonString(friendReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":20906," +
                "\"msg\":\"send Friend request failed: unknown reason\",\"data\":null}");

        final MockHttpServletResponse response2 = mockMvc.perform(post("/friend/send")
                        .header("Authorization", "token")
                        .content(GsonFormatter.toJsonString(friendReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo(successNullToString);
    }

    @Test
    void testRespFriendReq() throws Exception {
        // Setup
        when(mockFriendService.respFriendReq("reqId", false)).thenReturn(false, true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                get("/friend/resp/{requestId}/{agree}", "reqId", false)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":20907," +
                "\"msg\":\"response to Friend request failed: unknown reason\",\"data\":null}");

        final MockHttpServletResponse response2 = mockMvc.perform(
                        get("/friend/resp/{requestId}/{agree}", "reqId", false)
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo(successNullToString);
    }

    @Test
    void testSearchNew() throws Exception {
        // Setup
        // Configure FriendService.searchNew(...).
        final UserVo userVo = new UserVo();
        userVo.setId("id");
        userVo.setAvatar("avatar");
        userVo.setUname("uname");
        userVo.setPwd("pwd");
        userVo.setEmail("email");
        userVo.setBirthday("birthday");
        userVo.setPhone("phone");
        userVo.setAddress("address");
        userVo.setPower(0);
        userVo.setLock(0);
        final List<UserVo> userVos = Arrays.asList(userVo);
        when(mockFriendService.searchNew("input", new PageVo(1, 1))).thenReturn(userVos);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                post("/friend/searchNew/{input}", "input")
                        .header("Authorization", "token")
                        .content(GsonFormatter.toJsonString(page))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo("{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":" +
                        "[{\"id\":\"id\",\"avatar\":\"avatar\",\"uname\":\"uname\",\"pwd\":\"pwd\",\"email\":" +
                        "\"email\",\"birthday\":\"birthday\",\"phone\":\"phone\",\"address\":\"address\"," +
                        "\"power\":0,\"lock\":0}]}");
    }

    @Test
    void testSearchNew_FriendServiceReturnsNull() throws Exception {
        // Setup
        when(mockFriendService.searchNew("input", new PageVo(1, 1))).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                post("/friend/searchNew/{input}", "input")
                        .header("Authorization", "token")
                        .content(GsonFormatter.toJsonString(page))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":20908," +
                "\"msg\":\"search new friends failed: unknown reason\",\"data\":null}");
    }

    @Test
    void testSearchNew_FriendServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockFriendService.searchNew("input", new PageVo(1, 1)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                post("/friend/searchNew/{input}", "input")
                        .header("Authorization", "token")
                        .content(GsonFormatter.toJsonString(page))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successListToString);
    }
}
