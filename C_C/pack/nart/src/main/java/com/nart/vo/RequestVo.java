package com.nart.vo;

import com.nart.pojo.FriendReq;
import lombok.Data;
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

        return requestVo;
    }

    public FriendReq tofRequest() {
        FriendReq friendReq = new FriendReq();
        friendReq.setId(this.id);
        friendReq.setSenderId(this.friendId);
        friendReq.setMsg(this.msg);
        return friendReq;
    }

}
