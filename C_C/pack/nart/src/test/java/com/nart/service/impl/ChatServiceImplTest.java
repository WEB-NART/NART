package com.nart.service.impl;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nart.dao.*;
import com.nart.pojo.*;
import com.nart.service.DataCounterService;
import com.nart.util.RedisUtil;
import com.nart.util.UserThreadLocal;
import com.nart.vo.DateVo;
import com.nart.vo.MessageVo;
import com.nart.vo.PageVo;
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
class ChatServiceImplTest {

    @Mock
    private FriendChatDao mockFriendChatDao;
    @Mock
    private GroupChatDao mockGroupChatDao;
    @Mock
    private UserDao mockUserDao;
    @Mock
    private GroupDao mockGroupDao;
    @Mock
    private DataCounterService mockDataCounterService;
    @Mock
    private RedisUtil mockRedisUtil;
    @Mock
    private FriendDao mockFriendDao;
    @Mock
    private UserGroupDao mockUserGroupDao;

    private ChatServiceImpl chatServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        chatServiceImplUnderTest = new ChatServiceImpl(mockFriendChatDao, mockGroupChatDao, mockUserDao, mockGroupDao,
                mockDataCounterService, mockRedisUtil, mockFriendDao, mockUserGroupDao);
        User user = new User();
        user.setId("id");
        UserThreadLocal.put(user);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), GroupChat.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), FriendChat.class);
    }

    @Test
    void testSendFriendMsg() {
        // Setup
        final FriendChat friendChat = new FriendChat();
        friendChat.setId(0L);
        friendChat.setReceiverId("receiverId");
        friendChat.setSenderId("senderId");
        friendChat.setLevel(0);
        friendChat.setMsg("msg");
        friendChat.setType("type");
        friendChat.setDate(0L);

        when(mockFriendChatDao.insert(any(FriendChat.class))).thenReturn(1,0);
        when(mockDataCounterService.updateMessageAmount(true)).thenReturn(1);

        // Run the test
        final boolean result = chatServiceImplUnderTest.sendFriendMsg(friendChat);
        // Verify the results
        assertThat(result).isTrue();
        verify(mockDataCounterService).updateMessageAmount(true);

        // Run the test
        final boolean result2 = chatServiceImplUnderTest.sendFriendMsg(friendChat);
        // Verify the results
        assertThat(result2).isFalse();
    }

    @Test
    void testSendGroupMsg() {
        // Setup
        final GroupChat groupChat = new GroupChat();
        groupChat.setId(0L);
        groupChat.setGroupId("groupId");
        groupChat.setSenderId("senderId");
        groupChat.setMsg("msg");
        groupChat.setType("type");
        groupChat.setDate(0L);
        groupChat.setLevel(0);

        // Configure GroupDao.selectById(...).
        final Group group = new Group();
        group.setId("id");
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
        friend.setOnline(false);
        group.setUserList(Arrays.asList(friend));
        when(mockGroupDao.selectById("groupId")).thenReturn(group);

        when(mockGroupChatDao.insert(any(GroupChat.class))).thenReturn(1,0);
        when(mockDataCounterService.updateMessageAmount(true)).thenReturn(1);

        // Run the test
        final boolean result = chatServiceImplUnderTest.sendGroupMsg(groupChat);
        // Verify the results
        assertThat(result).isTrue();
        verify(mockDataCounterService).updateMessageAmount(true);
        // Run the test
        final boolean result2 = chatServiceImplUnderTest.sendGroupMsg(groupChat);
        // Verify the results
        assertThat(result2).isFalse();
    }

    @Test
    void testReceiveFriendMsg() {
        // Setup
        final IPage<FriendChat> page = new Page<>(1, 1);
        final FriendChat friendChat = new FriendChat();
        friendChat.setId(0L);
        friendChat.setReceiverId("receiverId");
        friendChat.setSenderId("senderId");
        friendChat.setLevel(0);
        friendChat.setMsg("msg");
        friendChat.setType("type");
        friendChat.setDate(0L);
        final List<FriendChat> expectedResult = Arrays.asList(friendChat);
        IPage<FriendChat> friendChatIPage = new Page<>();
        friendChatIPage.setRecords(expectedResult);
        when(mockFriendChatDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(friendChatIPage);

        // Run the test
        final List<FriendChat> result = chatServiceImplUnderTest.receiveFriendMsg("receiveId", page);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testReceiveGroupMsg() {
        // Setup
        final IPage<GroupChat> page = new Page<>(1, 1);;
        final GroupChat groupChat = new GroupChat();
        groupChat.setId(0L);
        groupChat.setGroupId("groupId");
        groupChat.setSenderId("senderId");
        groupChat.setMsg("msg");
        groupChat.setType("type");
        groupChat.setDate(0L);
        groupChat.setLevel(0);
        final List<GroupChat> expectedResult = Arrays.asList(groupChat);
        Page<GroupChat> groupChatIPage = new Page<>();
        groupChatIPage.setRecords(expectedResult);
        when(mockGroupChatDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(groupChatIPage);

        // Run the test
        final List<GroupChat> result = chatServiceImplUnderTest.receiveGroupMsg("receiveId", page);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testShowFriendHistory() {
        // Setup
        PageVo p = new PageVo(1, 1);
        final IPage<FriendChat> page = p.toIPage(FriendChat.class);
        final MessageVo messageVo = new MessageVo();
        messageVo.setMsgId("1234");
        messageVo.setMsgType("msgType");
        //messageVo.setType("type");
        messageVo.setChatId("chatId");
        messageVo.setSenderId("senderId");
        messageVo.setSenderName("name");
        messageVo.setSenderAvatar("avatar");
        final DateVo sentDate = new DateVo();
        sentDate.setYear(2020);
        sentDate.setMonth(1);
        sentDate.setDay(1);
        sentDate.setHour(0);
        sentDate.setMin(0);
        messageVo.setSentDate(sentDate);
        messageVo.setMsg("msg");
        final List<MessageVo> expectedResult = Arrays.asList(messageVo);
        Page<FriendChat> objectPage = new Page<>();
        objectPage.setRecords(Arrays.asList(messageVo.MtoF()));
        when(mockFriendChatDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(objectPage);

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
        when(mockUserDao.selectById("senderId")).thenReturn(user);

        // Run the test
        final List<MessageVo> result = chatServiceImplUnderTest.showFriendHistory("Id", page);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testShowFriendHistory_UserDaoReturnsNull() {
        // Setup
        PageVo p = new PageVo(1, 1);
        final IPage<FriendChat> page = p.toIPage(FriendChat.class);
        final MessageVo messageVo = new MessageVo();
        messageVo.setMsgId("1234");
        messageVo.setMsgType("msgType");
        //messageVo.setType("type");
        messageVo.setChatId("chatId");
        messageVo.setSenderId("senderId");
        //messageVo.setSenderName("name");
        //messageVo.setSenderAvatar("avatar");
        final DateVo sentDate = new DateVo();
        sentDate.setYear(2020);
        sentDate.setMonth(1);
        sentDate.setDay(1);
        sentDate.setHour(0);
        sentDate.setMin(0);
        messageVo.setSentDate(sentDate);
        messageVo.setMsg("msg");
        final List<MessageVo> expectedResult = Arrays.asList(messageVo);
        Page<FriendChat> objectPage = new Page<>();
        objectPage.setRecords(Arrays.asList(messageVo.MtoF()));
        when(mockFriendChatDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(objectPage);
        when(mockUserDao.selectById("senderId")).thenReturn(null);

        // Run the test
        final List<MessageVo> result = chatServiceImplUnderTest.showFriendHistory("Id", page);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testShowGroupHistory() {
        // Setup\
        PageVo p = new PageVo(1, 1);
        final IPage<GroupChat> page = p.toIPage(GroupChat.class);
        final MessageVo messageVo = new MessageVo();
        messageVo.setMsgId("1234");
        messageVo.setMsgType("msgType");
        //messageVo.setType("type");
        messageVo.setChatId("chatId");
        messageVo.setSenderId("senderId");
        messageVo.setSenderName("name");
        messageVo.setSenderAvatar("avatar");
        final DateVo sentDate = new DateVo();
        sentDate.setYear(2020);
        sentDate.setMonth(1);
        sentDate.setDay(1);
        sentDate.setHour(0);
        sentDate.setMin(0);
        messageVo.setSentDate(sentDate);
        messageVo.setMsg("msg");
        final List<MessageVo> expectedResult = Arrays.asList(messageVo);
        Page<GroupChat> objectPage = new Page<>();
        objectPage.setRecords(Arrays.asList(messageVo.MtoG()));
        when(mockGroupChatDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(objectPage);

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
        when(mockUserDao.selectById("senderId")).thenReturn(user);

        // Run the test
        final List<MessageVo> result = chatServiceImplUnderTest.showGroupHistory("gId", page);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testShowGroupHistory_UserDaoReturnsNull() {
        // Setup
        PageVo p = new PageVo(1, 1);
        final IPage<GroupChat> page = p.toIPage(GroupChat.class);
        final MessageVo messageVo = new MessageVo();
        messageVo.setMsgId("1234");
        messageVo.setMsgType("msgType");
        //messageVo.setType("type");
        messageVo.setChatId("chatId");
        messageVo.setSenderId("senderId");
        //messageVo.setSenderName("name");
        //messageVo.setSenderAvatar("avatar");
        final DateVo sentDate = new DateVo();
        sentDate.setYear(2020);
        sentDate.setMonth(1);
        sentDate.setDay(1);
        sentDate.setHour(0);
        sentDate.setMin(0);
        messageVo.setSentDate(sentDate);
        messageVo.setMsg("msg");
        final List<MessageVo> expectedResult = Arrays.asList(messageVo);
        IPage<GroupChat> objectPage = new Page<>();
        objectPage.setRecords(Arrays.asList(messageVo.MtoG()));
        when(mockGroupChatDao.selectPage(any(IPage.class), any(LambdaQueryWrapper.class))).thenReturn(objectPage);
        when(mockUserDao.selectById("senderId")).thenReturn(null);

        // Run the test
        final List<MessageVo> result = chatServiceImplUnderTest.showGroupHistory("gId", page);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testLeaveRoom() {
        // Setup
        // Configure FriendDao.selectOne(...).
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
        when(mockFriendDao.selectOne(any(LambdaQueryWrapper.class))).thenReturn(friend);

        when(mockFriendDao.updateById(any(Friend.class))).thenReturn(0, 1);

        // Configure UserGroupDao.selectOne(...).
        final UserGroup userGroup = new UserGroup();
        userGroup.setId("id");
        userGroup.setUid("uid");
        userGroup.setGid("gid");
        userGroup.setState(0);
        userGroup.setJoinLevel(0);
        userGroup.setUserLevelTime(0L);
        when(mockUserGroupDao.selectOne(any(LambdaQueryWrapper.class))).thenReturn(userGroup);
        when(mockUserGroupDao.updateById(any(UserGroup.class))).thenReturn(0, 1);

        // Run the test
        final boolean result = chatServiceImplUnderTest.leaveRoom("roomId", false);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        final boolean result2 = chatServiceImplUnderTest.leaveRoom("roomId", false);
        // Verify the results
        assertThat(result2).isTrue();

        // Run the test
        final boolean result3 = chatServiceImplUnderTest.leaveRoom("roomId", true);
        // Verify the results
        assertThat(result3).isFalse();

        // Run the test
        final boolean result4 = chatServiceImplUnderTest.leaveRoom("roomId", true);
        // Verify the results
        assertThat(result4).isTrue();
    }

    @Test
    void testExistNewMsg() {
        // Setup
        // Configure FriendChatDao.selectList(...).
        final FriendChat friendChat = new FriendChat();
        friendChat.setId(0L);
        friendChat.setReceiverId("receiverId");
        friendChat.setSenderId("senderId");
        friendChat.setLevel(0);
        friendChat.setMsg("msg");
        friendChat.setType("type");
        friendChat.setDate(0L);
        final List<FriendChat> friendChatList = Arrays.asList(friendChat);
        when(mockFriendChatDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(friendChatList);

        // Configure GroupChatDao.selectList(...).
        final GroupChat groupChat = new GroupChat();
        groupChat.setId(0L);
        groupChat.setGroupId("groupId");
        groupChat.setSenderId("senderId");
        groupChat.setMsg("msg");
        groupChat.setType("type");
        groupChat.setDate(0L);
        groupChat.setLevel(0);
        final List<GroupChat> groupChatList = Arrays.asList(groupChat);
        when(mockGroupChatDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(groupChatList);

        // Run the test
        final boolean result = chatServiceImplUnderTest.existNewMsg("id", false, 0L);
        // Verify the results
        assertThat(result).isTrue();

        // Run the test
        final boolean result2 = chatServiceImplUnderTest.existNewMsg("id", true, 0L);
        // Verify the results
        assertThat(result2).isTrue();

        when(mockFriendChatDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(null);
        // Run the test
        final boolean result3 = chatServiceImplUnderTest.existNewMsg("id", true, 0L);
        // Verify the results
        assertThat(result3).isFalse();

        when(mockFriendChatDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());
        // Run the test
        final boolean result4 = chatServiceImplUnderTest.existNewMsg("id", true, 0L);
        // Verify the results
        assertThat(result4).isFalse();
    }

    @Test
    void testExistNewMsg_FriendChatDaoReturnsNull() {
        // Setup
        //when(mockFriendChatDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(null);

        // Run the test
        final boolean result = chatServiceImplUnderTest.existNewMsg("id", false, 0L);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testExistNewMsg_FriendChatDaoReturnsNoItems() {
        // Setup
        //when(mockFriendChatDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        // Run the test
        final boolean result = chatServiceImplUnderTest.existNewMsg("id", false, 0L);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testExistNewMsg_GroupChatDaoReturnsNoItems() {
        // Setup
        // Configure FriendChatDao.selectList(...).
        final FriendChat friendChat = new FriendChat();
        friendChat.setId(0L);
        friendChat.setReceiverId("receiverId");
        friendChat.setSenderId("senderId");
        friendChat.setLevel(0);
        friendChat.setMsg("msg");
        friendChat.setType("type");
        friendChat.setDate(0L);
        final List<FriendChat> friendChatList = Arrays.asList(friendChat);
        //when(mockFriendChatDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(friendChatList);

        when(mockGroupChatDao.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        // Run the test
        final boolean result = chatServiceImplUnderTest.existNewMsg("id", false, 0L);

        // Verify the results
        assertThat(result).isFalse();
    }
}
