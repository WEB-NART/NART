package com.nart.service.impl;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.nart.dao.*;
import com.nart.pojo.Comment;
import com.nart.pojo.Friend;
import com.nart.pojo.Status;
import com.nart.pojo.User;
import com.nart.service.CommentService;
import com.nart.service.DataCounterService;
import com.nart.service.StatusService;
import com.nart.util.RedisUtil;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private UserDao mockUserDao;
    @Mock
    private FriendChatDao mockFriendChatDao;
    @Mock
    private GroupChatDao mockGroupChatDao;
    @Mock
    private CommentDao mockCommentDao;
    @Mock
    private GroupDao mockGroupDao;
    @Mock
    private StatusDao mockStatusDao;
    @Mock
    private CommentService mockCommentService;
    @Mock
    private DataCounterService mockDataCounterService;
    @Mock
    private RedisUtil mockRedisUtil;
    @Mock
    private FriendDao mockFriendDao;
    @Mock
    private StatusService mockStatusService;
    @Mock
    private UserGroupDao mockUserGroupDao;

    private AdminServiceImpl adminServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        adminServiceImplUnderTest = new AdminServiceImpl(mockUserDao, mockFriendChatDao, mockGroupChatDao,
                mockCommentDao, mockGroupDao, mockStatusDao, mockCommentService, mockDataCounterService, mockRedisUtil,
                mockFriendDao, mockStatusService, mockUserGroupDao);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), Status.class);
    }

    @Test
    void testShowAllUserInfo() {
        // Setup
        final User user = new User();
        user.setId("id");
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
        final List<User> expectedResult = Arrays.asList(user);

        // Configure UserDao.selectList(...).
        final User user1 = new User();
        user1.setId("id");
        user1.setPwd("pwd");
        user1.setTpwd("tpwd");
        user1.setSalt("salt");
        user1.setAvatar("avatar");
        user1.setName("name");
        user1.setTel("tel");
        user1.setAddress("address");
        user1.setEmail("email");
        user1.setAge("age");
        user1.setUserOnline(0);
        user1.setPower(0);
        user1.setState(0);
        final Friend friend1 = new Friend();
        friend1.setId("id");
        user1.setFriendList(Arrays.asList(friend1));
        final List<User> users = Arrays.asList(user1);
        when(mockUserDao.selectList(null)).thenReturn(users);

        // Run the test
        final List<User> result = adminServiceImplUnderTest.showAllUserInfo();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testShowAllUserInfo_UserDaoReturnsNoItems() {
        // Setup
        when(mockUserDao.selectList(null)).thenReturn(Collections.emptyList());

        // Run the test
        final List<User> result = adminServiceImplUnderTest.showAllUserInfo();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testShowAllUserNum() {
        // Setup
        // Configure UserDao.selectList(...).
        final User user = new User();
        user.setId("id");
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
        final List<User> users = Arrays.asList(user);
        when(mockUserDao.selectList(null)).thenReturn(users);

        // Run the test
        final int result = adminServiceImplUnderTest.showAllUserNum();

        // Verify the results
        assertThat(result).isEqualTo(1);
    }

    @Test
    void testShowAllUserNum_UserDaoReturnsNoItems() {
        // Setup
        when(mockUserDao.selectList(null)).thenReturn(Collections.emptyList());

        // Run the test
        final int result = adminServiceImplUnderTest.showAllUserNum();

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testShowOnlineUserInfo() {
        // Setup
        final User user = new User();
        user.setId("id");
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
        final List<User> expectedResult = Arrays.asList(user);

        // Configure UserDao.selectList(...).
        final User user1 = new User();
        user1.setId("id");
        user1.setPwd("pwd");
        user1.setTpwd("tpwd");
        user1.setSalt("salt");
        user1.setAvatar("avatar");
        user1.setName("name");
        user1.setTel("tel");
        user1.setAddress("address");
        user1.setEmail("email");
        user1.setAge("age");
        user1.setUserOnline(0);
        user1.setPower(0);
        user1.setState(0);
        final Friend friend1 = new Friend();
        friend1.setId("id");
        user1.setFriendList(Arrays.asList(friend1));
        final List<User> users = Arrays.asList(user1);
        when(mockUserDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(users);

        // Run the test
        final List<User> result = adminServiceImplUnderTest.showOnlineUserInfo();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testShowOnlineUserInfo_UserDaoReturnsNoItems() {
        // Setup
        when(mockUserDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<User> result = adminServiceImplUnderTest.showOnlineUserInfo();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testShowOnlineUserNum() {
        // Setup
        // Configure UserDao.selectList(...).
        final User user = new User();
        user.setId("id");
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
        final List<User> users = Arrays.asList(user);
        when(mockUserDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(users);

        // Run the test
        final int result = adminServiceImplUnderTest.showOnlineUserNum();

        // Verify the results
        assertThat(result).isEqualTo(1);
    }

    @Test
    void testShowOnlineUserNum_UserDaoReturnsNoItems() {
        // Setup
        when(mockUserDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = adminServiceImplUnderTest.showOnlineUserNum();

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testShowAllStatusInfo() {
        // Setup
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        status.setLikes(0);
        status.setCreateDate(0L);
        status.setUserLike(false);
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(0L);
        comment.setUserId("userId");
        comment.setUname("uname");
        status.setCommentList(Arrays.asList(comment));
        final List<Status> expectedResult = Arrays.asList(status);

        // Configure StatusDao.selectList(...).
        final Status status1 = new Status();
        status1.setId("id");
        status1.setSenderId("senderId");
        status1.setText("text");
        status1.setPics("pics");
        status1.setLikes(0);
        status1.setCreateDate(0L);
        status1.setUserLike(false);
        final Comment comment1 = new Comment();
        comment1.setId("id");
        comment1.setMsg("msg");
        comment1.setStatusId("statusId");
        comment1.setCreateDate(0L);
        comment1.setUserId("userId");
        comment1.setUname("uname");
        status1.setCommentList(Arrays.asList(comment1));
        final List<Status> statuses = Arrays.asList(status1);
        when(mockStatusDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(statuses);

        // Configure CommentService.showCommentList(...).
        final Comment comment2 = new Comment();
        comment2.setId("id");
        comment2.setMsg("msg");
        comment2.setStatusId("statusId");
        comment2.setCreateDate(0L);
        comment2.setUserId("userId");
        comment2.setUname("uname");
        final List<Comment> comments = Arrays.asList(comment2);
        when(mockCommentService.showCommentList("id")).thenReturn(comments);

        // Run the test
        final List<Status> result = adminServiceImplUnderTest.showAllStatusInfo();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testShowAllStatusInfo_StatusDaoReturnsNoItems() {
        // Setup
        when(mockStatusDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        // Configure CommentService.showCommentList(...).
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(0L);
        comment.setUserId("userId");
        comment.setUname("uname");
        final List<Comment> comments = Arrays.asList(comment);

        // Run the test
        final List<Status> result = adminServiceImplUnderTest.showAllStatusInfo();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testShowAllStatusInfo_CommentServiceReturnsNoItems() {
        // Setup
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        status.setLikes(0);
        status.setCreateDate(0L);
        status.setUserLike(false);
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(0L);
        comment.setUserId("userId");
        comment.setUname("uname");
        status.setCommentList(Arrays.asList(comment));
        final List<Status> expectedResult = Arrays.asList(status);

        // Configure StatusDao.selectList(...).
        final Status status1 = new Status();
        status1.setId("id");
        status1.setSenderId("senderId");
        status1.setText("text");
        status1.setPics("pics");
        status1.setLikes(0);
        status1.setCreateDate(0L);
        status1.setUserLike(false);
        final Comment comment1 = new Comment();
        comment1.setId("id");
        comment1.setMsg("msg");
        comment1.setStatusId("statusId");
        comment1.setCreateDate(0L);
        comment1.setUserId("userId");
        comment1.setUname("uname");
        status1.setCommentList(Arrays.asList(comment1));
        final List<Status> statuses = Arrays.asList(status1);
        when(mockStatusDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(statuses);

        when(mockCommentService.showCommentList("id")).thenReturn(Arrays.asList(comment1));

        // Run the test
        final List<Status> result = adminServiceImplUnderTest.showAllStatusInfo();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testShowAllStatusNum() {
        // Setup
        // Configure StatusDao.selectList(...).
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        status.setLikes(0);
        status.setCreateDate(0L);
        status.setUserLike(false);
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(0L);
        comment.setUserId("userId");
        comment.setUname("uname");
        status.setCommentList(Arrays.asList(comment));
        final List<Status> statuses = Arrays.asList(status);
        when(mockStatusDao.selectList(null)).thenReturn(statuses);

        // Run the test
        final int result = adminServiceImplUnderTest.showAllStatusNum();

        // Verify the results
        assertThat(result).isEqualTo(1);
    }

    @Test
    void testShowAllStatusNum_StatusDaoReturnsNoItems() {
        // Setup
        when(mockStatusDao.selectList(null)).thenReturn(Collections.emptyList());

        // Run the test
        final int result = adminServiceImplUnderTest.showAllStatusNum();

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testShowAllCommentInfo() {
        // Setup
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(0L);
        comment.setUserId("userId");
        comment.setUname("uname");
        final List<Comment> expectedResult = Arrays.asList(comment);

        // Configure CommentDao.selectList(...).
        final Comment comment1 = new Comment();
        comment1.setId("id");
        comment1.setMsg("msg");
        comment1.setStatusId("statusId");
        comment1.setCreateDate(0L);
        comment1.setUserId("userId");
        comment1.setUname("uname");
        final List<Comment> comments = Arrays.asList(comment1);
        when(mockCommentDao.selectList(null)).thenReturn(comments);

        // Run the test
        final List<Comment> result = adminServiceImplUnderTest.showAllCommentInfo();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testShowAllCommentInfo_CommentDaoReturnsNoItems() {
        // Setup
        when(mockCommentDao.selectList(null)).thenReturn(Collections.emptyList());

        // Run the test
        final List<Comment> result = adminServiceImplUnderTest.showAllCommentInfo();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testShowAllCommentNum() {
        // Setup
        // Configure CommentDao.selectList(...).
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(0L);
        comment.setUserId("userId");
        comment.setUname("uname");
        final List<Comment> comments = Arrays.asList(comment);
        when(mockCommentDao.selectList(null)).thenReturn(comments);

        // Run the test
        final int result = adminServiceImplUnderTest.showAllCommentNum();

        // Verify the results
        assertThat(result).isEqualTo(1);
    }

    @Test
    void testShowAllCommentNum_CommentDaoReturnsNoItems() {
        // Setup
        when(mockCommentDao.selectList(null)).thenReturn(Collections.emptyList());

        // Run the test
        final int result = adminServiceImplUnderTest.showAllCommentNum();

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testSearchUser() {
        // Setup
        final User expectedResult = new User();
        expectedResult.setId("id");
        expectedResult.setPwd("pwd");
        expectedResult.setTpwd("tpwd");
        expectedResult.setSalt("salt");
        expectedResult.setAvatar("avatar");
        expectedResult.setName("name");
        expectedResult.setTel("tel");
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
        user.setId("id");
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
        when(mockUserDao.selectById("id")).thenReturn(user);

        // Run the test
        final User result = adminServiceImplUnderTest.searchUser("id");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testBlockUser() {
        // Setup
        // Configure UserDao.selectById(...).
        final User user = new User();
        user.setId("id");
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
        when(mockUserDao.selectById("id")).thenReturn(user);

        user.setState(1);

        when(mockUserDao.updateById(user)).thenReturn(0);
        // Run the test
        final boolean result = adminServiceImplUnderTest.blockUser("id");
        // Verify the results
        assertThat(result).isFalse();

        when(mockUserDao.updateById(user)).thenReturn(1);
        // Run the test
        final boolean result2 = adminServiceImplUnderTest.blockUser("id");
        // Verify the results
        assertThat(result2).isTrue();
    }

    @Test
    void testDeleteStatus() {
        // Setup
        // Configure StatusDao.selectById(...).
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        status.setLikes(0);
        status.setCreateDate(0L);
        status.setUserLike(false);
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(0L);
        comment.setUserId("userId");
        comment.setUname("uname");
        status.setCommentList(Arrays.asList(comment));
        when(mockStatusDao.selectById("id")).thenReturn(status);

        // Configure CommentDao.selectList(...).
        final Comment comment1 = new Comment();
        comment1.setId("id");
        comment1.setMsg("msg");
        comment1.setStatusId("statusId");
        comment1.setCreateDate(0L);
        comment1.setUserId("userId");
        comment1.setUname("uname");
        final List<Comment> comments = Arrays.asList(comment1);
        when(mockCommentDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(comments);

        when(mockCommentDao.deleteById("id")).thenReturn(0);
        when(mockStatusDao.deleteById("id")).thenReturn(0);
        // Run the test
        final boolean result = adminServiceImplUnderTest.deleteStatus("id");
        // Verify the results
        assertThat(result).isFalse();
        verify(mockCommentDao).deleteById("id");

        when(mockStatusDao.deleteById("id")).thenReturn(1);
        // Run the test
        final boolean result2 = adminServiceImplUnderTest.deleteStatus("id");
        // Verify the results
        assertThat(result2).isTrue();
    }

    @Test
    void testDeleteStatus_CommentDaoSelectListReturnsNoItems() {
        // Setup
        // Configure StatusDao.selectById(...).
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        status.setLikes(0);
        status.setCreateDate(0L);
        status.setUserLike(false);
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(0L);
        comment.setUserId("userId");
        comment.setUname("uname");
        status.setCommentList(Arrays.asList(comment));
        when(mockStatusDao.selectById("id")).thenReturn(status);

        when(mockCommentDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        when(mockStatusDao.deleteById("id")).thenReturn(0);
        // Run the test
        final boolean result = adminServiceImplUnderTest.deleteStatus("id");
        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testDeleteComment() {
        // Setup
        when(mockCommentDao.deleteById("id")).thenReturn(0);
        // Run the test
        final boolean result = adminServiceImplUnderTest.deleteComment("id");
        // Verify the results
        assertThat(result).isFalse();
        // Setup
        when(mockCommentDao.deleteById("id")).thenReturn(1);
        // Run the test
        final boolean result2 = adminServiceImplUnderTest.deleteComment("id");
        // Verify the results
        assertThat(result2).isTrue();
    }
}
