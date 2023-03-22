package com.nart.service.impl;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nart.dao.FriendDao;
import com.nart.dao.StatusDao;
import com.nart.dao.UserDao;
import com.nart.dao.UserLikeDao;
import com.nart.pojo.*;
import com.nart.service.CommentService;
import com.nart.service.DataCounterService;
import com.nart.util.UserThreadLocal;
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
class StatusServiceImplTest {

    @Mock
    private CommentService mockCommentService;
    @Mock
    private DataCounterService mockDataCounterService;
    @Mock
    private UserDao mockUserDao;
    @Mock
    private FriendDao mockFriendDao;
    @Mock
    private StatusDao mockStatusDao;

    @Mock
    private UserLikeDao mockUserLikeDao;

    private StatusServiceImpl statusServiceImplUnderTest;



    @BeforeEach
    void setUp() {
        statusServiceImplUnderTest = new StatusServiceImpl(mockCommentService, mockDataCounterService, mockUserDao,
                mockFriendDao, mockStatusDao,mockUserLikeDao);
        User user = new User();
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
        UserThreadLocal.put(user);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), GroupChat.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), FriendChat.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), User.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), Friend.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), FriendReq.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), GroupInvite.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), Group.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), Message.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), Status.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), UserGroup.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), UserLike.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), Comment.class);
    }

    @Test
    public void testShowStatusList() {
        // Setup
        final IPage<Status> page = new Page<>(1, 1);
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
        IPage<Status> statusIPage = new Page<>();
        statusIPage.setRecords(expectedResult);
        when(mockStatusDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(statusIPage);

        // Configure CommentService.showCommentList(...).
        final Comment comment1 = new Comment();
        comment1.setId("id");
        comment1.setMsg("msg");
        comment1.setStatusId("statusId");
        comment1.setCreateDate(0L);
        comment1.setUserId("userId");
        comment1.setUname("uname");
        final List<Comment> comments = Arrays.asList(comment1);
        when(mockCommentService.showCommentList("id")).thenReturn(comments);

        // Run the test
        final List<Status> result = statusServiceImplUnderTest.showStatusList("sid", page);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testShowStatusList_Like1() {
        // Setup
        final IPage<Status> page = new Page<>(1, 1);
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        status.setLikes(1);
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
        IPage<Status> statusIPage = new Page<>();
        statusIPage.setRecords(expectedResult);
        when(mockStatusDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(statusIPage);

        // Configure CommentService.showCommentList(...).
        final Comment comment1 = new Comment();
        comment1.setId("id");
        comment1.setMsg("msg");
        comment1.setStatusId("statusId");
        comment1.setCreateDate(0L);
        comment1.setUserId("userId");
        comment1.setUname("uname");
        final List<Comment> comments = Arrays.asList(comment1);
        when(mockCommentService.showCommentList("id")).thenReturn(comments);

        // Run the test
        final List<Status> result = statusServiceImplUnderTest.showStatusList("sid", page);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testShowStatusList_CommentServiceReturnsNoItems() {
        // Setup
        final IPage<Status> page = new Page<>(1, 1);
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        status.setLikes(0);
        status.setCreateDate(0L);
        status.setUserLike(false);
        final List<Status> expectedResult = Arrays.asList(status);
        IPage<Status> statusIPage = new Page<>();
        statusIPage.setRecords(expectedResult);
        when(mockStatusDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(statusIPage);
        when(mockCommentService.showCommentList("id")).thenReturn(Collections.emptyList());

        // Run the test
        final List<Status> result = statusServiceImplUnderTest.showStatusList("sid", page);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testShowAllStatusList() {
        // Setup
        final IPage<Status> page = new Page<>(1, 1);
        final Status status = new Status();
        status.setId("id");
        status.setSenderId("senderId");
        status.setText("text");
        status.setPics("pics");
        status.setLikes(0);
        status.setCreateDate(0L);
        status.setUserLike(false);
        final Status status2 = new Status();
        status2.setId("id");
        status2.setSenderId("senderId");
        status2.setText("text");
        status2.setPics("pics");
        status2.setLikes(2);
        status2.setCreateDate(0L);
        status2.setUserLike(false);
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(0L);
        comment.setUserId("userId");
        comment.setUname("uname");
        status.setCommentList(Arrays.asList(comment));
        final List<Status> expectedResult = Arrays.asList(status, status2);

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
        final Status status1 = new Status();
        status1.setId("id");
        status1.setSenderId("senderId");
        status1.setText("text");
        status1.setPics("pics");
        status1.setLikes(0);
        friend.setStatusList(Arrays.asList(status1));
        final List<Friend> friendList = Arrays.asList(friend);

        IPage<Status> statusIPage = new Page<>();
        statusIPage.setRecords(expectedResult);
        when(mockFriendDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(friendList);

        when(mockStatusDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(statusIPage);

        // Configure CommentService.showCommentList(...).
        final Comment comment1 = new Comment();
        comment1.setId("id");
        comment1.setMsg("msg");
        comment1.setStatusId("statusId");
        comment1.setCreateDate(0L);
        comment1.setUserId("userId");
        comment1.setUname("uname");
        final List<Comment> comments = Arrays.asList(comment1);
        when(mockCommentService.showCommentList("id")).thenReturn(comments);

        // Run the test
        final List<Status> result = statusServiceImplUnderTest.showAllStatusList("uid", page);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testShowAllStatusList_FriendDaoReturnsNoItems() {
        // Setup
        final IPage<Status> page = new Page<>(1, 1);
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
        IPage<Status> statusIPage = new Page<>();
        statusIPage.setRecords(expectedResult);
        when(mockFriendDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());
        when(mockStatusDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(statusIPage);

        // Configure CommentService.showCommentList(...).
        final Comment comment1 = new Comment();
        comment1.setId("id");
        comment1.setMsg("msg");
        comment1.setStatusId("statusId");
        comment1.setCreateDate(0L);
        comment1.setUserId("userId");
        comment1.setUname("uname");
        final List<Comment> comments = Arrays.asList(comment1);
        when(mockCommentService.showCommentList("id")).thenReturn(comments);

        // Run the test
        final List<Status> result = statusServiceImplUnderTest.showAllStatusList("uid", page);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testShowAllStatusList_CommentServiceReturnsNoItems() {
        // Setup
        final IPage<Status> page = new Page<>(1, 1);
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
        final Status status1 = new Status();
        status1.setId("id");
        status1.setSenderId("senderId");
        status1.setText("text");
        status1.setPics("pics");
        status1.setLikes(0);
        friend.setStatusList(Arrays.asList(status1));
        final List<Friend> friendList = Arrays.asList(friend);
        IPage<Status> statusIPage = new Page<>();
        statusIPage.setRecords(expectedResult);
        when(mockFriendDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(friendList);

        when(mockStatusDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(statusIPage);
        when(mockCommentService.showCommentList("id")).thenReturn(Collections.emptyList());

        // Run the test
        final List<Status> result = statusServiceImplUnderTest.showAllStatusList("uid", page);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testPostStatus() {
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

        when(mockStatusDao.insert(any(Status.class))).thenReturn(0, 1);
        when(mockDataCounterService.updateStatusAmount(true)).thenReturn(0);

        // Run the test
        final boolean result = statusServiceImplUnderTest.postStatus(status);
        // Verify the results
        assertThat(result).isFalse();
        verify(mockDataCounterService).updateStatusAmount(true);

        // Run the test
        final boolean result2 = statusServiceImplUnderTest.postStatus(status);
        // Verify the results
        assertThat(result2).isTrue();
    }

    @Test
    public void testDelStatus() {
        // Setup
        when(mockCommentService.delComment("id")).thenReturn(false);
        when(mockStatusDao.deleteById("id")).thenReturn(0, 1);
        when(mockDataCounterService.updateStatusAmount(false)).thenReturn(0);

        // Run the test
        final boolean result = statusServiceImplUnderTest.delStatus("id");
        // Verify the results
        assertThat(result).isFalse();
        verify(mockCommentService).delComment("id");
        verify(mockDataCounterService).updateStatusAmount(false);

        // Run the test
        final boolean result2 = statusServiceImplUnderTest.delStatus("id");
        // Verify the results
        assertThat(result2).isTrue();
    }

    @Test
    public void testLikeStatus() {
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
        when(mockStatusDao.updateById(any(Status.class))).thenReturn(1);

        // Run the test
        final boolean result = statusServiceImplUnderTest.likeStatus("id", false);
        // Verify the results
        assertThat(result).isTrue();

        // Run the test
        final boolean result2 = statusServiceImplUnderTest.likeStatus("id", true);
        // Verify the results
        assertThat(result2).isTrue();

        when(mockStatusDao.updateById(any(Status.class))).thenReturn(0);

        final boolean result3 = statusServiceImplUnderTest.likeStatus("id", false);
        // Verify the results
        assertThat(result3).isFalse();

        // Run the test
        final boolean result4 = statusServiceImplUnderTest.likeStatus("id", true);
        // Verify the results
        assertThat(result4).isFalse();
    }
}
