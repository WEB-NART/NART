package com.nart.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("tb_friend")
public class Friend {
    private String id;
    private String uid;
    private String fid;
    private Integer state;
    @TableField(value = "leave_time")
    private Long leaveTime;

    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String email;
    @TableField(exist = false)
    private String avatar;
    @TableField(exist = false)
    private Boolean online;

//    private Chat chat;

    @TableField(exist = false)
    private List<Status> statusList;
    @TableField(exist = false)
    private List<FriendChat> chatHistory;
    @TableField(exist = false)
    private Boolean newMessage;


}
