package com.nart.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_comment")
public class Comment {
    private String id;
    private String msg;
    @TableField(value = "status_id")
    private String statusId;
    @TableField(value = "date")
    private Long createDate;
    @TableField(value = "sid")
    private String userId;

    @TableField(exist = false)
    private String uname;

}
