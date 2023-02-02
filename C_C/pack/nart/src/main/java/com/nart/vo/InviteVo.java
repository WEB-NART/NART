package com.nart.vo;


import com.nart.pojo.GroupInvite;
import lombok.Data;
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

    public GroupInvite toGI() {
        GroupInvite groupInvite = new GroupInvite();
        groupInvite.setId(this.id);
        groupInvite.setGroupId(this.groupId);
        groupInvite.setMsg(this.msg);
        return groupInvite;
    }

}
