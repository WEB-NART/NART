package com.nart.service;

import com.nart.pojo.Comment;
import com.nart.pojo.Status;
import com.nart.pojo.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Rollback
@Transactional
class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Test
    void showAllUserInfo() {
        List<User> users = adminService.showAllUserInfo();
        System.out.println(users);
        List<User> users1 = users;
        assertEquals(users1,users);
    }

    @Test
    void showAllUserNum() {
        int i = adminService.showAllUserNum();
        System.out.println(i);
        int a = i;
        assertEquals(a,i);
    }

    @Test
    void showOnlineUserInfo() {
        List<User> users = adminService.showOnlineUserInfo();
        System.out.println(users);
        List<User> users1 = users;
        assertEquals(users1,users);
    }

    @Test
    void showOnlineUserNum() {
        int i = adminService.showOnlineUserNum();
        System.out.println(i);
        int a = i;
        assertEquals(a,i);
    }

    @Test
    void showAllStatusInfo() {
        List<Status> statuses = adminService.showAllStatusInfo();
        System.out.println(statuses);
        List<Status> statuses1 = statuses;
        assertEquals(statuses1,statuses);
    }

    @Test
    void showAllStatusNum() {
        int i = adminService.showAllStatusNum();
        System.out.println(i);
        int a = i;
        assertEquals(a,i);
    }

    @Test
    void showAllCommentInfo() {
        List<Comment> comments = adminService.showAllCommentInfo();
        System.out.println(comments);
        List<Comment> comments1 = comments;
        assertEquals(comments1,comments);
    }

    @Test
    void showAllCommentNum() {
        int i = adminService.showAllCommentNum();
        System.out.println(i);
        int a = i;
        assertEquals(a,i);
    }

    @Test
    void searchUser() {
        User user = adminService.searchUser("1574989632599367682");
        System.out.println(user);
        User user1 = user;
        assertEquals(user1,user);
    }

    @Test
    void blockUser() {
        boolean b = adminService.blockUser("1574989632599367682");
        System.out.println(b);
        assertEquals(true,b);
    }

    @Test
    void deleteStatus() {
        boolean b = adminService.deleteStatus("1574989660160139265");
        System.out.println(b);
        assertEquals(true,b);
    }

    @Test
    void deleteComment() {
        boolean b = adminService.deleteComment("1574990084678279170");
        System.out.println(b);
        assertEquals(true,b);
    }
//
//    @Test
//    void logOut() {
//    }
//
//    @Test
//    void logIn() {
//    }
//
//    @Test
//    void checkAdmin() {
//    }
//
//    @Test
//    void setonline() {
//    }
}