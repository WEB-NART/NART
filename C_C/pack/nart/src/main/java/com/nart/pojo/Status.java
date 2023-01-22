package com.nart.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("tb_status")
public class Status {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @TableField(value = "sid")
    private String senderId;
    private String text;
    private String pics;
    private int likes;
    @TableField(value = "date")
    private Long createDate;


    @TableField(exist = false)
    private Boolean userLike;

    @TableField(exist = false)
    private List<Comment> commentList;

}
