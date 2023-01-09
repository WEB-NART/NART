package com.nart.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nart.config.WebSocketConfig;
import com.nart.service.GroupService;
import com.nart.util.EncryptUtil;
import com.nart.util.GsonFormatter;
import com.nart.util.SpringUtil;
import com.nart.vo.WSMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: ChatEndPoint
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/12/29 11:05
 */
@ServerEndpoint(value="/chat",configurator = WebSocketConfig.class)
@Component
@Slf4j
public class ChatEndPoint {
    // stores all online users
    private static final Map<String, ChatEndPoint> onlineUsers = new ConcurrentHashMap<>();
    private Session session;
    private HttpSession httpSession;

    private GroupService getGroupService() {
        GroupService gsi = (GroupService) SpringUtil.getBean("groupServiceImpl");
        return gsi;
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        String uid = (String) httpSession.getAttribute("uid");
        uid = uid.substring(5);
        onlineUsers.put(uid, this);
        onlineUsers.forEach((key, value) -> {
            String temp = key + ": " + value.toString();
            log.info(temp);
        });
    }

    private void broadcastAllUsers(WSMsg wsMsg, Set<String> receivers) {
        // find all online userIds
        Map<String, ChatEndPoint> onlineUsers1 = onlineUsers;
        Set<String> ids = new HashSet<String>(onlineUsers1.keySet());

        // find intersections between online users and target users;
        ids.retainAll(receivers);
        ids.remove(wsMsg.getSender());
        try {
            // send to all
            for(String id: ids){
                ChatEndPoint chatEndPoint = onlineUsers.get(id);
                chatEndPoint.session.getBasicRemote().sendText(GsonFormatter.toJsonString(wsMsg));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<String> getIds() {
        return onlineUsers.keySet();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            WSMsg msg = mapper.readValue(message, WSMsg.class);
            String sid = msg.getSender();
            Map<String, Object> stringObjectMap = EncryptUtil.checkToken(sid);
            if (stringObjectMap != null) {
                sid = ((Long) stringObjectMap.get("userId")).toString();
            }
            msg.setSender(sid);

            Set<String> receivers = new HashSet<>();
            String receiverType = msg.getReceiverType();
            String receiver = msg.getReceiver();
            // set receivers
            if(receiverType.equals("friend")) {
                receivers.add(receiver);
            } else {
                GroupService gs = getGroupService();
                receivers.addAll(gs.findAllMembers(receiver));
            }

            broadcastAllUsers(msg, receivers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session){
        String uid = (String) httpSession.getAttribute("uid");
        onlineUsers.remove(uid);
    }
}
