package com.nart.service.impl;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nart.dao.FriendDao;
import com.nart.dao.FriendReqDao;
import com.nart.dao.UserDao;
import com.nart.pojo.*;
import com.nart.service.ChatService;
import com.nart.service.UserService;
import com.nart.util.UserThreadLocal;
import com.nart.vo.FriendVo;
import com.nart.vo.PageVo;
import com.nart.vo.RequestVo;
import com.nart.vo.UserVo;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendServiceImplTest {

    @Mock
    private FriendDao mockFriendDao;
    @Mock
    private UserDao mockUserDao;
    @Mock
    private UserService mockUserService;
    @Mock
    private FriendReqDao mockFriendReqDAO;
    @Mock
    private ChatService mockChatService;

    private FriendServiceImpl friendServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        friendServiceImplUnderTest = new FriendServiceImpl(mockFriendDao, mockUserDao, mockUserService,
                mockFriendReqDAO, mockChatService);
        User user = new User();
        user.setId("id");
        UserThreadLocal.put(user);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), Friend.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), FriendReq.class);
    }

    @Test
    public void testShowFriendList() {
        // Setup
        PageVo p = new PageVo(1, 1);
        final IPage<Friend> page = p.toIPage(Friend.class);
        final FriendVo friendVo = new FriendVo();
        friendVo.setId("id");
        friendVo.setName("friendName");
        friendVo.setAvatar("avatar");
        friendVo.setState(0);
        friendVo.setNewMsg(false);
        final List<FriendVo> expectedResult = Arrays.asList(friendVo);
        IPage<Friend> objectPage = new Page<>();
        objectPage.setRecords(Arrays.asList(friendVo.toFriend()));
        when(mockFriendDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(objectPage);

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("id");
        user.setPwd("pwd");
        user.setTpwd("tpwd");
        user.setSalt("salt");
        user.setAvatar("avatar");
        user.setName("friendName");
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
        when(mockUserDao.selectById("id")).thenReturn(user);

        //when(mockChatService.existNewMsg("rid", true, 0L)).thenReturn(false);

        // Run the test
        final List<FriendVo> result = friendServiceImplUnderTest.showFriendList(page, "userId");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testShowFriendList_UserOnline1() {
        // Setup
        PageVo p = new PageVo(1, 1);
        final IPage<Friend> page = p.toIPage(Friend.class);
        final FriendVo friendVo = new FriendVo();
        friendVo.setId("id");
        friendVo.setName("friendName");
        friendVo.setAvatar("avatar");
        friendVo.setState(0);
        friendVo.setNewMsg(false);
        final List<FriendVo> expectedResult = Arrays.asList(friendVo);
        IPage<Friend> objectPage = new Page<>();
        objectPage.setRecords(Arrays.asList(friendVo.toFriend()));
        when(mockFriendDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(objectPage);

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("id");
        user.setPwd("pwd");
        user.setTpwd("tpwd");
        user.setSalt("salt");
        user.setAvatar("avatar");
        user.setName("friendName");
        user.setTel("tel");
        user.setAddress("address");
        user.setEmail("email");
        user.setAge("age");
        user.setUserOnline(1);
        user.setPower(0);
        user.setState(0);
        final Friend friend = new Friend();
        friend.setId("id");
        user.setFriendList(Arrays.asList(friend));
        when(mockUserDao.selectById("id")).thenReturn(user);

        //when(mockChatService.existNewMsg("rid", true, 0L)).thenReturn(false);

        // Run the test
        final List<FriendVo> result = friendServiceImplUnderTest.showFriendList(page, "userId");
        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testSearchFriend() {
        // Setup
        PageVo p = new PageVo(1, 1);
        final IPage<Friend> page = p.toIPage(Friend.class);
        final UserVo userVo = new UserVo();
        userVo.setId("id");
        userVo.setAvatar("avatar");
        userVo.setUname("friendName");
        userVo.setPwd("pwd");
        userVo.setEmail("email");
        userVo.setBirthday("age");
        userVo.setPhone("tel");
        userVo.setAddress("address");
        userVo.setPower(0);
        userVo.setLock(0);
        final List<UserVo> expectedResult = Arrays.asList(userVo);

        // Configure FriendDao.selectList(...).
        final Friend friend = new Friend();
        friend.setId("id");
        friend.setUid("rid");
        friend.setFid("rid");
        friend.setState(0);
        friend.setLeaveTime(0L);
        friend.setName("friendName");
        friend.setEmail("email");
        friend.setAvatar("avatar");
        friend.setOnline(false);
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        friend.setStatusList(Arrays.asList(status));
        friend.setNewMessage(false);
        final List<Friend> friendList = Arrays.asList(friend);
        when(mockFriendDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(friendList);

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("id");
        user.setPwd("pwd");
        user.setTpwd("tpwd");
        user.setSalt("salt");
        user.setAvatar("avatar");
        user.setName("friendName");
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
        when(mockUserDao.selectById("rid")).thenReturn(user);

        // Run the test
        final List<UserVo> result = friendServiceImplUnderTest.searchFriend("name", page);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testSearchFriend_NotContainName() {
        // Setup
        PageVo p = new PageVo(1, 1);
        final IPage<Friend> page = p.toIPage(Friend.class);
        // Configure FriendDao.selectList(...).
        final Friend friend = new Friend();
        friend.setId("id");
        friend.setUid("rid");
        friend.setFid("rid");
        friend.setState(0);
        friend.setLeaveTime(0L);
        friend.setName("friendName");
        friend.setEmail("email");
        friend.setAvatar("avatar");
        friend.setOnline(false);
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        friend.setStatusList(Arrays.asList(status));
        friend.setNewMessage(false);
        final List<Friend> friendList = Arrays.asList(friend);
        when(mockFriendDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(friendList);

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("id");
        user.setPwd("pwd");
        user.setTpwd("tpwd");
        user.setSalt("salt");
        user.setAvatar("avatar");
        user.setName("friendName");
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
        when(mockUserDao.selectById("rid")).thenReturn(user);

        // Run the test
        final List<UserVo> result = friendServiceImplUnderTest.searchFriend("1111", page);
        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testSearchFriend_FriendDaoReturnsNoItems() {
        // Setup
        PageVo p = new PageVo(1, 1);
        final IPage<User> page = p.toIPage(User.class);
        when(mockFriendDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("id");
        user.setPwd("pwd");
        user.setTpwd("tpwd");
        user.setSalt("salt");
        user.setAvatar("avatar");
        user.setName("friendName");
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
        //when(mockUserDao.selectById("rid")).thenReturn(user);

        // Run the test
        final List<UserVo> result = friendServiceImplUnderTest.searchFriend("name", page);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testDelFriend() {
        // Setup
        when(mockFriendDao.delete(any(LambdaQueryWrapper.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = friendServiceImplUnderTest.delFriend("fid", "uid");
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        final boolean result2 = friendServiceImplUnderTest.delFriend("fid", "uid");
        // Verify the results
        assertThat(result2).isTrue();
    }

    @Test
    public void testChangeFriendState() {
        // Setup
        when(mockFriendDao.update(any(Friend.class), any(LambdaQueryWrapper.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = friendServiceImplUnderTest.changeFriendState("fid", "uid", 0);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        final boolean result2 = friendServiceImplUnderTest.changeFriendState("fid", "uid", 0);
        // Verify the results
        assertThat(result2).isTrue();
    }

    @Test
    public void testShowReqList() {
        // Setup
        PageVo p = new PageVo(1, 1);
        final IPage<GroupChat> page = p.toIPage(GroupChat.class);
        final RequestVo requestVo = new RequestVo();
        requestVo.setId("id");
        requestVo.setFriendId("friendId");
        requestVo.setMsg("msg");
        requestVo.setFriendName("friendName");
        requestVo.setFriendAvatar("avatar");
        requestVo.setSenderName("friendName");
        final List<RequestVo> expectedResult = Arrays.asList(requestVo);
        IPage<FriendReq> objectPage = new Page<>();
        objectPage.setRecords(Arrays.asList(requestVo.tofRequest()));
        when(mockFriendReqDAO.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(objectPage);

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("id");
        user.setPwd("pwd");
        user.setTpwd("tpwd");
        user.setSalt("salt");
        user.setAvatar("avatar");
        user.setName("friendName");
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
        when(mockUserDao.selectById("friendId")).thenReturn(user);

        // Run the test
        final List<RequestVo> result = friendServiceImplUnderTest.showReqList(page, "sid");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testSendFriendReq() {
        // Setup
        when(mockFriendReqDAO.insert(any(FriendReq.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = friendServiceImplUnderTest.sendFriendReq("rid", "rid", "msg");
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        final boolean result2 = friendServiceImplUnderTest.sendFriendReq("rid", "rid", "msg");
        // Verify the results
        assertThat(result2).isTrue();
    }

    @Test
    public void testRespFriendReq() {
        // Setup
        // Configure FriendReqDao.selectById(...).
        final FriendReq friendReq = new FriendReq();
        friendReq.setId("id");
        friendReq.setSenderId("rid");
        friendReq.setReceiverId("rid");
        friendReq.setMsg("msg");
        friendReq.setDate(0L);
        when(mockFriendReqDAO.selectById("reqId")).thenReturn(friendReq);

        Friend friend = new Friend();
        friend.setUid("rid");
        friend.setFid("rid");
        when(mockFriendDao.insert(friend)).thenReturn(0,1);
        when(mockFriendReqDAO.deleteById("reqId")).thenReturn(0, 1);

        // Run the test
        final boolean result = friendServiceImplUnderTest.respFriendReq("reqId", false);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        final boolean result2 = friendServiceImplUnderTest.respFriendReq("reqId", false);
        // Verify the results
        assertThat(result2).isTrue();

        // Run the test
        final boolean result3 = friendServiceImplUnderTest.respFriendReq("reqId", true);
        // Verify the results
        assertThat(result3).isTrue();

        // Run the test
        final boolean result4 = friendServiceImplUnderTest.respFriendReq("reqId", true);
        // Verify the results
        assertThat(result4).isTrue();

        when(mockFriendDao.insert(friend)).thenReturn(1);
        // Run the test
        final boolean result5 = friendServiceImplUnderTest.respFriendReq("reqId", true);
        // Verify the results
        assertThat(result5).isTrue();
    }

    @Test
    public void testSearchNew() {
        // Setup
        final PageVo pageVo = new PageVo(0, 0);
        final UserVo userVo = new UserVo();
        userVo.setId("id");
        userVo.setAvatar("avatar");
        userVo.setUname("uname");
        userVo.setPwd("pwd");
        userVo.setEmail("email");
        userVo.setBirthday("birthday");
        userVo.setPhone("phone");
        userVo.setAddress("address");
        userVo.setPower(0);
        userVo.setLock(0);
        final List<UserVo> expectedResult = Arrays.asList(userVo);
        IPage<User> objectPage = new Page<>();
        objectPage.setRecords(Arrays.asList(userVo.toUser()));
        when(mockUserService.searchNew("name", new PageVo(0, 0))).thenReturn(objectPage);

        // Run the test
        final List<UserVo> result = friendServiceImplUnderTest.searchNew("name", pageVo);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindAllFriends() {
        // Setup
        final Friend friend = new Friend();
        friend.setId("id");
        friend.setUid("rid");
        friend.setFid("rid");
        friend.setState(0);
        friend.setLeaveTime(0L);
        friend.setName("friendName");
        friend.setEmail("email");
        friend.setAvatar("avatar");
        friend.setOnline(false);
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        friend.setStatusList(Arrays.asList(status));
        friend.setNewMessage(false);
        final List<Friend> expectedResult = Arrays.asList(friend);

        // Configure FriendDao.selectList(...).
        final Friend friend1 = new Friend();
        friend1.setId("id");
        friend1.setUid("rid");
        friend1.setFid("rid");
        friend1.setState(0);
        friend1.setLeaveTime(0L);
        friend1.setName("friendName");
        friend1.setEmail("email");
        friend1.setAvatar("avatar");
        friend1.setOnline(false);
        final Status status1 = new Status();
        status1.setId("id");
        status1.setSenderId("senderId");
        status1.setText("text");
        status1.setPics("pics");
        friend1.setStatusList(Arrays.asList(status1));
        friend1.setNewMessage(false);
        final List<Friend> friendList = Arrays.asList(friend1);
        when(mockFriendDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(friendList);

        // Run the test
        final List<Friend> result = friendServiceImplUnderTest.findAllFriends("uid");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindAllFriends_FriendDaoReturnsNoItems() {
        // Setup
        when(mockFriendDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<Friend> result = friendServiceImplUnderTest.findAllFriends("uid");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
