package com.nart.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_friend_chat")
public class FriendChat {
    private Long id;

    @TableField(value = "rid")
    private String receiverId;
    @TableField(value = "sid")
    private String senderId;
    @TableField(exist = false)
    private int level;

    private String msg;
    private String type;
    private Long date;

}
