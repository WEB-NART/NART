package com.nart.vo;

import com.nart.pojo.Friend;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class FriendVo {
    private String id;
    private String name;
    private String avatar;
    private Integer state;
    private Boolean newMsg;

//    @Autowired
//    private UserDao userDao;

    public FriendVo transfer(Friend friend){
        FriendVo friendVo = new FriendVo();
        friendVo.setId(friend.getFid());
        friendVo.setAvatar(friend.getAvatar());
        friendVo.setName(friend.getName());
        friendVo.setState(Integer.valueOf(friend.getState()));
        friendVo.setNewMsg(friend.getNewMessage());


//        User user = userDao.selectById(friend.getUid());
//        friendVo.setUname(user.getName());
        return friendVo;

    }

    public Friend toFriend() {
        Friend friend = new Friend();
        friend.setFid(this.id);
        friend.setAvatar(this.avatar);
        friend.setName(this.name);
        friend.setState(this.state);
        friend.setNewMessage(this.newMsg);
        return friend;
    }
}
