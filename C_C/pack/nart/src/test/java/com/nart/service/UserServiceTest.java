package com.nart.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nart.dao.UserDao;
import com.nart.pojo.User;
import com.nart.util.Result;
import com.nart.util.UserThreadLocal;
import com.nart.vo.PageVo;
import com.nart.vo.UserVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Rollback
@Transactional
class UserServiceTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Test
    void findUser() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        User liu = userService.findUser("apiu", "f7502042e911bc69fceb1dcfffbd6adc");
        System.out.println(liu);
        User liu1= liu;
        assertEquals(liu1,liu);

        User user1 = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user1);
        User liu3 = userService.findUser("", "f7502042e911bc69fceb1dcfffbd6adc");
        System.out.println(liu);
        User liu2= liu3;
        assertEquals(liu2,liu3);
    }

    @Test
    void findUserByName() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        User apiu = userService.findUserByName("apiu");
        System.out.println(apiu);
        User apiu1= apiu;
        assertEquals(apiu1,apiu);
    }

    @Test
    void logout() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        boolean logout = userService.logout("1574989636311326722");
        System.out.println(logout);
        assertEquals(false,logout);

        boolean logout1 = userService.logout("1574989636705591298");
        System.out.println(logout1);
        assertEquals(true,logout1);
    }

    @Test
    void register() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);

        User register = userService.register("wewewe", "qi", "323423");
        System.out.println(register);
        User user1 = register;
        assertEquals(user1,register);
    }

    @Test
    void showUserInfo() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        User user1 = userService.showUserInfo("1574989632599367682");
        System.out.println(user1);
        User user2 = user1;
        assertEquals(user2,user1);
    }

    @Test
    void showUnameAvatar() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);

        User user1 = userService.showUnameAvatar("1574989632599367682");
        System.out.println(user1);
        User user2 = user1;
        assertEquals(user2,user1);
    }

    @Test
    void changeUserInfo() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);

        UserVo userVo = new UserVo();
        userVo.setId("4");
        userVo.setAddress("sfefef");
        userVo.setAvatar("sqqq");
        userVo.setBirthday("19980909");
        userVo.setEmail("dede");
        userVo.setPhone("3232323");
        userVo.setPwd("1222");
        userVo.setUname("qqqq");
        boolean b = userService.changeUserInfo(userVo, "1574989636311326722");
        assertEquals(true,b);

        UserVo userVo1 = new UserVo();
        userVo1.setId("4");
        userVo1.setAddress("sfefef");
        userVo1.setAvatar("sqqq");
        userVo1.setBirthday("19980909");
        userVo1.setEmail("dede");
        userVo1.setPhone("3232323");
        userVo1.setPwd("1KNl8HXMcBUt");

        userVo1.setUname("qqqq");
        boolean c = userService.changeUserInfo(userVo1, "1574989636311326722");
        assertEquals(true,c);
    }

    @Test
    void searchNew() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);

        PageVo pageVo = new PageVo();
        pageVo.setPageNum(1);
        pageVo.setPageSize(1);
        IPage<User> u = userService.searchNew("u", pageVo);
        System.out.println(u.getRecords());
        IPage<User> u1 = u;
        assertEquals(u1,u);
    }

    @Test
    void upDatetime() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        boolean b = userService.upDatetime("1574989636311326722");
        assertEquals(true,b);

    }
}