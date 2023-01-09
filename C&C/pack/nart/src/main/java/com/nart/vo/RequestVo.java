package com.nart.vo;

import com.nart.dao.UserDao;
import com.nart.pojo.FriendReq;
import com.nart.pojo.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class RequestVo {
    private String id;
    private String friendId;
    private String msg;
    private String friendName;
    private String friendAvatar;
    private String senderName;


    public RequestVo transfer(FriendReq friendReq){
        RequestVo requestVo = new RequestVo();
        requestVo.setId(friendReq.getId());
        requestVo.setFriendId(friendReq.getSenderId());
        requestVo.setMsg(friendReq.getMsg());




        ;


        return requestVo;
    }

}
