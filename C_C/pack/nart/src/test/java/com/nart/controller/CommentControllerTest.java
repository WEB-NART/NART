package com.nart.controller;

import com.nart.pojo.Comment;
import com.nart.pojo.User;
import com.nart.service.CommentService;
import com.nart.service.LoginService;
import com.nart.util.GsonFormatter;
import com.nart.vo.CommentVo;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommentController.class)
@AutoConfigureMybatis
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService mockCommentService;

    @MockBean
    private LoginService mockLoginService;

    private DateVo dateVo;
    private final String successListToString = "{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":[]}";
    private final String successNullToString = "{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":null}";

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
    void testShowCommentList() throws Exception {
        // Setup
        // Configure CommentService.showCommentList(...).
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(dateVo.toLong());
        comment.setUserId("userId");
        comment.setUname("uname");
        final List<Comment> comments = Arrays.asList(comment);
        when(mockCommentService.showCommentList("statusId")).thenReturn(comments);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                get("/comment/list/{statusId}", "statusId")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo("{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":" +
                "[{\"statusId\":\"statusId\",\"uname\":\"uname\",\"msg\":\"msg\"," +
                "\"createDate\":{\"year\":1970,\"month\":1,\"day\":1,\"hour\":0,\"min\":0}}]}");
    }

    @Test
    void testShowCommentList_CommentServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockCommentService.showCommentList("statusId")).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                get("/comment/list/{statusId}", "statusId")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successListToString);
    }

    @Test
    void testPostComment() throws Exception {
        // Setup
        when(mockCommentService.postComment("statusId", "msg", "id")).thenReturn(false, true);
        CommentVo commentVo = new CommentVo();
        commentVo.setStatusId("statusId");
        commentVo.setMsg("msg");

        // Run the test
        MockHttpServletResponse response;
        response = mockMvc.perform(post("/comment/post")
                        .header("Authorization", "token")
                        .content(GsonFormatter.toJsonString(commentVo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":50902," +
                "\"msg\":\"post comment failed: unknown reason\",\"data\":null}");

        response = mockMvc.perform(post("/comment/post")
                        .header("Authorization", "token")
                        .content(GsonFormatter.toJsonString(commentVo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successNullToString);
    }
}
