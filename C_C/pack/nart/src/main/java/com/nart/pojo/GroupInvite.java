package com.nart.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_invitation")
public class GroupInvite {
    private String id;
    @TableField(value = "gid")
    private String groupId;
    @TableField(value = "sid")
    private String senderId;
    @TableField(value = "rid")
    private String receiverId;
    private String msg;
    private Long date;

}
