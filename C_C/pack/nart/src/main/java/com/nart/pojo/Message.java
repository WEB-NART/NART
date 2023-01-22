package com.nart.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_user")
public class Message {
    private String receiverId;
    private String senderId;
    private String msg;
    private String type;
    private Long date;
    private int level;

}
