package com.nart.service.impl;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.nart.dao.*;
import com.nart.pojo.*;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadDataInDataBaseImplTest {

    @Mock
    private UserDao mockUserDao;
    @Mock
    private FriendReqDao mockFriendReqDao;
    @Mock
    private StatusDao mockStatusDao;
    @Mock
    private GroupDao mockGroupDao;
    @Mock
    private CommentDao mockCommentDao;
    @Mock
    private GroupInviteDao mockGroupInviteDao;
    @Mock
    private FriendDao mockFriendDao;
    @Mock
    private FriendChatDao mockFriendChatDao;
    @Mock
    private UserLikeDao mockUserLikeDao;
    @Mock
    private UserGroupDao mockUserGroupDao;
    @Mock
    private GroupChatDao mockGroupChatDao;

    private LoadDataInDataBaseImpl loadDataInDataBaseImplUnderTest;

    @BeforeEach
    void setUp() {
        loadDataInDataBaseImplUnderTest = new LoadDataInDataBaseImpl(mockUserDao, mockFriendReqDao, mockStatusDao,
                mockGroupDao, mockCommentDao, mockGroupInviteDao, mockFriendDao, mockFriendChatDao, mockUserLikeDao,
                mockUserGroupDao, mockGroupChatDao);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), FriendReq.class);
    }

    @Test
    public void testLoadListUser() {
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
        final List<User> userList = Arrays.asList(user);
        when(mockUserDao.insert(any(User.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = loadDataInDataBaseImplUnderTest.LoadListUser(userList);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        boolean r = loadDataInDataBaseImplUnderTest.LoadListUser(userList);
        // Verify the results
        assertThat(r).isTrue();
    }

    @Test
    public void testLoadListFriendReq() {
        // Setup
        final FriendReq friendReq = new FriendReq();
        friendReq.setId("id");
        friendReq.setSenderId("senderId");
        friendReq.setReceiverId("receiverId");
        friendReq.setMsg("msg");
        friendReq.setDate(0L);
        final List<FriendReq> friendReqList = Arrays.asList(friendReq);
        when(mockFriendReqDao.insert(any(FriendReq.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = loadDataInDataBaseImplUnderTest.LoadListFriendReq(friendReqList);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        boolean r = loadDataInDataBaseImplUnderTest.LoadListFriendReq(friendReqList);
        // Verify the results
        assertThat(r).isTrue();
    }

    @Test
    public void testLoadListStatus() {
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
        final List<Status> statusList = Arrays.asList(status);
        when(mockStatusDao.insert(any(Status.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = loadDataInDataBaseImplUnderTest.LoadListStatus(statusList);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        boolean r = loadDataInDataBaseImplUnderTest.LoadListStatus(statusList);
        // Verify the results
        assertThat(r).isTrue();
    }

    @Test
    public void testLoadListGroup() {
        // Setup
        final List<Group> groupList = Arrays.asList(new Group("id"));
        when(mockGroupDao.insert(new Group("id"))).thenReturn(0, 1);

        // Run the test
        final boolean result = loadDataInDataBaseImplUnderTest.LoadListGroup(groupList);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        boolean r = loadDataInDataBaseImplUnderTest.LoadListGroup(groupList);
        // Verify the results
        assertThat(r).isTrue();
    }

    @Test
    public void testLoadListComment() {
        // Setup
        final Comment comment = new Comment();
        comment.setId("id");
        comment.setMsg("msg");
        comment.setStatusId("statusId");
        comment.setCreateDate(0L);
        comment.setUserId("userId");
        comment.setUname("uname");
        final List<Comment> commentList = Arrays.asList(comment);
        when(mockCommentDao.insert(any(Comment.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = loadDataInDataBaseImplUnderTest.LoadListComment(commentList);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        boolean r = loadDataInDataBaseImplUnderTest.LoadListComment(commentList);
        // Verify the results
        assertThat(r).isTrue();
    }

    @Test
    public void testLoadListGroupInvite() {
        // Setup
        final GroupInvite groupInvite = new GroupInvite();
        groupInvite.setId("id");
        groupInvite.setGroupId("groupId");
        groupInvite.setSenderId("senderId");
        groupInvite.setReceiverId("receiverId");
        groupInvite.setMsg("msg");
        groupInvite.setDate(0L);
        final List<GroupInvite> groupInviteList = Arrays.asList(groupInvite);
        when(mockGroupInviteDao.insert(any(GroupInvite.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = loadDataInDataBaseImplUnderTest.LoadListGroupInvite(groupInviteList);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        boolean r = loadDataInDataBaseImplUnderTest.LoadListGroupInvite(groupInviteList);
        // Verify the results
        assertThat(r).isTrue();
    }

    @Test
    public void testLoadListFriendShips() {
        // Setup
        final Friend friend = new Friend();
        friend.setId("id");
        friend.setUid("fid");
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
        when(mockFriendDao.insert(any(Friend.class))).thenReturn(0, 1);
        when(mockFriendReqDao.delete(any(LambdaQueryWrapper.class))).thenReturn(0);

        // Run the test
        final boolean result = loadDataInDataBaseImplUnderTest.LoadListFriendShips(friendList);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        boolean r = loadDataInDataBaseImplUnderTest.LoadListFriendShips(friendList);
        // Verify the results
        assertThat(r).isTrue();
    }

    @Test
    public void testLoadListFriendChats() {
        // Setup
        final FriendChat friendChat = new FriendChat();
        friendChat.setId(0L);
        friendChat.setReceiverId("receiverId");
        friendChat.setSenderId("senderId");
        friendChat.setLevel(0);
        friendChat.setMsg("msg");
        friendChat.setType("type");
        friendChat.setDate(0L);
        final List<FriendChat> friendChatList = Arrays.asList(friendChat);
        when(mockFriendChatDao.insert(any(FriendChat.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = loadDataInDataBaseImplUnderTest.LoadListFriendChats(friendChatList);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        boolean r = loadDataInDataBaseImplUnderTest.LoadListFriendChats(friendChatList);
        // Verify the results
        assertThat(r).isTrue();
    }

    @Test
    public void testLoadListLikes() {
        // Setup
        final UserLike userLike = new UserLike();
        userLike.setId("id");
        userLike.setUid("uid");
        userLike.setStatusId("statusId");
        final List<UserLike> likeList = Arrays.asList(userLike);
        when(mockUserLikeDao.insert(any(UserLike.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = loadDataInDataBaseImplUnderTest.LoadListLikes(likeList);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        boolean r = loadDataInDataBaseImplUnderTest.LoadListLikes(likeList);
        // Verify the results
        assertThat(r).isTrue();
    }

    @Test
    public void testLoadListGroupShips() {
        // Setup
        final UserGroup userGroup = new UserGroup();
        userGroup.setId("id");
        userGroup.setUid("uid");
        userGroup.setGid("gid");
        userGroup.setState(0);
        userGroup.setJoinLevel(0);
        userGroup.setUserLevelTime(0L);
        final List<UserGroup> userGroups = Arrays.asList(userGroup);
        when(mockUserGroupDao.insert(any(UserGroup.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = loadDataInDataBaseImplUnderTest.LoadListGroupShips(userGroups);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        boolean r = loadDataInDataBaseImplUnderTest.LoadListGroupShips(userGroups);
        // Verify the results
        assertThat(r).isTrue();
    }

    @Test
    public void testLoadListGroupChats() {
        // Setup
        final GroupChat groupChat = new GroupChat();
        groupChat.setId(0L);
        groupChat.setGroupId("groupId");
        groupChat.setSenderId("senderId");
        groupChat.setMsg("msg");
        groupChat.setType("type");
        groupChat.setDate(0L);
        groupChat.setLevel(0);
        final List<GroupChat> groupChatList = Arrays.asList(groupChat);
        when(mockGroupChatDao.insert(any(GroupChat.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = loadDataInDataBaseImplUnderTest.LoadListGroupChats(groupChatList);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        boolean r = loadDataInDataBaseImplUnderTest.LoadListGroupChats(groupChatList);
        // Verify the results
        assertThat(r).isTrue();
    }
}
