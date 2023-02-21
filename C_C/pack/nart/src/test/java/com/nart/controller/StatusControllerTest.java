package com.nart.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nart.dao.UserDao;
import com.nart.pojo.Comment;
import com.nart.pojo.Friend;
import com.nart.pojo.Status;
import com.nart.pojo.User;
import com.nart.service.LoginService;
import com.nart.service.StatusService;
import com.nart.util.GsonFormatter;
import com.nart.vo.DateVo;
import com.nart.vo.StatusVo;
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
@WebMvcTest(StatusController.class)
@AutoConfigureMybatis
class StatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatusService mockStatusService;
    @MockBean
    private UserDao mockUserDao;

    @MockBean
    private LoginService mockLoginService;

    private DateVo dateVo;

    private final String expectedResult = "{\"success\":true,\"code\":200,\"msg\":\"success\"," +
            "\"data\":" +
                "[{\"uid\":\"uid\",\"uname\":\"name\",\"avatar\":\"avatar\",\"statusId\":\"id\"," +
                    "\"createDate\":" +
                        "{\"year\":1969,\"month\":12,\"day\":31,\"hour\":19,\"min\":0}," +
                    "\"likes\":0,\"liked\":false,\"msg\":\"msg\",\"pics\":[\"pics\"]," +
                    "\"comments\":" +
                        "[{\"statusId\":\"statusId\",\"uname\":\"uname\",\"msg\":\"msg\"," +
                        "\"createDate\":" +
                            "{\"year\":1969,\"month\":12,\"day\":31,\"hour\":19,\"min\":0}" +
                        "}]" +
                    "}]" +
            "}";
    private final String successToString = "{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":[]}";

    @BeforeEach
    void setup() {
        User user = new User();
        user.setId("id");
        when(mockLoginService.checkToken("token")).thenReturn(user);
        dateVo = new DateVo();
        dateVo.setYear(1969);
        dateVo.setMonth(12);
        dateVo.setDay(31);
        dateVo.setHour(19);
        dateVo.setMin(0);
    }

    @Test
    void testShowStatusList() throws Exception {
        // Setup
        // Configure StatusService.showStatusList(...).
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("uid");
        status.setText("msg");
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
        when(mockStatusService.showStatusList(eq("id"), any(IPage.class))).thenReturn(statuses);

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("uid");
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
        when(mockUserDao.selectById("uid")).thenReturn(user);

        // Configure StatusService.showAllStatusList(...).
        final Status status1 = new Status();
        status1.setId("id");
        status1.setSenderId("uid");
        status1.setText("msg");
        status1.setPics("pics");
        status1.setLikes(0);
        status1.setCreateDate(dateVo.toLong());
        status1.setUserLike(false);
        final Comment comment1 = new Comment();
        comment1.setId("id");
        comment1.setMsg("msg");
        comment1.setStatusId("statusId");
        comment1.setCreateDate(0L);
        comment1.setUserId("userId");
        comment1.setUname("uname");
        status1.setCommentList(Arrays.asList(comment1));
        final List<Status> statuses1 = Arrays.asList(status1);
        when(mockStatusService.showAllStatusList(eq("id"), any(IPage.class))).thenReturn(statuses1);

        // not my status
        final MockHttpServletResponse response = mockMvc.perform(
                post("/status/list/{type}/{uid}", "type", "id")
                        .content("{\"pageSize\":1,\"pageNum\":1}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedResult);

        // my status and uid == -1
        final MockHttpServletResponse response2 = mockMvc.perform(
                post("/status/list/{type}/{uid}", "my", "-1")
                        .content("{\"pageSize\":1,\"pageNum\":1}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo(expectedResult);

        // my status and uid != -1
        final MockHttpServletResponse response3 = mockMvc.perform(
                post("/status/list/{type}/{uid}", "my", "id")
                        .content("{\"pageSize\":1,\"pageNum\":1}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response3.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response3.getContentAsString()).isEqualTo(expectedResult);
    }

    @Test
    void testShowStatusList_StatusServiceShowStatusListReturnsNoItems() throws Exception {
        // Setup
        when(mockStatusService.showStatusList(eq("id"), any(IPage.class))).thenReturn(Collections.emptyList());

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("uid");
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
        when(mockUserDao.selectById("uid")).thenReturn(user);

        // Configure StatusService.showAllStatusList(...).
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("uid");
        status.setText("msg");
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
        when(mockStatusService.showAllStatusList(eq("id"), any(IPage.class))).thenReturn(statuses);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                post("/status/list/{type}/{uid}", "type", "id")
                        .content("{\"pageSize\":1,\"pageNum\":1}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedResult);
    }

    @Test
    void testShowStatusList_StatusServiceShowAllStatusListReturnsNoItems() throws Exception {
        // Setup
        // Configure StatusService.showStatusList(...).
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("uid");
        status.setText("msg");
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
        when(mockStatusService.showStatusList(eq("id"), any(IPage.class))).thenReturn(statuses);

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("uid");
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
        when(mockUserDao.selectById("uid")).thenReturn(user);

        when(mockStatusService.showAllStatusList(eq("id"), any(IPage.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                post("/status/list/{type}/{uid}", "type", "uid")
                        .content("{\"pageSize\":1,\"pageNum\":1}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successToString);
    }

    @Test
    void testLikeStatus() throws Exception {
        // Setup
        when(mockStatusService.likeStatus("statusId", false)).thenReturn(false, true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                put("/status/like/{statusId}/{like}", "statusId", false)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo("{\"success\":false,\"code\":40901," +
                        "\"msg\":\"like status failed: unknown reason\",\"data\":null}");

        // Run the test
        final MockHttpServletResponse response2 = mockMvc.perform(
                        put("/status/like/{statusId}/{like}", "statusId", false)
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo("{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":null}");
    }

    @Test
    void testPostStatus() throws Exception {
        // Setup
        StatusVo statusVo = new StatusVo();
        statusVo.setMsg("");

        when(mockStatusService.postStatus(any(Status.class))).thenReturn(false, true);
        final MockHttpServletResponse response = mockMvc.perform(
                post("/status/post")
                        .content(GsonFormatter.toJsonString(statusVo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo("{\"success\":false,\"code\":40902," +
                        "\"msg\":\"post status failed: unknown reason\",\"data\":null}");

        statusVo.setPics(Collections.emptyList());

        final MockHttpServletResponse response2 = mockMvc.perform(
                        post("/status/post")
                                .content(GsonFormatter.toJsonString(statusVo))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString())
                .isEqualTo("{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":null}");
    }

    @Test
    void testDelStatus() throws Exception {
        // Setup
        when(mockStatusService.delStatus("statusId")).thenReturn(false, true);
        final MockHttpServletResponse response = mockMvc.perform(
                delete("/status/del/{statusId}", "statusId")
                        .content("{\"pageSize\":1,\"pageNum\":1}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo("{\"success\":false,\"code\":40903," +
                        "\"msg\":\"delete status failed: unknown reason\",\"data\":null}");

        final MockHttpServletResponse response2 = mockMvc.perform(
                        delete("/status/del/{statusId}", "statusId")
                                .content("{\"pageSize\":1,\"pageNum\":1}")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString())
                .isEqualTo("{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":null}");
    }
}
