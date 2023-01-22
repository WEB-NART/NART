//package com.nart;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.nart.dao.UserDao;
//import com.nart.pojo.*;
//import com.nart.service.GroupService;
//import com.nart.service.LoginService;
//import com.nart.util.UserThreadLocal;
//import com.nart.vo.GroupVo;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import sun.nio.cs.ext.MacGreek;
//
//import java.util.List;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class groupTest {
//    @Test
//    void contextLoads() {
//    }
//    @Autowired
//    private GroupService groupService;
//
//    @Autowired
//    private LoginService loginService;
//
//    @Autowired
//    private UserDao userDao;
//
//    @Test
//    public void log(){
//        User user = userDao.selectById("1574989632599367682");
//        UserThreadLocal.put(user);
//        String id = UserThreadLocal.get().getId();
//        System.out.println(id);
////        loginService.login();
//
//    }
//
//
////    @Test
////    public void sendInvite(){
////        GroupInvite groupInvite = new GroupInvite();
////        groupInvite.setReceiverId("1574989639444471809");
////        groupInvite.setMsg("ttt");
////        groupInvite.setGroupId("1574990490691067906");
////        groupService.sendInvite(groupInvite);
////
////    }
//    @Test
//    public void findGroup(){
//        Group api = groupService.findGroup("api");
//        System.out.println(api);
//    }
//
//
//
////    status.setCreateDate(new Date().getTime());
//
////    @Test
////    public void send(){
////        GroupInvite groupInvite =new GroupInvite();
////        groupInvite.setId("1");
////        groupInvite.setSenderId("1574989632599367682");
////        groupInvite.setGroupId("1574990494298169346");
////        groupInvite.setMsg("test");
////        groupInvite.setReceiverId("1574989639444471809");
////        boolean b = groupService.sendInvite(groupInvite);
////        System.out.println(b);
////    }
////
////    @Test
////    public void resp(){
////
////        groupService.respGroupInvite("1574990495602597890",true);
////    }
////
//    @Test
//    public void showGroupList(){
//        User user = userDao.selectById("1574989632599367682");
//        UserThreadLocal.put(user);
//        String id = UserThreadLocal.get().getId();
//        System.out.println(id);
//        IPage<UserGroup> page=new Page<>(1,10);
//        List<GroupVo> groupVos = groupService.showGroupList(page);
//        System.out.println(groupVos);
//    }
//
//
////    @Test
////    public void TestshowGroupMebList(){
////        IPage<UserGroup> page=new Page<>(1,3);
////        List<User> Users = groupService.showGroupMebList("1");
////        System.out.println(Users);
////    }
////
////    @Test
////    public void TestchangeGroupInfo(){
////        Group group = new Group();
////        group.setId("1");
////        group.setGroupName("llll");
////        boolean b = groupService.changeGroupInfo(group);
////        System.out.println(b);
////    }
////
//////    @Test
//////    public void TestshowGroupList(){
//////        IPage<UserGroup> page=new Page<>(1,3);
//////        List<Group> Groups = groupService.showGroupList(page);
//////        System.out.println(Groups);
//////    }
////
////    @Test
////    public void TestleaveGroup(){
//
////    }
////
////    @Test
////    public void TestchangeGroupState(){
////        boolean b = groupService.changeGroupState("1", "1", 1);
////        System.out.println(b);
////    }
////
////    @Test
////    public void showInviteList(){
////        IPage<UserGroup> page=new Page<>(1,3);
////        List<GroupInvite> GroupInvites = groupService.showInviteList(page);
////        System.out.println(GroupInvites);
////
////    }
////
////    @Test
////    public void sendInvite(){
////        GroupInvite groupInvite =new GroupInvite();
////        groupInvite.setId("2");
////        groupInvite.setSenderId("1");
////        groupInvite.setGroupId("1");
////        groupInvite.setMsg("dwdw");
////        groupInvite.setReceiverId("3");
////        boolean b = groupService.sendInvite(groupInvite);
////        System.out.println(b);
////
////    }
////
////    @Test
////    public void respGroupInvite(){
////
////        boolean b = groupService.respGroupInvite("2", true);
////        System.out.println(b);
////
////    }
////
////    @Test
////    public void createGroup(){
////        boolean test = groupService.createGroup("test", "1");
////
////        System.out.println(test);
////    }
////
////    @Test
////    public void joinGroup(){
////        User user = new User();
////        user.setId("3");
////        UserThreadLocal.put(user);
////        boolean b = groupService.joinGroup("1565224110185975810");
////        System.out.println(b);
////    }
//}
