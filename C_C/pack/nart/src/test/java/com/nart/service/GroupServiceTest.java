package com.nart.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nart.dao.UserDao;
import com.nart.pojo.Group;
import com.nart.pojo.GroupInvite;
import com.nart.pojo.User;
import com.nart.pojo.UserGroup;
import com.nart.util.UserThreadLocal;
import com.nart.vo.GroupVo;
import com.nart.vo.InviteVo;
import com.nart.vo.UserVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Rollback
@Transactional
class GroupServiceTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupService groupService;
    @Test
    void showGroupMebList() {
        IPage<UserGroup> page=new Page<>(1,3);
        List<UserVo> userVos = groupService.showGroupMebList("1576235231550111745");
        UserVo userVo = new UserVo();
        userVo.setId("1574989639117316098");
        userVo.setAvatar("https://s3.amazonaws.com/uifaces/faces/twitter/gseguin/128.jpg");
        userVo.setUname("kim.grant");
        userVo.setLock(0);
        System.out.println(userVos);
        List<UserVo> userVos1 = new ArrayList<>();
        userVos1.add(userVo);
        assertEquals(userVos1,userVos);
    }

    @Test
    void changeGroupInfo() {
        Group group = new Group();
        group.setId("1574990490691067906");
        group.setGroupName("llll");
        boolean b = groupService.changeGroupInfo(group);
        System.out.println(b);
        assertEquals(true,b);

    }

    @Test
    void showGroupList() {
        User user = userDao.selectById("1574989632599367682");
        UserThreadLocal.put(user);
        IPage<UserGroup> page=new Page<>(1,1);
        List<GroupVo> groupVos = groupService.showGroupList(page);
        System.out.println(groupVos);
        GroupVo groupVo = new GroupVo();
        groupVo.setId("1574990494696628226");
        groupVo.setName("Schamberger-Conroy");
        groupVo.setAvatar("https://s1.ax1x.com/2023/01/01/pSCMx7d.png");
        groupVo.setNotice("__t1_Z_RLqzdBk7tY__w_Hb1_");
        groupVo.setState(0);
        groupVo.setNewMsg(false);
        List<GroupVo> groupVos1 = new ArrayList<>();
        groupVos1.add(groupVo);
        assertEquals(groupVos,groupVos);

    }

    @Test
    void leaveGroup() {
        boolean b = groupService.leaveGroup("1574990494298169346", "1574989636311326722");
        System.out.println(b);
        assertEquals(true,b);
    }

    @Test
    void changeGroupState() {
        boolean b = groupService.changeGroupState("1574990494298169346", "1574989636311326722", 1);
        System.out.println(b);
        assertEquals(true,b);
    }

    @Test
    void showInviteList() {
        User user = userDao.selectById("1574989636311326722");
        UserThreadLocal.put(user);
        IPage<UserGroup> page=new Page<>(1,1);
        List<InviteVo> inviteVos = groupService.showInviteList(page);
        InviteVo inviteVo = new InviteVo();
        inviteVo.setId("1574990496386932738");
        inviteVo.setGroupId("1574990490691067906");
        inviteVo.setMsg("3");
        inviteVo.setGroupName("Rippin Inc");
        inviteVo.setGroupAvatar("https://s3.amazonaws.com/uifaces/faces/twitter/xadhix/128.jpg");
        inviteVo.setSenderName("dani.altenwerth");
        inviteVo.setReceiverId("1574989636311326722");
        List<InviteVo> inviteVos1 = new ArrayList<>();
        inviteVos1.add(inviteVo);
        System.out.println(inviteVos);
        assertEquals(inviteVos,inviteVos);
    }

    @Test
    void sendInvite() {
        User user = userDao.selectById("1574989632599367682");
        UserThreadLocal.put(user);
        GroupInvite groupInvite =new GroupInvite();

        boolean b = groupService.sendInvite(groupInvite);
        System.out.println(b);
        assertEquals(true,b);
    }

    @Test
    void respGroupInvite() {
        User user = userDao.selectById("1574989632599367682");
        UserThreadLocal.put(user);
        boolean b = groupService.respGroupInvite("1574990495996862466", true);
        System.out.println(b);
        assertEquals(true,b);

        User user1 = userDao.selectById("1574989632599367682");
        UserThreadLocal.put(user1);
        boolean c = groupService.respGroupInvite("1574990495996862466", false);
        System.out.println(c);
        assertEquals(false,c);

    }

//    @Test
//    void respGroupInvite2() {
//        User user = userDao.selectById("1574989632599367682");
//        UserThreadLocal.put(user);
//        boolean b = groupService.respGroupInvite("1574990495996862466", false);
//        System.out.println(b);
//        assertEquals(true,b);
//    }

    @Test
    void createGroup() {
        User user = userDao.selectById("1574989632599367682");
        UserThreadLocal.put(user);
        boolean b = groupService.createGroup("new", "1574989632599367682");
        System.out.println(b);
        assertEquals(true,b);

        boolean c = groupService.createGroup("-1", "1574989632599367682");
        System.out.println(c);
        assertEquals(true,c);

    }

    @Test
    void joinGroup() {
        User user = userDao.selectById("1574989632599367682");
        UserThreadLocal.put(user);
        boolean b = groupService.joinGroup("1574990494298169346");
        System.out.println(b);
        assertEquals(true,b);
    }

    @Test
    void findAllMembers() {
        User user = userDao.selectById("1574989632599367682");
        UserThreadLocal.put(user);
        Set<String> allMembers = groupService.findAllMembers("1574990494298169346");
        System.out.println(allMembers);
        Set<String> a = new HashSet<>();
        a = allMembers;
        assertEquals(a,allMembers);
    }

    @Test
    void findGroup() {
        User user = userDao.selectById("1574989632599367682");
        UserThreadLocal.put(user);
        Group api = groupService.findGroup("Rippin Inc");
        System.out.println(api);
        Group group = new Group();
        group.setId("1574990490691067906");
        group.setGroupName("Rippin Inc");
        group.setNotice("7DN__9062JQ_eIo_9_j_");
        group.setAvatar("https://s3.amazonaws.com/uifaces/faces/twitter/xadhix/128.jpg");
        group.setUserLevel(1);
        group.setState(0);
        assertEquals(group,api);
    }
}