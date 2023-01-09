package com.nart.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nart.dao.UserDao;
import com.nart.pojo.*;
import com.nart.util.UserThreadLocal;
import com.nart.vo.MessageVo;
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
class ChatServiceTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ChatService chatService;
    @Test
    void sendFriendMsg() {
        FriendChat friendChat = new FriendChat();
        friendChat.setType("text");
        friendChat.setDate(11111L);
        boolean b = chatService.sendFriendMsg(friendChat);
        System.out.println(b);
        assertEquals(true,b);
    }

    @Test
    void sendGroupMsg() {
        GroupChat groupChat = new GroupChat();
        groupChat.setGroupId("1574990494298169346");
        groupChat.setDate(112323L);
        groupChat.setType("text");
        boolean b = chatService.sendGroupMsg(groupChat);
        System.out.println(b);
        assertEquals(true,b);
    }

    @Test
    void receiveFriendMsg() {
        IPage<Friend> page=new Page<>(1,1);
        List<FriendChat> friendChats = chatService.receiveFriendMsg("1574989639444471809", page);
        System.out.println(friendChats);
        List<FriendChat> friendChats1 = friendChats;
        assertEquals(friendChats1,friendChats);
    }

    @Test
    void receiveGroupMsg() {
        IPage<Friend> page=new Page<>(1,1);
        List<GroupChat> groupChats = chatService.receiveGroupMsg("1574990494298169346", page);
        System.out.println(groupChats);
        List<GroupChat> groupChats1 = groupChats;
        assertEquals(groupChats1,groupChats);
    }

    @Test
    void showFriendHistory() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        IPage<UserGroup> page=new Page<>(1,1);
        List<MessageVo> messageVos = chatService.showFriendHistory("1574989638660136961", page);
        System.out.println(messageVos);
        List<MessageVo> messageVos1 = messageVos;
        assertEquals(messageVos1,messageVos);
    }

    @Test
    void showGroupHistory() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        IPage<UserGroup> page=new Page<>(1,1);
        List<MessageVo> messageVos = chatService.showGroupHistory("1574990494298169346", page);
        System.out.println(messageVos);
        List<MessageVo> messageVos1 = messageVos;
        assertEquals(messageVos1,messageVos);
    }

    @Test
    void leaveRoom() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        boolean b = chatService.leaveRoom("1574989637485731841", true);
        assertEquals(true,b);

    }

    @Test
    void existNewMsg() {
        User user = userDao.selectById("1574989639444471809");
        UserThreadLocal.put(user);
        boolean b = chatService.existNewMsg("1574989638265872385", true, 1608904969006L);
        assertEquals(true,b);
    }
}