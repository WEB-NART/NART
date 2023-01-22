package com.nart.vo;

import com.nart.pojo.Comment;
import lombok.Data;

@Data
public class CommentVo {
    private String statusId;
    private String uname;
    private String msg;
    private DateVo createDate;

    public CommentVo transfer(Comment comment){
        CommentVo commentVo = new CommentVo();
        commentVo.setMsg(comment.getMsg());
        commentVo.setUname(comment.getUname());
        Long createDate = comment.getCreateDate();

        DateVo dateVo = new DateVo();
        DateVo dateToString = dateVo.getDateToString(createDate);
        commentVo.setCreateDate(dateToString);
        commentVo.setStatusId(comment.getStatusId());

        return commentVo;
    }
}
