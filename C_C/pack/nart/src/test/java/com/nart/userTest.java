//package com.nart;
//
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.nart.dao.CommentDao;
//import com.nart.dao.UserDao;
//import com.nart.pojo.Comment;
//import com.nart.pojo.Status;
//import com.nart.pojo.User;
//import com.nart.service.*;
//import com.nart.util.FakeDataGenerator;
//import com.nart.util.Result;
//import com.nart.util.UserThreadLocal;
//import net.minidev.json.JSONUtil;
//import org.apache.catalina.Context;
//import org.apache.catalina.Manager;
//import org.apache.catalina.Session;
//import org.apache.catalina.SessionIdGenerator;
//import org.apache.catalina.session.StandardManager;
//import org.apache.catalina.session.StandardSession;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpSession;
//import javax.servlet.http.HttpSessionContext;
//import java.beans.PropertyChangeListener;
//import java.io.IOException;
//import java.util.Enumeration;
//import java.util.List;
//
////@RunWith(SpringRunner.class)
////@Rollback
////@Transactional
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class userTest {
//
//    @Test
//    void contextLoads() {
//    }
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private FakeDataGenerator fakeDataGenerator;
//
//    @Autowired
//    private LoadDataInDataBase loadDataInDataBase;
//
//    @Autowired
//    private AdminService adminService;
//
//    @Autowired
//    private CommentService commentService;
//
//    @Autowired
//    private CommentDao commentDao;
//
//    @Autowired
//    private LoginService loginService;
//
//    @Mock
//    private HttpSession session;
//
//    @Autowired
//    private UserDao userDao;
//
//    @Test
//    public void commentTest(){
//
//        List<Comment> comments = commentService.showCommentList("1574989661011582978");
//        System.out.println(comments);
//
//    }
//    @Test
//    public void showAllStatusNum(){
//        int i = adminService.showAllStatusNum();
//        System.out.println(i);
//    }
//
//    @Test
//
//    public void test(){
//
//        adminService.setonline("1574989632599367682");
//
//    }
////    @Test
////    public void lock(){
////        deleteStatus
////    }
//    @Test
//    public void showAllCommentInfo(){
//        List<Comment> comments = adminService.showAllCommentInfo();
//        System.out.println(comments);
//    }
//    @Test
//    public void showOnlineUserNum(){
//        int i = adminService.showOnlineUserNum();
//        System.out.println(i);
//    }
//    @Test
//    public void showAllUserInfo(){
//        List<User> users = adminService.showAllUserInfo();
//        System.out.println(users);
//
//    }
//
//    @Test
//    public void showAllUserNum(){
//        int i = adminService.showAllUserNum();
//        System.out.println(i);
//    }
//
//    @Test
//    public void showOnlineUserInfo(){
//        List<User> users = adminService.showOnlineUserInfo();
//        System.out.println(users);
//    }
////    @Test
////    public void setFakeDataGenerator(){
////        fakeDataGenerator.generateTestData(1);
////    }
//
//    @Test
//    public void updatetime(){
////        userService.upDatetime();
//    }
//
////    @Test
////    public void setLoadDataInDataBase(){
////        List<User> Users = new ArrayList<>();
////        User user = new User();
////        user.setId("1");
////        user.setName("1");
////        user.setAvatar(null);
////        user.setPwd("1");
////        user.setEmail(null);
////        user.setAddress(null);
////        user.setAge(null);
////        user.setTel(null);
////        User user2 = new User();
////        user2.setId("2");
////        user2.setName("2");
////        user2.setAvatar(null);
////        user2.setPwd("2");
////        user2.setEmail(null);
////        user2.setAddress(null);
////        user2.setAge(null);
////        user2.setTel(null);
////        Users.add(user);
////        Users.add(user2);
////        System.out.println(Users);
////        boolean b = loadDataInDataBase.LoadListUser(Users);
////        System.out.println(b);
////    }
//
//
//
////    @Test
////    public void Testlogin(){
////        User liu = userService.findUser("apiu", "f7502042e911bc69fceb1dcfffbd6adc");
////        System.out.println(liu);
////    }
//    @Test
//    public void Testlogin(){
////        Manager manager = new StandardManager();
////        HttpSession session = new StandardSession(manager);
//        Result apiu = loginService.login("apiu", "222222222", session);
//        System.out.println(apiu);
//    }
//
//    @Test
//    public void testChecktoken(){
//        User user = loginService.checkToken("eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NzgzMzUyMTYsInVzZXJJZCI6MTU3NDk4OTYzMjU5OTM2NzY4MiwiaWF0IjoxNjczMTUxMjE2fQ.gRayz_0oOUS8XFtiRRKNnGGP2BO0zKDRj8obZkIbHCY");
//        System.out.println(user);
//    }
//
//    @Test
//    public void testlogout(){
//        User user = userDao.selectById("1574989632599367682");
//        UserThreadLocal.put(user);
//        Result logout = loginService.logout("eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NzgzMzUyMTYsInVzZXJJZCI6MTU3NDk4OTYzMjU5OTM2NzY4MiwiaWF0IjoxNjczMTUxMjE2fQ.gRayz_0oOUS8XFtiRRKNnGGP2BO0zKDRj8obZkIbHCY");
//        System.out.println(logout);
//    }
//
//    @Test
//    public void register(){
//        Result register = loginService.register("lll", "iiii", "weew", session);
//        System.out.println(register);
//
//    }
//
////
////    @Test
////    public void Testlogout(){
////        boolean logout = userService.logout("1");
////        System.out.println(logout);
////    }
////
////    @Test
////    public void Testregister(){
////        User register = userService.register("wewewe", "qi", "323423");
////        System.out.println(register);
////    }
////
////    @Test
////    public void TestshowUserInfo(){
////        User user = userService.showUserInfo("1");
////        System.out.println(user);
////    }
////
////    @Test
////    public void TestshowUnameAvatar(){
////        User user = userService.showUnameAvatar("1");
////        System.out.println(user);
////    }
////
////    @Test
////    public void TestchangeUserInfo(){
////        UserVo userVo = new UserVo();
////        userVo.setId("4");
////        userVo.setAddress("sfefef");
////        userVo.setAvatar("sqqq");
////        userVo.setBirthday("19980909");
////        userVo.setEmail("dede");
////        userVo.setPhone("3232323");
////        userVo.setPwd("1222");
////        userVo.setUname("qqqq");
////        boolean b = userService.changeUserInfo(userVo, "3");
////        System.out.println(b);
////    }
////
////    @Test
////    public void TestsearchNew(){
////        PageVo pageVo = new PageVo();
////        pageVo.setPageNum(1);
////        pageVo.setPageSize(1);
////        IPage<User> u = userService.searchNew("u", pageVo);
////        System.out.println(u.getRecords());
////    }
//
//
//}
