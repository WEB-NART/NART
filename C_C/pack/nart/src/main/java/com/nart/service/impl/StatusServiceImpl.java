package com.nart.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nart.dao.FriendDao;
import com.nart.dao.StatusDao;
import com.nart.dao.UserDao;
import com.nart.pojo.Comment;
import com.nart.pojo.Friend;
import com.nart.pojo.Status;
import com.nart.service.CommentService;
import com.nart.service.DataCounterService;
import com.nart.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class StatusServiceImpl implements StatusService {

    private final CommentService commentService;

    private final DataCounterService dataCounterService;

    private final UserDao userDao;

    private final FriendDao friendDao;

    private final StatusDao statusDao;

    @Autowired
    public StatusServiceImpl(CommentService commentService, DataCounterService dataCounterService, UserDao userDao, FriendDao friendDao, StatusDao statusDao) {
        this.commentService = commentService;
        this.dataCounterService = dataCounterService;
        this.userDao = userDao;
        this.friendDao = friendDao;
        this.statusDao = statusDao;
    }

    @Override
    public List<Status> showStatusList(String sid, IPage page) {
        LambdaQueryWrapper<Status> lqw = new LambdaQueryWrapper<Status>();
        lqw.eq(Status::getSenderId, sid);
        lqw.orderBy(true,false, Status::getCreateDate);
        IPage iPage = statusDao.selectPage(page, lqw);
        List<Status> records = iPage.getRecords();
        for (Status record : records) {
            if(record.getLikes()!=0){
                record.setUserLike(true);
            }
            String id = record.getId();
            List<Comment> Comments = commentService.showCommentList(id);
            record.setCommentList(Comments);

        }
        return records;
    }

    @Override
    public List<Status> showAllStatusList(String uid, IPage page) {
//        User user = userDao.selectById(uid);
        LambdaQueryWrapper<Friend> lqw = new LambdaQueryWrapper<Friend>();
        lqw.eq(Friend::getUid, uid);
        List<Friend> friends = friendDao.selectList(lqw);
        List<String> friendIds = new ArrayList<>();
        for (Friend friend : friends) {
            friendIds.add(friend.getFid());
        }
        friendIds.add(uid);
        LambdaQueryWrapper<Status> lqw1 = new LambdaQueryWrapper<Status>();
        lqw1.in(Status::getSenderId, friendIds);
        lqw1.orderBy(true,false, Status::getCreateDate);
        IPage<Status> statusPages = statusDao.selectPage(page, lqw1);
        List<Status> statuses = statusPages.getRecords();
        List<Status> Allstatuses = new ArrayList<Status>(statuses);
        Allstatuses.sort((a, b) -> b.getCreateDate().compareTo(a.getCreateDate()));

        for (Status record : Allstatuses) {
            if(record.getLikes()!=0){
                record.setUserLike(true);
            }
            String id = record.getId();
            List<Comment> Comments = commentService.showCommentList(id);
            record.setCommentList(Comments);

        }
        return Allstatuses;
    }

    @Override
    public boolean postStatus(Status status) {
        int insert = statusDao.insert(status);
        dataCounterService.updateStatusAmount(true);
        boolean a = false;
        if (insert == 1){
            a = true;
        }
        return a;
    }

    @Override
    public boolean delStatus(String id) {
        commentService.delComment(id);
        int id1 = statusDao.deleteById(id);
        dataCounterService.updateStatusAmount(false);
        boolean a = false;
        if (id1 == 1){
            a = true;
        }
        return a;

    }

    @Override
    public boolean likeStatus(String id, boolean like) {
        int i;
        if(like){
            Status status = statusDao.selectById(id);
            status.setLikes(status.getLikes()+1);
            i = statusDao.updateById(status);
            boolean a = false;
            if (i == 1){
                a = true;
            }
            return a;
        }else{
            Status status = statusDao.selectById(id);
            status.setLikes(status.getLikes()-1);
            i = statusDao.updateById(status);
            boolean a = false;
            if (i == 1){
                a = true;
            }
            return a;
        }

    }
}
