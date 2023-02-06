package com.nart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nart.dao.CommentDao;
import com.nart.dao.UserDao;
import com.nart.pojo.Comment;
import com.nart.pojo.User;
import com.nart.service.CommentService;
import com.nart.service.DataCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    private final UserDao userDao;

    private final DataCounterService dataCounterService;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, UserDao userDao, DataCounterService dataCounterService) {
        this.commentDao = commentDao;
        this.userDao = userDao;
        this.dataCounterService = dataCounterService;
    }

    @Override
    public List<Comment> showCommentList(String statusId) {
        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<Comment>();
//
        lqw.eq(Comment::getStatusId,statusId);
        lqw.orderBy(true,false, Comment::getCreateDate);

        List<Comment> Comments = commentDao.selectList(lqw);
        for (Comment comment : Comments) {
            String userId = comment.getUserId();
            User user = userDao.selectById(userId);
            String name = user.getName();
            comment.setUname(name);
        }

        return Comments;
    }

    @Override
    public boolean postComment(String statusId, String msg, String sid) {
        Comment Comment = new Comment();
        Comment.setMsg(msg);
        Comment.setStatusId(statusId);
        Comment.setUserId(sid);
        long l = System.currentTimeMillis();
        Long createTime = l;
        //System.out.println(l);
        Comment.setCreateDate(createTime);
        dataCounterService.updateCommentAmount(true);
        int a = commentDao.insert(Comment);
//        System.out.println(a);
        return a >= 1;
    }

    @Override
    public boolean delComment(String statusId) {
        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<Comment>();
        lqw.eq(Comment::getStatusId, statusId);
        List<Comment> Comments = commentDao.selectList(lqw);
        boolean flag = true;
        for (Comment comment : Comments) {
            int i = commentDao.deleteById(comment.getId());
            if(i <= 0) flag = false;
        }
        return flag;
    }
}
