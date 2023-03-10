package com.nart.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nart.dao.*;
import com.nart.pojo.Comment;
import com.nart.pojo.Status;
import com.nart.pojo.User;
import com.nart.service.AdminService;
import com.nart.service.CommentService;
import com.nart.service.DataCounterService;
import com.nart.service.StatusService;
import com.nart.util.RedisUtil;
import com.nart.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AdminServiceImpl implements AdminService {
    private final FriendChatDao friendChatDao;
    private final GroupChatDao groupChatDao;
    private final UserDao userDao;
    private final CommentDao commentDao;
    private final GroupDao groupDao;
    private final StatusDao statusDao;
    private final CommentService commentService;
    private final DataCounterService dataCounterService;
    private final RedisUtil redisUtil;
    private final FriendDao friendDao;
    private final UserGroupDao userGroupDao;
    private final StatusService statusService;

    @Autowired
    public AdminServiceImpl(UserDao userDao,
                            FriendChatDao friendChatDao,
                            GroupChatDao groupChatDao,
                            CommentDao commentDao,
                            GroupDao groupDao,
                            StatusDao statusDao,
                            CommentService commentService,
                            DataCounterService dataCounterService,
                            RedisUtil redisUtil,
                            FriendDao friendDao,
                            StatusService statusService,
                            UserGroupDao userGroupDao) {
        this.userDao = userDao;
        this.friendChatDao = friendChatDao;
        this.groupChatDao = groupChatDao;
        this.commentDao = commentDao;
        this.groupDao = groupDao;
        this.statusDao = statusDao;
        this.commentService = commentService;
        this.dataCounterService = dataCounterService;
        this.redisUtil = redisUtil;
        this.friendDao = friendDao;
        this.statusService = statusService;
        this.userGroupDao = userGroupDao;
    }

    @Override
    public List<User> showAllUserInfo() {
        return userDao.selectList(null);
    }

    @Override
    public int showAllUserNum() {
        List<User> users = userDao.selectList(null);
        return users.size();
    }

    @Override
    public List<User> showOnlineUserInfo() {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.eq(User::getUserOnline, 1);

        return userDao.selectList(lqw);
    }

    @Override
    public int showOnlineUserNum() {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.eq(User::getUserOnline, 1);
        List<User> users = userDao.selectList(lqw);
        return users.size();
    }

    @Override
    public List<Status> showAllStatusInfo() {
        LambdaQueryWrapper<Status> lqw1 = new LambdaQueryWrapper<Status>();
        lqw1.orderBy(true,false, Status::getCreateDate);
        List<Status> statuses = statusDao.selectList(lqw1);
        for (Status status : statuses) {
            String id = status.getId();
            List<Comment> comments = commentService.showCommentList(id);
            status.setCommentList(comments);
        }
        return statuses;
    }

    @Override
    public int showAllStatusNum() {
        List<Status> statuses = statusDao.selectList(null);
        return statuses.size();
    }

    @Override
    public List<Comment> showAllCommentInfo() {
        return commentDao.selectList(null);
    }

    @Override
    public int showAllCommentNum() {
        List<Comment> comments = commentDao.selectList(null);
        for (int i = 0; i < comments.size(); i++){
            for (int j = comments.size()-1; j > i; j--){
                if (comments.get(j).equals(comments.get(i))){
                    comments.remove(j);
                }
            }

        }

        return comments.size();
    }

    @Override
    public User searchUser(String id) {
        return userDao.selectById(id);
    }


    @Override
    public boolean blockUser(String id) {
        User user = userDao.selectById(id);
        user.setState(1);
        int i = userDao.updateById(user);
        boolean a = false;
        if (i == 1){
            a = true;
        }
        return a;
    }


//    @Override
//    public boolean setonline(String id) {
//        User user = userDao.selectById(id);
//        user.setUserOnline(1);
//        int i = userDao.updateById(user);
////        int a = 1/0;
//        return i>0;
//    }


    @Override
    public boolean deleteStatus(String id) {
        Status status = statusDao.selectById(id);
        String id1 = status.getId();
        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<Comment>();
        lqw.eq(Comment::getStatusId, id1);
        List<Comment> comments = commentDao.selectList(lqw);
        for (Comment comment : comments) {
            commentDao.deleteById(comment.getId());
        }
        int i = statusDao.deleteById(id1);
        boolean a = false;
        if (i == 1){
            a = true;
        }
        return a;
    }

    @Override
    public boolean deleteComment(String id) {

        int i = commentDao.deleteById(id);
        boolean a = false;
        if (i == 1){
            a = true;
        }
        return a;
    }

//    @Override
//    public Result logOut() {
//        return null;
//    }
//
//    @Override
//    public Result logIn() {
//        return null;
//    }
//
//    @Override
//    public boolean checkAdmin() {
//        return false;
//    }


}
