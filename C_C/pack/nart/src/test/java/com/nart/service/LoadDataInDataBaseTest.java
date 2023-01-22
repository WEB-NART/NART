package com.nart.service;

import com.nart.pojo.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Rollback
@Transactional
class LoadDataInDataBaseTest {

    @Autowired
    private LoadDataInDataBase loadDataInDataBase;
    @Test
    void loadListUser() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        user1.setName("lol1");
        user1.setPwd("1111111");

        user2.setName("lol2");
        user2.setPwd("222222");

        user3.setName("lol3");
        user3.setPwd("333333");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        boolean b = loadDataInDataBase.LoadListUser(users);
        assertEquals(true,b);
    }

    @Test
    void loadListFriendReq() {
        List<FriendReq> friendReqList = new ArrayList<>();
        FriendReq friendReq1 = new FriendReq();
        FriendReq friendReq2 = new FriendReq();
        friendReqList.add(friendReq1);
        friendReqList.add(friendReq2);
        boolean b = loadDataInDataBase.LoadListFriendReq(friendReqList);
        assertEquals(true,b);


    }

    @Test
    void loadListStatus() {
        List<Status> statusList = new ArrayList<>();
        Status status1 = new Status();
        Status status2 = new Status();
        status1.setCreateDate(111111L);
        status2.setCreateDate(111111L);
        statusList.add(status1);
        statusList.add(status2);
        boolean b = loadDataInDataBase.LoadListStatus(statusList);
        assertEquals(true,b);
    }

    @Test
    void loadListGroup() {
        List<Group> groupList = new ArrayList<>();
        Group group1 = new Group();
        Group group2 = new Group();
        groupList.add(group1);
        groupList.add(group2);
        boolean b = loadDataInDataBase.LoadListGroup(groupList);
        assertEquals(true,b);
    }

    @Test
    void loadListComment() {
        List<Comment> commentList = new ArrayList<>();
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        comment1.setCreateDate(1111L);
        comment2.setCreateDate(1111L);
        commentList.add(comment1);
        commentList.add(comment2);
        boolean b = loadDataInDataBase.LoadListComment(commentList);
        assertEquals(true,b);
    }

    @Test
    void loadListGroupInvite() {
        List<GroupInvite> groupInviteList = new ArrayList<>();
        GroupInvite groupInvite1= new GroupInvite();
        GroupInvite groupInvite2= new GroupInvite();
        groupInviteList.add(groupInvite1);
        groupInviteList.add(groupInvite2);
        boolean b = loadDataInDataBase.LoadListGroupInvite(groupInviteList);
        assertEquals(true,b);
    }
}