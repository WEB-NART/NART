package com.nart.vo;


import com.nart.dao.GroupDao;
import com.nart.dao.GroupInviteDao;
import com.nart.dao.UserDao;
import com.nart.pojo.Group;
import com.nart.pojo.GroupInvite;
import com.nart.pojo.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class InviteVo {
    private String id;
    private String groupId;
    private String msg;
    private String groupName;
    private String groupAvatar;
    private String senderName;
    private String receiverId;
//    @Autowired
//    private GroupDao groupDao;
//    @Autowired
//    private UserDao userDao;

    public InviteVo transfer(GroupInvite groupInvite){
        InviteVo inviteVo = new InviteVo();
        inviteVo.setId(groupInvite.getId());
        inviteVo.setGroupId(groupInvite.getGroupId());
        inviteVo.setMsg(groupInvite.getMsg());

//        User user = userDao.selectById(groupInvite.getSenderId());
//        inviteVo.setSenderName(user.getName());

        return inviteVo;
    }

}
