package com.nart.vo;

import com.nart.pojo.Group;
import lombok.Data;

@Data
public class GroupVo {
    private String id;
    private String name;
    private String avatar;
    private String notice;
    private Integer state;
    private Boolean newMsg;

    public GroupVo transfer(Group group){
        GroupVo groupVo = new GroupVo();
        groupVo.setId(group.getId());
        groupVo.setName(group.getGroupName());
        groupVo.setAvatar(group.getAvatar());
        groupVo.setNotice(group.getNotice());
        groupVo.setState(group.getState());
        groupVo.setNewMsg(group.getNewMessage());
        return groupVo;
    }
}
