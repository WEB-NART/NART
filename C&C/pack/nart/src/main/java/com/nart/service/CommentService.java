package com.nart.service;

import com.nart.pojo.Comment;

import java.util.List;
/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: NART
 * @className: CommentService
 *  This method can search the corresponding comments according to the ID of status.
 * This method can also upload new comments.
 *
 * @version: v1.17.0
 * @Author YUNZHOU LIU
 * @Date 2023-1-14
 *
 */
public interface CommentService {


    /**
     *  This method finds all comments for this status according to the status id entered.
     * @param statusId, The id of Status
     * @return List of Comment , All comments in the tb_comment, that the status_id is same as params.
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    List<Comment> showCommentList(String statusId);

    /**
     *  This method combines the input parameters into a comment and uploads it to the database.
     * @param statusId ,The id of Status
     * @param sid  , The id of user who send this comment
     * @param msg , The message who want to send
     * @return boolean,  If it is true, the comment is sent successfully. If it is false, the comment is sent failed.
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean postComment(String statusId, String msg, String sid);

    /**
     *  delete all statusId related comments
     * @param statusId id of status
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-01-08 4:33 p.m.
     */
    boolean delComment(String statusId);
}
