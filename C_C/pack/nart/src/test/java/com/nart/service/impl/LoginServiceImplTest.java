package com.nart.service.impl;

import com.nart.pojo.Friend;
import com.nart.pojo.User;
import com.nart.service.DataCounterService;
import com.nart.service.UserService;
import com.nart.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @Mock
    private UserService mockUserService;
    @Mock
    private RedisUtil mockRedisUtil;
    @Mock
    private DataCounterService mockDataCounterService;

    private LoginServiceImpl loginServiceImplUnderTest;

    final private String userId = "1620537771405410306";

    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        loginServiceImplUnderTest = new LoginServiceImpl(mockUserService, mockRedisUtil, mockDataCounterService);
        User user = new User();
        user.setId("id");
        UserThreadLocal.put(user);
    }

    @Test
    public void testLogin() {
        // Setup
        final Result expectedResult = Result.fail(ErrorCode.ACCOUNT_PWD_NOT_MATCH);
        final Result expectedResult2 = Result.success(anyString());
        final Result expectedResult3 = Result.fail(ErrorCode.PARAMS_ERROR);

        // Configure UserService.findUserByName(...).
        final User user = new User();
        user.setId("1234");
        user.setPwd("16bc8226733824295e9fc4383f63f39b");
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

        User user1 = new User();
        user1.setSalt("qwer");
        user1.setPwd("pwd");
        when(mockUserService.findUserByName("uname")).thenReturn(user1, user);
        when(mockUserService.login("1234")).thenReturn(true);

        when(mockDataCounterService.updateOnlineUserAmount(true)).thenReturn(0);

        // Run the test
        final Result r = loginServiceImplUnderTest.login("uname", "pwd", session);
        // Verify the results
        assertThat(r).isEqualTo(expectedResult);

        // Run the test
        final Result result = loginServiceImplUnderTest.login("uname", "pwd", session);
        // Verify the results
        assertThat(result.getSuccess()).isEqualTo(expectedResult2.getSuccess());
        assertThat(result.getCode()).isEqualTo(expectedResult2.getCode());
        assertThat(result.getMsg()).isEqualTo(expectedResult2.getMsg());
        verify(mockRedisUtil).set("TOKEN_" + result.getData(), user, RedisUtil.DEFAULT_EXPIRE);
        verify(mockDataCounterService).updateOnlineUserAmount(true);

        // Run the test
        final Result result2 = loginServiceImplUnderTest.login("uname", "", session);
        // Verify the results
        assertThat(result2).isEqualTo(expectedResult3);

        // Run the test
        final Result result3 = loginServiceImplUnderTest.login("", "pwd", session);
        // Verify the results
        assertThat(result3).isEqualTo(expectedResult3);
    }

    @Test
    public void testCheckToken() {
        // Setup
        final User expectedResult = new User();
        expectedResult.setId("1234");
        expectedResult.setPwd("pwd");
        expectedResult.setTpwd("tpwd");
        expectedResult.setSalt("salt");
        expectedResult.setAvatar("avatar");
        expectedResult.setName("name");
        expectedResult.setTel("tel");
        expectedResult.setAddress("address");
        expectedResult.setEmail("email");
        expectedResult.setAge("age");
        expectedResult.setUserOnline(0);
        expectedResult.setPower(0);
        expectedResult.setState(0);
        final Friend friend = new Friend();
        friend.setId("id");
        expectedResult.setFriendList(Arrays.asList(friend));

        // Configure RedisUtil.get(...).
        final User user = new User();
        user.setId("1234");
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
        final Friend friend1 = new Friend();
        friend1.setId("id");
        user.setFriendList(Arrays.asList(friend1));
        when(mockRedisUtil.get(anyString(), any())).thenReturn(user);
        String token = EncryptUtil.createToken(1234L);

        // Run the test
        final User result = loginServiceImplUnderTest.checkToken(token);
        // Verify the results
        assertThat(result).isEqualTo(expectedResult);

        // Run the test
        final User result2 = loginServiceImplUnderTest.checkToken("");
        // Verify the results
        assertThat(result2).isEqualTo(null);
    }

    @Test
    public void testCheckToken_EncryptUtilReturnsNull() {
        // Run the test
        final User result = loginServiceImplUnderTest.checkToken("token");
        // Verify the results
        assertThat(result).isNull();
    }

    @Test
    public void testCheckToken_RedisUtilReturnsNull() {
        // Setup
        String token = EncryptUtil.createToken(1234L);
        when(mockRedisUtil.get("TOKEN_" + token, User.class)).thenReturn(null);

        // Run the test
        final User result = loginServiceImplUnderTest.checkToken(token);

        // Verify the results
        assertThat(result).isNull();
    }

    @Test
    public void testLogout() {
        // Setup
        final Result expectedResult = Result.success(null);
        when(mockUserService.logout("id")).thenReturn(false);
        when(mockDataCounterService.updateOnlineUserAmount(false)).thenReturn(0);

        // Run the test
        final Result result = loginServiceImplUnderTest.logout("token");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockRedisUtil).delete("TOKEN_token");
        verify(mockUserService).logout("id");
        verify(mockDataCounterService).updateOnlineUserAmount(false);
    }

    @Test
    public void testRegister() {
        // Setup
        final Result expectedResult = Result.fail(ErrorCode.ACCOUNT_EXIST);
        final Result expectedResult2 = Result.success(EncryptUtil.createToken(1234L));

        // Configure UserService.findUserByName(...).
        final User user = new User();
        user.setId("1234");
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
        when(mockUserService.findUserByName("uname")).thenReturn(null, user);

        // Configure UserService.register(...).
        final User user1 = new User();
        user1.setId("1234");
        user1.setPwd("pwd");
        user1.setTpwd("tpwd");
        user1.setSalt("salt");
        user1.setAvatar("avatar");
        user1.setName("name");
        user1.setTel("tel");
        user1.setAddress("address");
        user1.setEmail("email");
        user1.setAge("age");
        user1.setUserOnline(0);
        user1.setPower(0);
        user1.setState(0);
        final Friend friend1 = new Friend();
        friend1.setId("id");
        user1.setFriendList(Arrays.asList(friend1));
        when(mockUserService.register(anyString(), anyString(), anyString(), anyString())).thenReturn(user1);

        when(mockDataCounterService.updateUserAmount(true)).thenReturn(0);
        when(mockDataCounterService.updateOnlineUserAmount(true)).thenReturn(0);

        // Run the test
        final Result result = loginServiceImplUnderTest.register("email", "uname", "pwd", session);

        // Verify the results
        assertThat(result.getSuccess()).isEqualTo(expectedResult2.getSuccess());
        assertThat(result.getCode()).isEqualTo(expectedResult2.getCode());
        assertThat(result.getMsg()).isEqualTo(expectedResult2.getMsg());
        verify(mockRedisUtil, atLeast(1))
                .set("TOKEN_" + (String) result.getData(), user1, 86400L);
        verify(mockDataCounterService, atLeast(1)).updateUserAmount(true);
        verify(mockDataCounterService, atLeast(1)).updateOnlineUserAmount(true);

        final Result r = loginServiceImplUnderTest.register("email", "uname", "pwd", session);
        assertThat(r).isEqualTo(expectedResult);
    }

    @Test
    public void testRegister_InputNull() {
        // Setup
        final Result expectedResult = Result.fail(ErrorCode.PARAMS_ERROR);
        // Run the test
        final Result result1 = loginServiceImplUnderTest.register("", "uname", "pwd", session);
        // Verify the results
        assertThat(result1).isEqualTo(expectedResult);

        // Run the test
        final Result result2 = loginServiceImplUnderTest.register("email", "", "pwd", session);
        // Verify the results
        assertThat(result2).isEqualTo(expectedResult);

        // Run the test
        final Result result3 = loginServiceImplUnderTest.register("email", "uname", "", session);
        // Verify the results
        assertThat(result3).isEqualTo(expectedResult);
    }

    @Test
    public void testRegister_UserServiceFindUserByNameReturnsNull() {
        // Setup
        final HttpSession session = null;
        final Result expectedResult = Result.fail(ErrorCode.REGISTER_ERROR);
        when(mockUserService.findUserByName("uname")).thenReturn(null);

        // Configure UserService.register(...).
        final User user = new User();
        user.setId("1234");
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
//        when(mockUserService.register("email", "uname", "pwd", "salt")).thenReturn(user);

//        when(mockDataCounterService.updateUserAmount(true)).thenReturn(0);
//        when(mockDataCounterService.updateOnlineUserAmount(true)).thenReturn(0);

        // Run the test
        final Result result = loginServiceImplUnderTest.register("email", "uname", "pwd", session);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
