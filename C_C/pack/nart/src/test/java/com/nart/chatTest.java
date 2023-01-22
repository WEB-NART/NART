//package com.nart;
//
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.nart.pojo.FriendChat;
//import com.nart.pojo.GroupChat;
//import com.nart.pojo.UserGroup;
//import com.nart.service.ChatService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//@SpringBootTest
//public class chatTest {
//    @Test
//    void contextLoads() {
//    }
//    @Autowired
//    private ChatService chatService;
//
//
//    @Test
//    public void TestsendFriendMsg(){
//        FriendChat friendChat = new FriendChat();
//        friendChat.setSenderId("1");
//        friendChat.setReceiverId("2");
//        friendChat.setType("wenzi");
//        friendChat.setMsg("dwdwde");
//        friendChat.setDate(123132323L);
//        boolean b = chatService.sendFriendMsg(friendChat);
//        System.out.println(b);
//    }
//
//    @Test
//    public void TestsendGroupMsg(){
//        GroupChat groupChat = new GroupChat();
//        groupChat.setGroupId("1");
//        groupChat.setSenderId("1");
//        groupChat.setDate(112323L);
//
//        groupChat.setMsg("sfefef");
//        groupChat.setType("tupian");
//        boolean b = chatService.sendGroupMsg(groupChat);
//        System.out.println(b);
//
//    }
//
//    @Test
//    public void TestrecivicefriendMsg(){
//        IPage<UserGroup> page=new Page<>(1,3);
//        List<FriendChat> FriendChats = chatService.recivicefriendMsg("2",page);
//        System.out.println(FriendChats);
//    }
//
//    @Test
//    public void TestrecivicegroupMsg(){
//        IPage<UserGroup> page=new Page<>(1,3);
//        List<GroupChat> GroupChats = chatService.recivicegroupMsg("1",page);
//        System.out.println(GroupChats);
//
//    }
//
//    @Test
//    public void TestshowFriendHistory(){
//        IPage<UserGroup> page=new Page<>(1,3);
//        List<FriendChat> FriendChats = chatService.showFriendHistory("1",page);
//        System.out.println(FriendChats);
//    }
//
//}
