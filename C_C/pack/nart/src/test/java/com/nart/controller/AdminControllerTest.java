package com.nart.controller;

import com.nart.pojo.Comment;
import com.nart.pojo.Friend;
import com.nart.pojo.Status;
import com.nart.pojo.User;
import com.nart.service.AdminService;
import com.nart.service.LoginService;
import com.nart.vo.DateVo;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminController.class)
@AutoConfigureMybatis
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService mockAdminService;

    @MockBean
    private LoginService mockLoginService;

    private DateVo dateVo;

    private final String successListToString = "{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":[]}";
    private final String successNullToString = "{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":null}";
    private final String successZeroToString = "{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":0}";
    private final String successTrueToString = "{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":true}";
    private final String undefineToString = "{\"success\":false,\"code\":99999,\"msg\":\"undefined error\",\"data\":null}";

    @BeforeEach
    void setup() {
        User user = new User();
        user.setId("id");
        when(mockLoginService.checkToken("token")).thenReturn(user);
        dateVo = new DateVo();
        dateVo.setYear(1970);
        dateVo.setMonth(1);
        dateVo.setDay(1);
        dateVo.setHour(0);
        dateVo.setMin(0);
    }

    @Test
    void testShowAllUserInfo() throws Exception {
        // Setup
        // Configure AdminService.showAllUserInfo(...).
        final User user = new User();
        user.setId("id");
        user.setPwd("pwd");
        user.setTpwd("tpwd");
        user.setSalt("salt");
        user.setAvatar("avatar");
        user.setName("name");
        user.setTel("tel");
        user.setAddress("address");
        user.setEmail("email");
        user.setAge("age");
        user.setUserOnline(0);
        user.setPower(0);
        user.setState(0);
        final Friend friend = new Friend();
        friend.setId("id");
        user.setFriendList(Arrays.asList(friend));
        final List<User> users = Arrays.asList(user);
        when(mockAdminService.showAllUserInfo()).thenReturn(users);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/admin/allUser")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":true,\"code\":200," +
                "\"msg\":\"success\",\"data\":[{\"id\":\"id\",\"avatar\":\"avatar\",\"uname\":\"name\"," +
                "\"pwd\":\"pwd\",\"email\":\"email\",\"birthday\":\"age\",\"phone\":\"tel\"," +
                "\"address\":\"address\",\"power\":0,\"lock\":0}]}");
    }

    @Test
    void testShowAllUserInfo_AdminServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockAdminService.showAllUserInfo()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/admin/allUser")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successListToString);
    }

    @Test
    void testShowAllUserNum() throws Exception {
        // Setup
        when(mockAdminService.showAllUserNum()).thenReturn(0, -1);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/admin/allUserNum")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successZeroToString);

        final MockHttpServletResponse response2 = mockMvc.perform(get("/admin/allUserNum")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo(undefineToString);
    }

    @Test
    void testShowOnlineUserInfo() throws Exception {
        // Setup
        // Configure AdminService.showOnlineUserInfo(...).
        final User user = new User();
        user.setId("id");
        user.setPwd("pwd");
        user.setTpwd("tpwd");
        user.setSalt("salt");
        user.setAvatar("avatar");
        user.setName("name");
        user.setTel("tel");
        user.setAddress("address");
        user.setEmail("email");
        user.setAge("age");
        user.setUserOnline(0);
        user.setPower(0);
        user.setState(0);
        final Friend friend = new Friend();
        friend.setId("id");
        user.setFriendList(Arrays.asList(friend));
        final List<User> users = Arrays.asList(user);
        when(mockAdminService.showOnlineUserInfo()).thenReturn(users);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/admin/onlineUser")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":true,\"code\":200," +
                "\"msg\":\"success\",\"data\":[{\"id\":\"id\",\"avatar\":\"avatar\",\"uname\":\"name\"," +
                "\"pwd\":\"pwd\",\"email\":\"email\",\"birthday\":\"age\",\"phone\":\"tel\"," +
                "\"address\":\"address\",\"power\":0,\"lock\":0}]}");
    }

    @Test
    void testShowOnlineUserInfo_AdminServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockAdminService.showOnlineUserInfo()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/admin/onlineUser")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successListToString);
    }

    @Test
    void testShowOnlineUserNum() throws Exception {
        // Setup
        when(mockAdminService.showOnlineUserNum()).thenReturn(0, -1);

        // Run the test
        MockHttpServletResponse response = mockMvc.perform(get("/admin/onlineUserNum")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successZeroToString);

        response = mockMvc.perform(get("/admin/onlineUserNum")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(undefineToString);
    }

    @Test
    void testShowAllStatusInfo() throws Exception {
        // Setup
        // Configure AdminService.showAllStatusInfo(...).
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        status.setLikes(0);
        status.setCreateDate(dateVo.toLong());
        status.setUserLike(false);
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(dateVo.toLong());
        comment.setUserId("userId");
        comment.setUname("uname");
        status.setCommentList(Arrays.asList(comment));
        final List<Status> statuses = Arrays.asList(status);
        when(mockAdminService.showAllStatusInfo()).thenReturn(statuses);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/admin/allStatus")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":true,\"code\":200," +
                "\"msg\":\"success\",\"data\":[{\"uid\":\"senderId\",\"uname\":null,\"avatar\":null," +
                "\"statusId\":\"id\",\"createDate\":{\"year\":1970,\"month\":1,\"day\":1,\"hour\":0,\"min\":0}," +
                "\"likes\":0,\"liked\":false,\"msg\":\"text\",\"pics\":[\"pics\"],\"comments\":" +
                "[{\"statusId\":\"statusId\",\"uname\":\"uname\",\"msg\":\"msg\"," +
                "\"createDate\":{\"year\":1970,\"month\":1,\"day\":1,\"hour\":0,\"min\":0}}]}]}");
    }

    @Test
    void testShowAllStatusInfo_AdminServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockAdminService.showAllStatusInfo()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/admin/allStatus")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successListToString);
    }

    @Test
    void testShowAllStatusNum() throws Exception {
        // Setup
        when(mockAdminService.showAllStatusNum()).thenReturn(0, -1);

        // Run the test
        MockHttpServletResponse response = mockMvc.perform(get("/admin/allStatusNum")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successZeroToString);

        response = mockMvc.perform(get("/admin/allStatusNum")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(undefineToString);
    }

    @Test
    void testShowAllCommentInfo() throws Exception {
        // Setup
        // Configure AdminService.showAllCommentInfo(...).
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(dateVo.toLong());
        comment.setUserId("userId");
        comment.setUname("uname");
        final List<Comment> comments = Arrays.asList(comment);
        when(mockAdminService.showAllCommentInfo()).thenReturn(comments);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/admin/allComment")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":true,\"code\":200," +
                "\"msg\":\"success\",\"data\":[{\"statusId\":\"statusId\",\"uname\":\"uname\",\"msg\":\"msg\"," +
                "\"createDate\":{\"year\":1970,\"month\":1,\"day\":1,\"hour\":0,\"min\":0}}]}");
    }

    @Test
    void testShowAllCommentInfo_AdminServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockAdminService.showAllCommentInfo()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/admin/allComment")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successListToString);
    }

    @Test
    void testShowAllCommentNum() throws Exception {
        // Setup
        when(mockAdminService.showAllCommentNum()).thenReturn(0, -1);

        // Run the test
        MockHttpServletResponse response = mockMvc.perform(get("/admin/allCommentNum")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successZeroToString);

        response = mockMvc.perform(get("/admin/allCommentNum")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(undefineToString);
    }

    @Test
    void testSearchUser() throws Exception {
        // Setup
        // Configure AdminService.searchUser(...).
        final User user = new User();
        user.setId("id");
        user.setPwd("pwd");
        user.setTpwd("tpwd");
        user.setSalt("salt");
        user.setAvatar("avatar");
        user.setName("name");
        user.setTel("tel");
        user.setAddress("address");
        user.setEmail("email");
        user.setAge("age");
        user.setUserOnline(0);
        user.setPower(0);
        user.setState(0);
        final Friend friend = new Friend();
        friend.setId("id");
        user.setFriendList(Arrays.asList(friend));
        when(mockAdminService.searchUser("id")).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                get("/admin/searchUser/{id}", "id")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":true,\"code\":200," +
                "\"msg\":\"success\",\"data\":{\"id\":\"id\",\"pwd\":\"pwd\",\"tpwd\":\"tpwd\",\"salt\":\"salt\"," +
                "\"avatar\":\"avatar\",\"name\":\"name\",\"tel\":\"tel\",\"address\":\"address\"," +
                "\"email\":\"email\",\"age\":\"age\",\"userOnline\":0,\"power\":0,\"state\":0," +
                "\"friendList\":[{\"id\":\"id\",\"uid\":null,\"fid\":null,\"state\":null," +
                "\"leaveTime\":null,\"name\":null,\"email\":null,\"avatar\":null,\"online\":null," +
                "\"statusList\":null,\"chatHistory\":null,\"newMessage\":null}],\"requestList\":null," +
                "\"groupList\":null,\"statusList\":null,\"groupInvites\":null}}");
    }

    @Test
    void testSearchUser_AdminServiceReturnsNull() throws Exception {
        // Setup
        when(mockAdminService.searchUser("id")).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                get("/admin/searchUser/{id}", "id")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(undefineToString);
    }

    @Test
    void testBlockUser() throws Exception {
        // Setup
        when(mockAdminService.blockUser("id")).thenReturn(false, true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                get("/admin/blockUser/{id}", "id")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(undefineToString);

        final MockHttpServletResponse response2 = mockMvc.perform(
                        get("/admin/blockUser/{id}", "id")
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo(successTrueToString);
    }

    @Test
    void testDeleteStatus() throws Exception {
        // Setup
        when(mockAdminService.deleteStatus("id")).thenReturn(false, true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                get("/admin/deleteStatus/{id}", "id")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(undefineToString);

        final MockHttpServletResponse response2 = mockMvc.perform(
                        get("/admin/deleteStatus/{id}", "id")
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo(successTrueToString);
    }

    @Test
    void testDeleteComment() throws Exception {
        // Setup
        when(mockAdminService.deleteComment("id")).thenReturn(false, true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                get("/admin/deleteComment/{id}", "id")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(undefineToString);

        final MockHttpServletResponse response2 = mockMvc.perform(
                        get("/admin/deleteComment/{id}", "id")
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo(successTrueToString);

    }
}
