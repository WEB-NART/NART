package com.nart.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("tb_user")
public class User {

// 主键生成策略

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String pwd;
    private String avatar;
    @TableField(value = "uname")
    private String name;
    private String tel;
    private String address;
    private String email;
    private String age;
    @TableField(value = "on_line")
    private Integer userOnline;
    private String tpwd;
    private Integer power;
    private Integer state;
    @TableField(exist = false)
    private List<Friend> friendList;
    @TableField(exist = false)
    private List<FriendReq> requestList;
    @TableField(exist = false)
    private List<Group> groupList;
    @TableField(exist = false)
    private List<GroupInvite> GroupInvites;
    @TableField(exist = false)
    private List<Status> statusList;
}
