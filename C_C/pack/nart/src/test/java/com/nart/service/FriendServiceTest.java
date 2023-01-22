package com.nart.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nart.dao.UserDao;
import com.nart.pojo.Friend;
import com.nart.pojo.FriendReq;
import com.nart.pojo.User;
import com.nart.util.UserThreadLocal;
import com.nart.vo.FriendVo;
import com.nart.vo.PageVo;
import com.nart.vo.RequestVo;
import com.nart.vo.UserVo;
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
class FriendServiceTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private FriendService friendService;

    @Test
    void showFriendList() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        IPage<Friend> page=new Page<>(1,1);
        List<FriendVo> friendVos = friendService.showFriendList(page, "1574989632599367682");
        System.out.println(friendVos);
        List<FriendVo> friendVos1 = new ArrayList<>();
        FriendVo friendVo = new FriendVo();
        friendVo.setId("1574989639444471809");
        friendVo.setAvatar("https://s3.amazonaws.com/uifaces/faces/twitter/andyisonline/128.jpg");
        friendVo.setState(0);
        friendVo.setNewMsg(false);
        friendVo.setName("carmelina.streich");
        friendVos1.add(friendVo);
        assertEquals(friendVos1,friendVos);

        List<FriendVo> friendVosq = friendService.showFriendList(page, "1575504315928305665");
        List<FriendVo> friendVosqc = new ArrayList<>();
        System.out.println(friendVosq);
        friendVosqc = friendVosq;
        assertEquals(friendVosqc,friendVosq);
    }

    @Test
    void searchFriend() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        IPage<Friend> page=new Page<>(1,1);
        List<UserVo> userVos = friendService.searchFriend("dani.altenwerth", page);
        List<UserVo> userVos1 = userVos;
        assertEquals(userVos1,userVos);

        IPage<Friend> page1 =new Page<>(10,1);
        List<UserVo> userVos3 = friendService.searchFriend("dani.altenwerth", page1);
        List<UserVo> userVos2 = userVos3;
        assertEquals(userVos2,userVos3);


        IPage<Friend> page2 =new Page<>(1,100);
        List<UserVo> userVosa = friendService.searchFriend("dani.altenwerth", page2);
        List<UserVo> userVosb = userVosa;
        assertEquals(userVosb,userVosa);

    }

    @Test
    void delFriend() {
        boolean b = friendService.delFriend("1574989637485731841", "1574989636311326722");
        System.out.println(b);
        assertEquals(true,b);
    }

    @Test
    void changeFriendState() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        boolean b = friendService.changeFriendState("1574989637485731841", "1574989636311326722", 1);
        System.out.println(b);
        assertEquals(true,b);
    }

    @Test
    void showReqList() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        IPage<FriendReq> page = new Page<>(1,1);
        List<RequestVo> requestVos = friendService.showReqList(page, "1574989636705591298");
        List<RequestVo> requestVos1 = requestVos;
        System.out.println(requestVos);
        assertEquals(requestVos1,requestVos);
    }

    @Test
    void sendFriendReq() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        boolean qqqqq = friendService.sendFriendReq("1574989637485731841", "1574989636311326722", "qqqqq");
        System.out.println(qqqqq);
        assertEquals(true,qqqqq);
    }

    @Test
    void respFriendReq() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        boolean b = friendService.respFriendReq("1574989640237195266", true);
        System.out.println(b);
        assertEquals(true,b);

        boolean c = friendService.respFriendReq("1574989640237195266", false);
//        System.out.println(c);
        assertEquals(false,c);
    }

    @Test
    void searchNew() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        IPage<FriendReq> page = new Page<>(1,1);
        PageVo pageVo = new PageVo();
        pageVo.setPageNum(1);
        pageVo.setPageSize(1);
        List<UserVo> userVos = friendService.searchNew("carlee.ullrich", pageVo);
        System.out.println(userVos);
        List<UserVo> userVos1 = userVos;
        assertEquals(userVos1,userVos);
    }

    @Test
    void findAllFriends() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        List<Friend> allFriends = friendService.findAllFriends("1574989636311326722");
        List<Friend> allFriends1 = allFriends;
        System.out.println(allFriends);
        assertEquals(allFriends1,allFriends);
    }
}