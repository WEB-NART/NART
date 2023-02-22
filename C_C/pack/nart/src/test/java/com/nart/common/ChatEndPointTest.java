package com.nart.common;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nart.dao.GroupDao;
import com.nart.dao.GroupInviteDao;
import com.nart.dao.UserDao;
import com.nart.dao.UserGroupDao;
import com.nart.pojo.UserGroup;
import com.nart.service.ChatService;
import com.nart.service.impl.GroupServiceImpl;
import com.nart.util.EncryptUtil;
import com.nart.util.SpringUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ChatEndPointTest {

    private ChatEndPoint chatEndPointUnderTest;

    @Mock
    private Session session;

    private final MockHttpSession httpSession = new MockHttpSession();

    private final MockHttpSession httpSession2 = new MockHttpSession();

    @Mock
    private EndpointConfig config;

    @Mock
    private ApplicationContext context;

    private static final UserGroupDao mock = mock(UserGroupDao.class);

    @Mock
    private EndpointConfig config2;

    private final String senderId = "1234123412341234";
    private final String receiverId = "5678";

    private RemoteEndpoint.Basic mockRemoteEndpoint;

    @BeforeAll
    static void mockSet() {
        GroupServiceImpl groupService = new GroupServiceImpl(
                mock,
                mock(UserDao.class),
                mock(GroupDao.class),
                mock(GroupInviteDao.class),
                mock(ChatService.class));
        Mockito.mockStatic(SpringUtil.class);
        when(SpringUtil.getBean("groupServiceImpl")).thenReturn(groupService);
    }

    @BeforeEach
    void setUp() throws IOException {
        chatEndPointUnderTest = new ChatEndPoint();
        httpSession.setAttribute("uid", "uid: "+senderId);
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put(HttpSession.class.getName(), httpSession);
        when(config.getUserProperties()).thenReturn(stringObjectHashMap);

        httpSession2.setAttribute("uid", "uid: "+receiverId);
        HashMap<String, Object> stringObjectHashMap2 = new HashMap<>();
        stringObjectHashMap2.put(HttpSession.class.getName(), httpSession2);
        when(config2.getUserProperties()).thenReturn(stringObjectHashMap2);

        AtomicReference<String> text = new AtomicReference<>("");
        mockRemoteEndpoint = mock(RemoteEndpoint.Basic.class);
        when(session.getBasicRemote()).thenReturn(mockRemoteEndpoint);

        doAnswer(invocation -> {
            String t = invocation.getArgument(0);
            text.set(t);
            return null;
        }).when(mockRemoteEndpoint).sendText(any(String.class));

    }

    @Test
    void testOnOpen() {
        chatEndPointUnderTest.onOpen(session, config);
        chatEndPointUnderTest.onOpen(session, config2);

        // Verify the results
        chatEndPointUnderTest.onClose();
    }

    @Test
    void testOnClose() {
        chatEndPointUnderTest.onOpen(session, config);
        chatEndPointUnderTest.onClose();

        // Verify the results
    }

    @Test
    void testOnMessage_EncryptFriendText() throws IOException {
        String token = EncryptUtil.createToken(Long.parseLong(senderId));
        String friendEncryptText = "{" +
                "\"msg\": \"test\"," +
                "\"msgType\": \"text\"," +
                "\"sender\": \""+ token +"\"," +
                "\"senderName\": \"senderName\"," +
                "\"senderAvatar\": \"senderAvatar\"," +
                "\"receiver\": \""+receiverId+"\"," +
                "\"receiverType\": \"friend\"" +
                "}";

        chatEndPointUnderTest.onOpen(session, config);
        chatEndPointUnderTest.onOpen(session, config2);
        chatEndPointUnderTest.onMessage(friendEncryptText);

        verify(mockRemoteEndpoint).sendText("{" +
                "\"msg\":\"test\"," +
                "\"sender\":\""+ senderId +"\"," +
                "\"senderName\":\"senderName\"," +
                "\"senderAvatar\":\"senderAvatar\"," +
                "\"msgType\":\"text\"," +
                "\"receiver\":\""+receiverId+"\"," +
                "\"receiverType\":\"friend\"" +
                "}");
        chatEndPointUnderTest.onClose();
    }

    @Test
    void testOnMessage_UnEncryptFriendText() throws IOException {
        String friendUnEncryptText = "{" +
                "\"msg\": \"test\"," +
                "\"msgType\": \"text\"," +
                "\"sender\": \""+ senderId +"\"," +
                "\"senderName\": \"senderName\"," +
                "\"senderAvatar\": \"senderAvatar\"," +
                "\"receiver\": \""+receiverId+"\"," +
                "\"receiverType\": \"friend\"" +
                "}";

        chatEndPointUnderTest.onOpen(session, config);
        chatEndPointUnderTest.onOpen(session, config2);
        chatEndPointUnderTest.onMessage(friendUnEncryptText);

        verify(mockRemoteEndpoint).sendText("{" +
                "\"msg\":\"test\"," +
                "\"sender\":\""+ senderId +"\"," +
                "\"senderName\":\"senderName\"," +
                "\"senderAvatar\":\"senderAvatar\"," +
                "\"msgType\":\"text\"," +
                "\"receiver\":\""+receiverId+"\"," +
                "\"receiverType\":\"friend\"" +
                "}");
        chatEndPointUnderTest.onClose();
    }

    @Test
    void testOnMessage_EncryptGroupText() throws IOException {
        String token = EncryptUtil.createToken(Long.parseLong(senderId));
        String friendEncryptText = "{" +
                "\"msg\": \"test\"," +
                "\"msgType\": \"text\"," +
                "\"sender\": \""+ token +"\"," +
                "\"senderName\": \"senderName\"," +
                "\"senderAvatar\": \"senderAvatar\"," +
                "\"receiver\": \""+receiverId+"\"," +
                "\"receiverType\": \"group\"" +
                "}";

        List<UserGroup> objects = new ArrayList<>();
        lenient().when(mock.selectList(any(LambdaQueryWrapper.class))).thenReturn(objects);

        chatEndPointUnderTest.onOpen(session, config);
        chatEndPointUnderTest.onOpen(session, config2);
        chatEndPointUnderTest.onMessage(friendEncryptText);

        verifyNoInteractions(mockRemoteEndpoint);
        chatEndPointUnderTest.onClose();
    }

    @Test
    void testOnMessage_UnEncryptGroupText() throws IOException {
        String friendUnEncryptText = "{" +
                "\"msg\": \"test\"," +
                "\"msgType\": \"text\"," +
                "\"sender\": \""+ senderId +"\"," +
                "\"senderName\": \"senderName\"," +
                "\"senderAvatar\": \"senderAvatar\"," +
                "\"receiver\": \""+receiverId+"\"," +
                "\"receiverType\": \"group\"" +
                "}";

        List<UserGroup> objects = new ArrayList<>();
        UserGroup userGroup = new UserGroup();
        userGroup.setUid(receiverId);
        objects.add(userGroup);
        lenient().when(mock.selectList(any(LambdaQueryWrapper.class))).thenReturn(objects);
        doThrow(new IOException()).when(mockRemoteEndpoint).sendText(any(String.class));

        chatEndPointUnderTest.onOpen(session, config);
        chatEndPointUnderTest.onOpen(session, config2);
        chatEndPointUnderTest.onMessage(friendUnEncryptText);

        chatEndPointUnderTest.onClose();
    }
}
