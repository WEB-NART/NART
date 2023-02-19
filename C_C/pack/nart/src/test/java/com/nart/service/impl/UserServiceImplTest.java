package com.nart.service.impl;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.nart.dao.FriendDao;
import com.nart.dao.UserDao;
import com.nart.dao.UserGroupDao;
import com.nart.pojo.Friend;
import com.nart.pojo.Status;
import com.nart.pojo.User;
import com.nart.pojo.UserGroup;
import com.nart.service.DataCounterService;
import com.nart.util.UserThreadLocal;
import com.nart.vo.PageVo;
import com.nart.vo.UserVo;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao mockUserDao;
    @Mock
    private DataCounterService mockDataCounterService;
    @Mock
    private FriendDao mockFriendDao;
    @Mock
    private UserGroupDao mockUserGroupDao;
    private UserServiceImpl userServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        userServiceImplUnderTest = new UserServiceImpl(mockUserDao, mockDataCounterService, mockFriendDao,
                mockUserGroupDao);
        User user = new User();
        UserThreadLocal.put(user);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), User.class);
        Instant.now(
                Clock.fixed(
                        Instant.parse( "2016-01-23T12:34:56Z"), ZoneOffset.UTC
                )
        );
    }

    @AfterEach
    void tearDown() {
        Instant.now(
                Clock.systemUTC()
        );
    }

    @Test
    void testLogin() {
        User user = new User();
        user.setId("userId");
        user.setUserOnline(1);
        // Setup
        when(mockUserDao.updateById(user)).thenReturn(0, 1);

        // Run the test
        final boolean result = userServiceImplUnderTest.login("userId");
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        final boolean result2 = userServiceImplUnderTest.login("userId");
        // Verify the results
        assertThat(result2).isTrue();
    }

    @Test
    void testFindUserByName() {
        // Setup
        final User expectedResult = new User();
        expectedResult.setId("userId");
        expectedResult.setPwd("pwd");
        expectedResult.setTpwd("pwd");
        expectedResult.setSalt("salt");
        expectedResult.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        expectedResult.setName("name");
        expectedResult.setTel("phone");
        expectedResult.setAddress("address");
        expectedResult.setEmail("email");
        expectedResult.setAge("age");
        expectedResult.setUserOnline(0);
        expectedResult.setPower(0);
        expectedResult.setState(0);
        final Friend friend = new Friend();
        friend.setId("id");
        expectedResult.setFriendList(Arrays.asList(friend));

        // Configure UserDao.selectOne(...).
        final User user = new User();
        user.setId("userId");
        user.setPwd("pwd");
        user.setTpwd("pwd");
        user.setSalt("salt");
        user.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        user.setName("name");
        user.setTel("phone");
        user.setAddress("address");
        user.setEmail("email");
        user.setAge("age");
        user.setUserOnline(0);
        user.setPower(0);
        user.setState(0);
        final Friend friend1 = new Friend();
        friend1.setId("id");
        user.setFriendList(Arrays.asList(friend1));
        when(mockUserDao.selectOne(any(LambdaQueryWrapper.class))).thenReturn(user);

        // Run the test
        final User result = userServiceImplUnderTest.findUserByName("uname");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testLogout() {
        // Setup
        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("userId");
        user.setPwd("pwd");
        user.setTpwd("pwd");
        user.setSalt("salt");
        user.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        user.setName("name");
        user.setTel("phone");
        user.setAddress("address");
        user.setEmail("email");
        user.setAge("age");
        user.setUserOnline(1);
        user.setPower(0);
        user.setState(0);
        final Friend friend = new Friend();
        friend.setId("id");
        user.setFriendList(Arrays.asList(friend));
        when(mockUserDao.selectById("userId")).thenReturn(user);

        when(mockUserDao.updateById(any(User.class))).thenReturn(0, 1);
        when(mockDataCounterService.updateOnlineUserAmount(false)).thenReturn(0);

        // Run the test
        final boolean result = userServiceImplUnderTest.logout("userId");
        // Verify the results
        assertThat(result).isFalse();
        verify(mockDataCounterService).updateOnlineUserAmount(false);

        // Run the test
        final boolean result2 = userServiceImplUnderTest.logout("userId");
        // Verify the results
        assertThat(result2).isTrue();
    }

    @Test
    void testLogout_UserOnlineNull() {
        // Setup
        final User user = new User();
        user.setId("userId");
        user.setUserOnline(0);
        when(mockUserDao.selectById("userId")).thenReturn(user);

        // Run the test
        final boolean result = userServiceImplUnderTest.logout("userId");
        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testRegister() {
        // Setup
        final User expectedResult = new User();
        expectedResult.setPwd("pwd");
        expectedResult.setTpwd("pwd");
        expectedResult.setSalt("salt");
        expectedResult.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        expectedResult.setName("name");
        expectedResult.setEmail("email");
        expectedResult.setUserOnline(0);
        expectedResult.setPower(0);
        expectedResult.setState(0);
        final Friend friend = new Friend();
        friend.setId("id");
        //expectedResult.setFriendList(Arrays.asList(friend));

        when(mockUserDao.insert(any(User.class))).thenReturn(0);
        when(mockDataCounterService.updateUserAmount(true)).thenReturn(0);

        // Run the test
        final User result = userServiceImplUnderTest.register("email", "name", "pwd", "salt");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testShowUserInfo() {
        // Setup
        final User expectedResult = new User();
        expectedResult.setId("userId");
        expectedResult.setPwd("pwd");
        expectedResult.setTpwd("pwd");
        expectedResult.setSalt("salt");
        expectedResult.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        expectedResult.setName("name");
        expectedResult.setTel("phone");
        expectedResult.setAddress("address");
        expectedResult.setEmail("email");
        expectedResult.setAge("age");
        expectedResult.setUserOnline(0);
        expectedResult.setPower(0);
        expectedResult.setState(0);
        final Friend friend = new Friend();
        friend.setId("id");
        expectedResult.setFriendList(Arrays.asList(friend));

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("userId");
        user.setPwd("pwd");
        user.setTpwd("pwd");
        user.setSalt("salt");
        user.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        user.setName("name");
        user.setTel("phone");
        user.setAddress("address");
        user.setEmail("email");
        user.setAge("age");
        user.setUserOnline(0);
        user.setPower(0);
        user.setState(0);
        final Friend friend1 = new Friend();
        friend1.setId("id");
        user.setFriendList(Arrays.asList(friend1));
        when(mockUserDao.selectById("userId")).thenReturn(user);

        // Run the test
        final User result = userServiceImplUnderTest.showUserInfo("userId");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testShowUnameAvatar() {
        // Setup
        final User expectedResult = new User();
        expectedResult.setId("userId");
        expectedResult.setPwd("pwd");
        expectedResult.setTpwd("pwd");
        expectedResult.setSalt("salt");
        expectedResult.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        expectedResult.setName("name");
        expectedResult.setTel("phone");
        expectedResult.setAddress("address");
        expectedResult.setEmail("email");
        expectedResult.setAge("age");
        expectedResult.setUserOnline(0);
        expectedResult.setPower(0);
        expectedResult.setState(0);
        final Friend friend = new Friend();
        friend.setId("id");
        expectedResult.setFriendList(Arrays.asList(friend));

        // Configure UserDao.selectOne(...).
        final User user = new User();
        user.setId("userId");
        user.setPwd("pwd");
        user.setTpwd("pwd");
        user.setSalt("salt");
        user.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        user.setName("name");
        user.setTel("phone");
        user.setAddress("address");
        user.setEmail("email");
        user.setAge("age");
        user.setUserOnline(0);
        user.setPower(0);
        user.setState(0);
        final Friend friend1 = new Friend();
        friend1.setId("id");
        user.setFriendList(Arrays.asList(friend1));
        when(mockUserDao.selectOne(any(LambdaQueryWrapper.class))).thenReturn(user);

        // Run the test
        final User result = userServiceImplUnderTest.showUnameAvatar("userId");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testChangeUserInfo() {
        // Setup
        final UserVo userVo = new UserVo();
        userVo.setId("id");
        userVo.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        userVo.setUname("name");
        userVo.setPwd("pwd");
        userVo.setEmail("email");
        userVo.setBirthday("age");
        userVo.setPhone("phone");
        userVo.setAddress("address");
        userVo.setPower(0);
        userVo.setLock(0);

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("userId");
        user.setPwd("pwd");
        user.setTpwd("pwd");
        user.setSalt("salt");
        user.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        user.setName("name");
        user.setTel("phone");
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

        when(mockUserDao.updateById(user)).thenReturn(0);

        // Run the test
        final boolean result = userServiceImplUnderTest.changeUserInfo(userVo, "id");

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testChangeUserInfo_InputNull() {
        // Setup
        final UserVo userVo = new UserVo();
        userVo.setId("id");
        userVo.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        userVo.setUname("name");
        userVo.setPower(0);
        userVo.setLock(0);
        userVo.setPwd("");

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("userId");
        user.setPwd("pwd");
        user.setTpwd("pwd");
        user.setSalt("salt");
        user.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        user.setName("name");
        user.setTel("phone");
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

        when(mockUserDao.updateById(user)).thenReturn(0);

        // Run the test
        boolean result = userServiceImplUnderTest.changeUserInfo(userVo, "id");
        // Verify the results
        assertThat(result).isFalse();

        userVo.setPwd("pwd2");
        userVo.setPhone("");
        userVo.setEmail("");
        userVo.setAddress("");
        userVo.setBirthday("");

        // Run the test
        result = userServiceImplUnderTest.changeUserInfo(userVo, "id");
        // Verify the results
        assertThat(result).isFalse();

        userVo.setPwd("pwd");
        userVo.setPhone("phone");
        userVo.setEmail("email");
        userVo.setAddress("address");
        userVo.setBirthday("age");

        // Run the test
        result = userServiceImplUnderTest.changeUserInfo(userVo, "id");
        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testChangeUserInfo_UnameEmpty_PwdSpecial() {
        // Setup
        final UserVo userVo = new UserVo();
        userVo.setId("id");
        userVo.setUname("");
        userVo.setPwd("pwd2");
        userVo.setPower(0);
        userVo.setLock(0);

        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("userId");
        user.setPwd("pwd");
        user.setTpwd("pwd");
        user.setSalt("salt");
        user.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        user.setName("name");
        user.setTel("phone");
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
        when(mockUserDao.updateById(any(User.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = userServiceImplUnderTest.changeUserInfo(userVo, "id");
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        final boolean result2 = userServiceImplUnderTest.changeUserInfo(userVo, "id");
        // Verify the results
        assertThat(result2).isTrue();
    }

    @Test
    void testSearchNew() {
        // Setup
        final PageVo pageVo = new PageVo(0, 0);

        // Configure FriendDao.selectList(...).
        final Friend friend = new Friend();
        friend.setId("id");
        friend.setUid("uid");
        friend.setFid("fid");
        friend.setState(0);
        friend.setLeaveTime(0L);
        friend.setName("name");
        friend.setEmail("email");
        friend.setAvatar("avatar");
        friend.setOnline(false);
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        status.setLikes(0);
        friend.setStatusList(Arrays.asList(status));
        final List<Friend> friendList = Arrays.asList(friend);
        when(mockFriendDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(friendList);

        when(mockUserDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(null);

        // Run the test
        final IPage<User> result = userServiceImplUnderTest.searchNew("name", pageVo);

        // Verify the results
    }

    @Test
    void testSearchNew_FriendDaoReturnsNoItems() {
        // Setup
        final PageVo pageVo = new PageVo(0, 0);
        when(mockFriendDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());
        when(mockUserDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(null);

        // Run the test
        final IPage<User> result = userServiceImplUnderTest.searchNew("name", pageVo);

        // Verify the results
    }

    @Test
    void testUpDatetime() {
        // Setup
        // Configure FriendDao.selectList(...).
        long l = System.currentTimeMillis();
        final Friend friend = new Friend();
        friend.setId("id");
        friend.setUid("uid");
        friend.setFid("fid");
        friend.setState(0);
        friend.setLeaveTime(0L);
        friend.setName("name");
        friend.setEmail("email");
        friend.setAvatar("avatar");
        friend.setOnline(false);
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        status.setLikes(0);
        friend.setStatusList(Arrays.asList(status));
        final List<Friend> friendList = Arrays.asList(friend);
        when(mockFriendDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(friendList);
        when(mockFriendDao.updateById(any(Friend.class))).thenReturn(0);

        // Configure UserGroupDao.selectList(...).
        final UserGroup userGroup = new UserGroup();
        userGroup.setId("id");
        userGroup.setUid("uid");
        userGroup.setGid("gid");
        userGroup.setState(0);
        userGroup.setJoinLevel(0);
        userGroup.setUserLevelTime(0L);
        final List<UserGroup> userGroupList = Arrays.asList(userGroup);
        when(mockUserGroupDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(userGroupList,
                userGroupList,  Collections.emptyList());
        when(mockUserGroupDao.updateById(any(UserGroup.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = userServiceImplUnderTest.upDatetime("userId");
        // Verify the results
        assertThat(result).isFalse();
        userGroup.setUserLevelTime(l);
        verify(mockUserGroupDao).updateById(userGroup);

        // Run the test
        final boolean result2 = userServiceImplUnderTest.upDatetime("userId");
        // Verify the results
        assertThat(result2).isTrue();

        // Run the test
        final boolean result3 = userServiceImplUnderTest.upDatetime("userId");
        // Verify the results
        assertThat(result3).isTrue();
    }

    @Test
    void testUpDatetime_FriendDaoSelectListReturnsNoItems() {
        // Setup
        long l = System.currentTimeMillis();
        when(mockFriendDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());
        //when(mockFriendDao.updateById(any(Friend.class))).thenReturn(0);

        // Configure UserGroupDao.selectList(...).
        final UserGroup userGroup = new UserGroup();
        userGroup.setId("id");
        userGroup.setUid("uid");
        userGroup.setGid("gid");
        userGroup.setState(0);
        userGroup.setJoinLevel(0);
        userGroup.setUserLevelTime(0L);
        final List<UserGroup> userGroupList = Arrays.asList(userGroup);
        when(mockUserGroupDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(userGroupList);

        when(mockUserGroupDao.updateById(userGroup)).thenReturn(0);

        // Run the test
        final boolean result = userServiceImplUnderTest.upDatetime("userId");

        // Verify the results
        assertThat(result).isFalse();
        userGroup.setUserLevelTime(l);
        verify(mockUserGroupDao).updateById(userGroup);
    }

    @Test
    void testUpDatetime_UserGroupDaoSelectListReturnsNoItems() {
        // Setup
        // Configure FriendDao.selectList(...).
        long l = System.currentTimeMillis();
        final Friend friend = new Friend();
        friend.setId("id");
        friend.setUid("uid");
        friend.setFid("fid");
        friend.setState(0);
        friend.setLeaveTime(0L);
        friend.setName("name");
        friend.setEmail("email");
        friend.setAvatar("avatar");
        friend.setOnline(false);
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        status.setLikes(0);
        friend.setStatusList(Arrays.asList(status));
        final List<Friend> friendList = Arrays.asList(friend);

        final UserGroup userGroup = new UserGroup();
        userGroup.setId("id");
        userGroup.setUid("uid");
        userGroup.setGid("gid");
        userGroup.setState(0);
        userGroup.setJoinLevel(0);
        userGroup.setUserLevelTime(0L);
        final List<UserGroup> userGroupList = Arrays.asList(userGroup);

        when(mockFriendDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(friendList);
        when(mockFriendDao.updateById(any(Friend.class))).thenReturn(0);
        when(mockUserGroupDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(userGroupList);
        when(mockUserGroupDao.updateById(any(UserGroup.class))).thenReturn(0);

        // Run the test
        final boolean result = userServiceImplUnderTest.upDatetime("userId");

        // Verify the results
        assertThat(result).isFalse();
        userGroup.setUserLevelTime(l);
        verify(mockUserGroupDao).updateById(userGroup);
    }
}
