package com.nart.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("tb_group")
public class Group {

    private String id;
    @TableField(value = "gname")
    private String groupName;
    private String notice;
    private String avatar;
    @TableField(value = "last_level")
    private int userLevel;

    @TableField(exist = false)
    private List<Friend> userList;
    @TableField(exist = false)
    private int state;

    @TableField(exist = false)
    private List<Message> chatHistory;

    @TableField(exist = false)
    private Boolean newMessage;

    public Group(String id) {
        this.id = id;
    }

    public Group() {}
}
