package com.nart.vo;

import com.nart.pojo.FriendChat;
import com.nart.pojo.GroupChat;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class MessageVo {
    private String msgId;
    private String msgType;
    private String type;

    private String chatId;
    private String senderId;
    private String senderName;
    private String senderAvatar;
    private DateVo sentDate;
    private String msg;


    public MessageVo Ftransfer(FriendChat friendChat){
        MessageVo messageVo = new MessageVo();
        messageVo.setMsgId(String.valueOf(friendChat.getId()));
        messageVo.setMsgType(friendChat.getType());
        messageVo.setChatId(friendChat.getReceiverId());
        messageVo.setSenderId(friendChat.getSenderId());




        DateVo dateVo = new DateVo();
        DateVo dateToString = dateVo.getDateToString(friendChat.getDate());
        messageVo.setSentDate(dateToString);

        messageVo.setMsg(friendChat.getMsg());

        return messageVo;
    }

    public MessageVo Gtransfer(GroupChat groupChat){
        MessageVo messageVo = new MessageVo();
        messageVo.setMsgId(String.valueOf(groupChat.getId()));
        messageVo.setMsgType(groupChat.getType());
        messageVo.setChatId(groupChat.getGroupId());
        messageVo.setSenderId(groupChat.getSenderId());
//        User user = userDao.selectById(groupChat.getSenderId());
//        messageVo.setSenderName(user.getName());
//        messageVo.setSenderAvatar(user.getAvatar());
        DateVo dateVo = new DateVo();
        DateVo dateToString = dateVo.getDateToString(groupChat.getDate());
        messageVo.setSentDate(dateToString);
        messageVo.setMsg(groupChat.getMsg());
        return messageVo;
    }

}
