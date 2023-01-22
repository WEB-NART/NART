package com.nart.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_user_group")
public class UserGroup {
    private String id;
    private String uid;
    private String gid;
    private int state;
    @TableField(value = "join_level")
    private int joinLevel;
    @TableField(value = "user_level_time")
    private Long userLevelTime;


}
