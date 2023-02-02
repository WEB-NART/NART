package com.nart.service.impl;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.nart.dao.CommentDao;
import com.nart.dao.UserDao;
import com.nart.pojo.Comment;
import com.nart.pojo.Friend;
import com.nart.pojo.User;
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
class CommentServiceImplTest {

    @Mock
    private CommentDao mockCommentDao;
    @Mock
    private UserDao mockUserDao;
    @Mock
    private DataCounterService mockDataCounterService;

    private CommentServiceImpl commentServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        commentServiceImplUnderTest = new CommentServiceImpl(mockCommentDao, mockUserDao, mockDataCounterService);
        User user = new User();
        user.setId("id");
        UserThreadLocal.put(user);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), Comment.class);
    }

    @Test
    public void testShowCommentList() {
        // Setup
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(0L);
        comment.setUserId("sid");
        comment.setUname("name");
        final List<Comment> expectedResult = Arrays.asList(comment);

        // Configure CommentDao.selectList(...).
        final Comment comment1 = new Comment();
        comment1.setId("id");
        comment1.setMsg("msg");
        comment1.setStatusId("statusId");
        comment1.setCreateDate(0L);
        comment1.setUserId("sid");
        comment1.setUname("name");
        final List<Comment> comments = Arrays.asList(comment1);
        when(mockCommentDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(comments);

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
        when(mockUserDao.selectById("sid")).thenReturn(user);

        // Run the test
        final List<Comment> result = commentServiceImplUnderTest.showCommentList("statusId");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testShowCommentList_CommentDaoReturnsNoItems() {
        // Setup
        when(mockCommentDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

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
        //when(mockUserDao.selectById("sid")).thenReturn(user);

        // Run the test
        final List<Comment> result = commentServiceImplUnderTest.showCommentList("statusId");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testPostComment() {
        // Setup
        when(mockDataCounterService.updateCommentAmount(true)).thenReturn(0);
        when(mockCommentDao.insert(any(Comment.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = commentServiceImplUnderTest.postComment("statusId", "msg", "sid");

        // Verify the results
        assertThat(result).isFalse();
        verify(mockDataCounterService).updateCommentAmount(true);

        // Run the test
        boolean r = commentServiceImplUnderTest.postComment("statusId", "msg", "sid");
        // Verify the results
        assertThat(r).isTrue();
    }

    @Test
    public void testDelComment() {
        // Setup
        // Configure CommentDao.selectList(...).
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(0L);
        comment.setUserId("sid");
        comment.setUname("name");
        final List<Comment> comments = Arrays.asList(comment);
        when(mockCommentDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(comments);
        when(mockCommentDao.deleteById("id")).thenReturn(0, 1);

        // Run the test
        final boolean result = commentServiceImplUnderTest.delComment("statusId");
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        boolean r = commentServiceImplUnderTest.delComment("statusId");
        // Verify the results
        assertThat(r).isTrue();
    }

    @Test
    public void testDelComment_CommentDaoSelectListReturnsNoItems() {
        // Setup
        when(mockCommentDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());
        //when(mockCommentDao.deleteById("id")).thenReturn(0);

        // Run the test
        final boolean result = commentServiceImplUnderTest.delComment("statusId");
        // Verify the results
        assertThat(result).isTrue();
    }
}
