package com.nart.controller;

import com.nart.common.LogA;
import com.nart.pojo.Comment;
import com.nart.service.CommentService;
import com.nart.util.ErrorCode;
import com.nart.util.Result;
import com.nart.util.UserThreadLocal;
import com.nart.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: CommentController
 *  comment related request controller
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/01/12 15:10
 */
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @LogA
    @GetMapping("list/{statusId}")
    public Result showCommentList(@PathVariable String statusId) {
        CommentVo commentVo = new CommentVo();
        List<Comment> Comments = commentService.showCommentList(statusId);

        List<CommentVo> commentVos = new ArrayList<>();
        for (Comment comment : Comments) {
            CommentVo transfer = commentVo.transfer(comment);
            commentVos.add(transfer);
        }

        if(commentVos == null) {
            return Result.fail(ErrorCode.SHOW_COMMENT_LIST_ERROR);
        }
        return Result.success(commentVos);
    }

    @LogA
    @PostMapping("/post")
    public Result postComment(@RequestBody CommentVo cInfo) {
        boolean b = commentService.postComment(cInfo.getStatusId(), cInfo.getMsg(), UserThreadLocal.get().getId());
        if(b) {
            return Result.success(null);
        }
        return Result.fail(ErrorCode.POST_COMMENT_ERROR);
    }
}
