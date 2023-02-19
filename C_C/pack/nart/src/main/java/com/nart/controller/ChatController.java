package com.nart.controller;

import com.nart.common.LogA;
import com.nart.pojo.FriendChat;
import com.nart.pojo.GroupChat;
import com.nart.service.ChatService;
import com.nart.util.ErrorCode;
import com.nart.util.Result;
import com.nart.util.UserThreadLocal;
import com.nart.vo.MessageVo;
import com.nart.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: ChatController
 *  chat related request controller
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/01/12 15:16
 */
@RestController
@RequestMapping("chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @LogA
    @PostMapping("history/{type}/{chatId}")
    public Result showHistory(@PathVariable String type,
                              @PathVariable String chatId,
                              @RequestBody PageVo page) {
        if(type.equals("friend")) {
            List<MessageVo> messageVos = chatService.showFriendHistory(chatId, page.toIPage(FriendChat.class));

            if(messageVos == null) {
                return Result.fail(ErrorCode.SHOW_FRIEND_CHAT_HISTORY_ERROR);
            }
            return Result.success(messageVos);
        } else {
            List<MessageVo> messageVos = chatService.showGroupHistory(chatId, page.toIPage(GroupChat.class));
            if(messageVos == null) {
                return Result.fail(ErrorCode.SHOW_GROUP_CHAT_HISTORY_ERROR);
            }
            return Result.success(messageVos);
        }
    }
    @LogA
    @PostMapping("send")
    public Result sendMsg(@RequestBody MessageVo msgInfo) {
        if(msgInfo.getType().equals("friend")) {
            FriendChat friendChat = new FriendChat();
            friendChat.setReceiverId(msgInfo.getChatId());
            friendChat.setSenderId(UserThreadLocal.get().getId());
            friendChat.setMsg(msgInfo.getMsg());
            friendChat.setType(msgInfo.getMsgType());
            friendChat.setDate(new Date().getTime());
            boolean b = chatService.sendFriendMsg(friendChat);
            if(b) {
                return Result.success(null);
            }
            return Result.fail(ErrorCode.SEND_FRIEND_CHAT_HISTORY_ERROR);
        } else {
            GroupChat groupChat = new GroupChat();
            groupChat.setGroupId(msgInfo.getChatId());
            groupChat.setSenderId(UserThreadLocal.get().getId());
            groupChat.setMsg(msgInfo.getMsg());
            groupChat.setType(msgInfo.getMsgType());
            groupChat.setDate(new Date().getTime());
            boolean b = chatService.sendGroupMsg(groupChat);
            if(b) {
                return Result.success(null);
            }
            return Result.fail(ErrorCode.SEND_GROUP_CHAT_HISTORY_ERROR);
        }
    }
    @LogA
    @PutMapping("leaveRoom/{roomId}/{isFriend}")
    public Result leaveRoom(@PathVariable String roomId,
                            @PathVariable Boolean isFriend) {
        // roomId is friendId or groupId
        boolean b = false;
        if(isFriend) {
            // friend updates user departure times
            b = chatService.leaveRoom(roomId, isFriend);
        } else {
            // group update user departure time
            b = chatService.leaveRoom(roomId, isFriend);
        }
        if(b) {
            return Result.success(null);
        }
        if(isFriend) {
            return Result.fail(ErrorCode.LEAVE_FRIEND_CHAT_ERROR);
        }
        return Result.fail(ErrorCode.LEAVE_GROUP_CHAT_ERROR);
    }

}
