package com.nart.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_user_like")
public class UserLike {
    private String id;
    private String uid;
    @TableField(value = "status_id")
    private String statusId;
}
