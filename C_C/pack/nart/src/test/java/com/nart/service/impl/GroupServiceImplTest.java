package com.nart.service.impl;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nart.dao.GroupDao;
import com.nart.dao.GroupInviteDao;
import com.nart.dao.UserDao;
import com.nart.dao.UserGroupDao;
import com.nart.pojo.*;
import com.nart.service.ChatService;
import com.nart.util.UserThreadLocal;
import com.nart.vo.GroupVo;
import com.nart.vo.InviteVo;
import com.nart.vo.PageVo;
import com.nart.vo.UserVo;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

    @Mock
    private UserGroupDao mockUserGroupDao;
    @Mock
    private UserDao mockUserDao;
    @Mock
    private GroupDao mockGroupDao;
    @Mock
    private GroupInviteDao mockGroupInviteDao;
    @Mock
    private ChatService mockChatService;

    private GroupServiceImpl groupServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        groupServiceImplUnderTest = new GroupServiceImpl(mockUserGroupDao, mockUserDao, mockGroupDao,
                mockGroupInviteDao, mockChatService);
        User user = new User();
        user.setId("id");
        UserThreadLocal.put(user);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), GroupInvite.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), UserGroup.class);
    }

    @Test
    public void testShowGroupMebList() {
        // Setup
        final UserVo userVo = new UserVo();
        userVo.setId("id");
        userVo.setAvatar("avatar");
        userVo.setUname("uname");
        //userVo.setPwd("pwd");
        //userVo.setEmail("email");
        //userVo.setBirthday("birthday");
        //userVo.setPhone("phone");
        //userVo.setAddress("address");
        //userVo.setPower(0);
        userVo.setLock(0);
        final List<UserVo> expectedResult = Arrays.asList(userVo);

        // Configure UserGroupDao.selectList(...).
        final UserGroup userGroup = new UserGroup();
        userGroup.setId("id");
        userGroup.setUid("uid");
        userGroup.setGid("groupId");
        userGroup.setState(0);
        userGroup.setJoinLevel(0);
        userGroup.setUserLevelTime(0L);
        final List<UserGroup> userGroupList = Arrays.asList(userGroup);
        when(mockUserGroupDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(userGroupList);

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("id");
        user.setPwd("pwd");
        user.setTpwd("tpwd");
        user.setSalt("salt");
        user.setAvatar("avatar");
        user.setName("uname");
        user.setTel("tel");
        user.setAddress("address");
        user.setEmail("email");
        user.setAge("age");
        user.setUserOnline(0);
        user.setPower(0);
        user.setState(0);
        final Friend friend = new Friend();
        friend.setId("id");
        user.setFriendList(Arrays.asList(friend));
        when(mockUserDao.selectById("uid")).thenReturn(user);

        // Run the test
        final List<UserVo> result = groupServiceImplUnderTest.showGroupMebList("gid");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testShowGroupMebList_UserGroupDaoReturnsNoItems() {
        // Setup
        when(mockUserGroupDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("uid");
        user.setPwd("pwd");
        user.setTpwd("tpwd");
        user.setSalt("salt");
        user.setAvatar("avatar");
        user.setName("name");
        user.setTel("tel");
        user.setAddress("address");
        user.setEmail("email");
        user.setAge("age");
        user.setUserOnline(0);
        user.setPower(0);
        user.setState(0);
        final Friend friend = new Friend();
        friend.setId("id");
        user.setFriendList(Arrays.asList(friend));
        //when(mockUserDao.selectById("uid")).thenReturn(user);

        // Run the test
        final List<UserVo> result = groupServiceImplUnderTest.showGroupMebList("gid");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testChangeGroupInfo() {
        // Setup
        final Group group = new Group();
        group.setId("groupId");
        group.setGroupName("groupName");
        group.setNotice("notice");
        group.setAvatar("avatar");
        group.setUserLevel(0);
        final Friend friend = new Friend();
        friend.setId("id");
        friend.setUid("uid");
        friend.setFid("fid");
        friend.setState(0);
        friend.setLeaveTime(0L);
        friend.setName("name");
        friend.setEmail("email");
        friend.setAvatar("avatar");
        group.setUserList(Arrays.asList(friend));
        group.setNewMessage(false);

        when(mockGroupDao.updateById(any(Group.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = groupServiceImplUnderTest.changeGroupInfo(group);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        final boolean result2 = groupServiceImplUnderTest.changeGroupInfo(group);
        // Verify the results
        assertThat(result2).isTrue();
    }

    @Test
    public void testShowGroupList() {
        // Setup
        PageVo p = new PageVo(1, 1);
        final IPage<UserGroup> page = p.toIPage(UserGroup.class);
        final GroupVo groupVo = new GroupVo();
        groupVo.setId("id");
        groupVo.setName("name");
        groupVo.setAvatar("avatar");
        groupVo.setNotice("notice");
        groupVo.setState(0);
        groupVo.setNewMsg(false);
        final List<GroupVo> expectedResult = Arrays.asList(groupVo);

        IPage<UserGroup> objectPage = new Page<>();
        UserGroup userGroup = new UserGroup();
        userGroup.setGid("id");
        userGroup.setState(0);
        objectPage.setRecords(Arrays.asList(userGroup));
        when(mockUserGroupDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(objectPage);

        // Configure GroupDao.selectById(...).
        final Group group = new Group();
        group.setId("id");
        group.setGroupName("name");
        group.setNotice("notice");
        group.setAvatar("avatar");
        group.setUserLevel(0);
        final Friend friend = new Friend();
        friend.setId("id");
        friend.setUid("uid");
        friend.setFid("fid");
        friend.setState(0);
        friend.setLeaveTime(0L);
        friend.setName("name");
        friend.setEmail("email");
        friend.setAvatar("avatar");
        group.setUserList(Arrays.asList(friend));
        group.setNewMessage(false);
        when(mockGroupDao.selectById("id")).thenReturn(group);

        //when(mockChatService.existNewMsg("groupId", false, 0L)).thenReturn(false);

        // Run the test
        final List<GroupVo> result = groupServiceImplUnderTest.showGroupList(page);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testLeaveGroup() {
        // Setup
        when(mockUserGroupDao.delete(any(LambdaQueryWrapper.class))).thenReturn(1);

        // Run the test
        final boolean result = groupServiceImplUnderTest.leaveGroup("gid", "uid");

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    public void testChangeGroupState() {
        // Setup
        when(mockUserGroupDao.update(eq(new UserGroup()), any(LambdaQueryWrapper.class))).thenReturn(1);

        // Run the test
        final boolean result = groupServiceImplUnderTest.changeGroupState("gid", "uid", 0);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    public void testShowInviteList() {
        // Setup
        PageVo p = new PageVo(1, 1);
        final IPage<GroupInvite> page = p.toIPage(GroupInvite.class);
        final InviteVo inviteVo = new InviteVo();
        inviteVo.setId("id");
        inviteVo.setGroupId("groupId");
        inviteVo.setMsg("msg");
        inviteVo.setGroupName("groupName");
        inviteVo.setGroupAvatar("avatar");
        inviteVo.setSenderName("name");
        inviteVo.setReceiverId("uid");
        final List<InviteVo> expectedResult = Arrays.asList(inviteVo);

        IPage<GroupInvite> objectPage = new Page<>();
        GroupInvite groupInvite = inviteVo.toGI();
        groupInvite.setSenderId("sid");
        groupInvite.setReceiverId("uid");
        objectPage.setRecords(Arrays.asList(groupInvite));
        when(mockGroupInviteDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(objectPage);

        // Configure GroupDao.selectById(...).
        final Group group = new Group();
        group.setId("groupId");
        group.setGroupName("groupName");
        group.setNotice("notice");
        group.setAvatar("avatar");
        group.setUserLevel(0);
        final Friend friend = new Friend();
        friend.setId("id");
        friend.setUid("uid");
        friend.setFid("fid");
        friend.setState(0);
        friend.setLeaveTime(0L);
        friend.setName("name");
        friend.setEmail("email");
        friend.setAvatar("avatar");
        group.setUserList(Arrays.asList(friend));
        group.setNewMessage(false);
        when(mockGroupDao.selectById("groupId")).thenReturn(group);

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("uid");
        user.setPwd("pwd");
        user.setTpwd("tpwd");
        user.setSalt("salt");
        user.setAvatar("avatar");
        user.setName("name");
        user.setTel("tel");
        user.setAddress("address");
        user.setEmail("email");
        user.setAge("age");
        user.setUserOnline(0);
        user.setPower(0);
        user.setState(0);
        final Friend friend1 = new Friend();
        friend1.setId("id");
        user.setFriendList(Arrays.asList(friend1));
        when(mockUserDao.selectById("sid")).thenReturn(user);

        // Run the test
        final List<InviteVo> result = groupServiceImplUnderTest.showInviteList(page);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testSendInvite() {
        // Setup
        final GroupInvite groupInvite = new GroupInvite();
        groupInvite.setId("id");
        groupInvite.setGroupId("groupId");
        groupInvite.setSenderId("uid");
        groupInvite.setReceiverId("uid");
        groupInvite.setMsg("msg");
        groupInvite.setDate(0L);

        when(mockGroupInviteDao.insert(any(GroupInvite.class))).thenReturn(1);

        // Run the test
        final boolean result = groupServiceImplUnderTest.sendInvite(groupInvite);
        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    public void testRespGroupInvite() {
        // Setup
        // Configure GroupInviteDao.selectById(...).
        final GroupInvite groupInvite = new GroupInvite();
        groupInvite.setId("id");
        groupInvite.setGroupId("groupId");
        groupInvite.setSenderId("uid");
        groupInvite.setReceiverId("uid");
        groupInvite.setMsg("msg");
        groupInvite.setDate(0L);
        when(mockGroupInviteDao.selectById("InviteId")).thenReturn(groupInvite);

        // Configure GroupDao.selectById(...).
        final Group group = new Group();
        group.setId("groupId");
        group.setGroupName("groupName");
        group.setNotice("notice");
        group.setAvatar("avatar");
        group.setUserLevel(0);
        final Friend friend = new Friend();
        friend.setId("id");
        friend.setUid("uid");
        friend.setFid("fid");
        friend.setState(0);
        friend.setLeaveTime(0L);
        friend.setName("name");
        friend.setEmail("email");
        friend.setAvatar("avatar");
        group.setUserList(Arrays.asList(friend));
        group.setNewMessage(false);
        when(mockGroupDao.selectById("groupId")).thenReturn(group);
        when(mockUserGroupDao.insert(any(UserGroup.class))).thenReturn(0, 1);
        when(mockGroupInviteDao.deleteById("InviteId")).thenReturn(0, 1);

        // Run the test
        final boolean result = groupServiceImplUnderTest.respGroupInvite("InviteId", false);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        final boolean result2 = groupServiceImplUnderTest.respGroupInvite("InviteId", false);
        // Verify the results
        assertThat(result2).isTrue();

        // Run the test
        final boolean result3 = groupServiceImplUnderTest.respGroupInvite("InviteId", true);
        // Verify the results
        assertThat(result3).isFalse();

        // Run the test
        final boolean result4 = groupServiceImplUnderTest.respGroupInvite("InviteId", true);
        // Verify the results
        assertThat(result4).isTrue();


        when(mockUserGroupDao.insert(any(UserGroup.class))).thenReturn(1);
        // Run the test
        final boolean result5 = groupServiceImplUnderTest.respGroupInvite("InviteId", true);
        // Verify the results
        assertThat(result5).isTrue();
    }

    @Test
    public void testCreateGroup() {
        // Setup
        //when(mockGroupDao.insert(new Group())).thenReturn(0);

        // Configure GroupDao.selectOne(...).
        final Group group = new Group();
        group.setId("groupId");
        group.setGroupName("groupName");
        group.setNotice("notice");
        group.setAvatar("avatar");
        group.setUserLevel(0);
        final Friend friend = new Friend();
        friend.setId("id");
        friend.setUid("uid");
        friend.setFid("fid");
        friend.setState(0);
        friend.setLeaveTime(0L);
        friend.setName("name");
        friend.setEmail("email");
        friend.setAvatar("avatar");
        group.setUserList(Arrays.asList(friend));
        group.setNewMessage(false);
        when(mockGroupDao.selectOne(any(LambdaQueryWrapper.class))).thenReturn(group);

        when(mockGroupDao.insert(any(Group.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = groupServiceImplUnderTest.createGroup("groupName", "uid");
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        final boolean result2 = groupServiceImplUnderTest.createGroup("-1", "uid");
        // Verify the results
        assertThat(result2).isTrue();
    }

    @Test
    public void testJoinGroup() {
        // Setup
        // Configure GroupDao.selectById(...).
        final Group group = new Group();
        group.setId("groupId");
        group.setGroupName("groupName");
        group.setNotice("notice");
        group.setAvatar("avatar");
        group.setUserLevel(0);
        final Friend friend = new Friend();
        friend.setId("id");
        friend.setUid("uid");
        friend.setFid("fid");
        friend.setState(0);
        friend.setLeaveTime(0L);
        friend.setName("name");
        friend.setEmail("email");
        friend.setAvatar("avatar");
        group.setUserList(Arrays.asList(friend));
        group.setNewMessage(false);
        when(mockGroupDao.selectById("groupId")).thenReturn(group);

        //when(mockUserGroupDao.insert(new UserGroup())).thenReturn(0);
        when(mockGroupDao.updateById(group)).thenReturn(0, 1);

        // Run the test
        final boolean result = groupServiceImplUnderTest.joinGroup("groupId");
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        final boolean result2 = groupServiceImplUnderTest.joinGroup("groupId");
        // Verify the results
        assertThat(result2).isTrue();
    }

    @Test
    public void testFindAllMembers() {
        // Setup
        // Configure UserGroupDao.selectList(...).
        final UserGroup userGroup = new UserGroup();
        userGroup.setId("id");
        userGroup.setUid("uid");
        userGroup.setGid("groupId");
        userGroup.setState(0);
        userGroup.setJoinLevel(0);
        userGroup.setUserLevelTime(0L);
        final List<UserGroup> userGroupList = Arrays.asList(userGroup);
        when(mockUserGroupDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(userGroupList);

        // Run the test
        final Set<String> result = groupServiceImplUnderTest.findAllMembers("gid");

        // Verify the results
        assertThat(result).isEqualTo(new HashSet<>(Arrays.asList("uid")));
    }

    @Test
    public void testFindAllMembers_UserGroupDaoReturnsNoItems() {
        // Setup
        when(mockUserGroupDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        // Run the test
        final Set<String> result = groupServiceImplUnderTest.findAllMembers("gid");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptySet());
    }

    @Test
    public void testFindGroup() {
        // Setup
        final Group expectedResult = new Group();
        expectedResult.setId("groupId");
        expectedResult.setGroupName("groupName");
        expectedResult.setNotice("notice");
        expectedResult.setAvatar("avatar");
        expectedResult.setUserLevel(0);
        final Friend friend = new Friend();
        friend.setId("id");
        friend.setUid("uid");
        friend.setFid("fid");
        friend.setState(0);
        friend.setLeaveTime(0L);
        friend.setName("name");
        friend.setEmail("email");
        friend.setAvatar("avatar");
        expectedResult.setUserList(Arrays.asList(friend));
        expectedResult.setNewMessage(false);

        // Configure GroupDao.selectOne(...).
        final Group group = new Group();
        group.setId("groupId");
        group.setGroupName("groupName");
        group.setNotice("notice");
        group.setAvatar("avatar");
        group.setUserLevel(0);
        final Friend friend1 = new Friend();
        friend1.setId("id");
        friend1.setUid("uid");
        friend1.setFid("fid");
        friend1.setState(0);
        friend1.setLeaveTime(0L);
        friend1.setName("name");
        friend1.setEmail("email");
        friend1.setAvatar("avatar");
        group.setUserList(Arrays.asList(friend1));
        group.setNewMessage(false);
        when(mockGroupDao.selectOne(any(LambdaQueryWrapper.class))).thenReturn(group);

        // Run the test
        final Group result = groupServiceImplUnderTest.findGroup("groupName");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
