package com.nart.service;

import com.nart.dao.UserDao;
import com.nart.pojo.User;
import com.nart.util.Result;
import com.nart.util.UserThreadLocal;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Rollback
@Transactional
class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Mock
    private HttpSession session;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;
    @Test
    void login() {
        Result apiu = loginService.login("apiu", "222222222", session);
        System.out.println(apiu);
        Object data = apiu.getData();
        Result a = new Result(true,200,"success",data);
        assertEquals(a,apiu);
    }

    @Test
    void checkToken() {
        Result apiu = loginService.login("apiu", "222222222", session);
//        System.out.println(apiu);
        String data = String.valueOf(apiu.getData());
        User user = loginService.checkToken(data);
        User user1 = new User();
        user1 = user;
        assertEquals(user1,user);
    }

    @Test
    void logout() {
        User user = userDao.selectById("1574989632599367682");
        UserThreadLocal.put(user);
        Result apiu = loginService.login("apiu", "222222222", session);
        String data = String.valueOf(apiu.getData());
        Result logout = loginService.logout(data);
        System.out.println(logout);
        Result r = new Result(true,200,"success",null);
        assertEquals(r,logout);
    }

    @Test
    void register() {
        Result register = loginService.register("lll", "ttt", "weew", session);
//        System.out.println(register);
        String data = String.valueOf(register.getData());
        Result r = new Result(true,200,"success",data);
        assertEquals(r,register);
    }
}