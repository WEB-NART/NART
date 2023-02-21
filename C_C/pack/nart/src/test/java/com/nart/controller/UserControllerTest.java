package com.nart.controller;

import com.nart.pojo.Friend;
import com.nart.pojo.User;
import com.nart.service.LoginService;
import com.nart.service.UserService;
import com.nart.util.ErrorCode;
import com.nart.util.Result;
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

import javax.servlet.http.HttpSession;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMybatis
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;
    @MockBean
    private LoginService mockLoginService;

    private final Result success = Result.success("data");
    private final String successToString = "{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":\"data\"}";
    private final Result fail = Result.fail(ErrorCode.UNDEFINED);
    private final String failToString = "{\"success\":false,\"code\":99999,\"msg\":\"undefined error\",\"data\":null}";

    @BeforeEach
    void setup() {
        User user = new User();
        user.setId("id");
        when(mockLoginService.checkToken("token")).thenReturn(user);
    }

    @Test
    void testLogin() throws Exception {
        // Setup
        when(mockLoginService.login(eq("uname"), eq("pwd"), any(HttpSession.class)))
                .thenReturn(success);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/user/login")
                        .content("{\"uname\":\"uname\",\"pwd\":\"pwd\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successToString);
    }

    @Test
    void testLogin_LoginServiceReturnsError() throws Exception {
        // Setup
        when(mockLoginService.login(eq("uname"), eq("pwd"), any(HttpSession.class)))
                .thenReturn(fail);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/user/login")
                        .content("{\"uname\":\"uname\",\"pwd\":\"pwd\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(failToString);
    }

    @Test
    void testLogout() throws Exception {
        // Setup
        when(mockUserService.upDatetime("id")).thenReturn(false);
        when(mockLoginService.logout("token")).thenReturn(success);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/user/logout")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successToString);
        verify(mockUserService).upDatetime("id");
    }

    @Test
    void testLogout_LoginServiceReturnsError() throws Exception {
        // Setup
        when(mockUserService.upDatetime("id")).thenReturn(false);
        when(mockLoginService.logout("token")).thenReturn(fail);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/user/logout")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(failToString);
        verify(mockUserService).upDatetime("id");
    }

    @Test
    void testRegister() throws Exception {
        // Setup
        when(mockLoginService.register(eq("email"), eq("uname"), eq("pwd"), any(HttpSession.class)))
                .thenReturn(success);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/user/register")
                        .content("{\"uname\":\"uname\",\"pwd\":\"pwd\",\"email\":\"email\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successToString);
    }

    @Test
    void testRegister_LoginServiceReturnsError() throws Exception {
        // Setup
        when(mockLoginService.register(eq("email"), eq("uname"), eq("pwd"), any(HttpSession.class)))
                .thenReturn(fail);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/user/register")
                        .content("{\"uname\":\"uname\",\"pwd\":\"pwd\",\"email\":\"email\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(failToString);
    }

    @Test
    void testShowUserInfo() throws Exception {
        // Setup
        // Configure UserService.showUserInfo(...).
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
        when(mockUserService.showUserInfo("id")).thenReturn(user);

        String result = "{\"success\":true,\"code\":200," +
                "\"msg\":\"success\",\"data\":{\"id\":\"id\",\"avatar\":\"avatar\",\"uname\":\"name\"," +
                "\"pwd\":\"pwd\",\"email\":\"email\",\"birthday\":\"age\",\"phone\":\"tel\"," +
                "\"address\":\"address\",\"power\":0,\"lock\":0}}";

        // Run the test
        MockHttpServletResponse response;
        response = mockMvc.perform(get("/user/info/{id}", "id")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(result);

        response = mockMvc.perform(get("/user/info/{id}", "-1")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(result);

        when(mockUserService.showUserInfo("id")).thenReturn(null);
        response = mockMvc.perform(get("/user/info/{id}", "id")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":10502," +
                "\"msg\":\"User not exists\",\"data\":null}");
    }

    @Test
    void testShowUnameAvatar() throws Exception {
        // Setup
        // Configure UserService.showUnameAvatar(...).
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
        when(mockUserService.showUnameAvatar("id")).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":true,\"code\":200," +
                "\"msg\":\"success\",\"data\":{\"id\":\"id\",\"avatar\":\"avatar\",\"uname\":\"name\"," +
                "\"pwd\":\"pwd\",\"email\":\"email\",\"birthday\":\"age\",\"phone\":\"tel\"," +
                "\"address\":\"address\",\"power\":0,\"lock\":0}}");
    }

    @Test
    void testChangeUserInfo() throws Exception {
        // Setup
        when(mockUserService.changeUserInfo(any(UserVo.class), any(String.class))).thenReturn(true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/user/changeInfo")
                        .header("Authorization", "token")
                        .content("{\"uname\":\"uname\",\"pwd\":\"pwd\",\"email\":\"email\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":true,\"code\":200," +
                "\"msg\":\"success\",\"data\":null}");

        // Setup
        when(mockUserService.changeUserInfo(any(UserVo.class), any(String.class))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response2 = mockMvc.perform(put("/user/changeInfo")
                        .header("Authorization", "token")
                        .content("{\"uname\":\"uname\",\"pwd\":\"pwd\",\"email\":\"email\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo("{\"success\":false,\"code\":10901," +
                "\"msg\":\"change personal information failed: unknown reason\",\"data\":null}");
    }
}
