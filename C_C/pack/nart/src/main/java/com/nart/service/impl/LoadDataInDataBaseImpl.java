package com.nart.service.impl;

import com.nart.dao.*;
import com.nart.pojo.*;
import com.nart.service.LoadDataInDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class LoadDataInDataBaseImpl implements LoadDataInDataBase {

    private final UserDao userDao;
    private final FriendReqDao friendReqDao;
    private final StatusDao statusDao;
    private final GroupDao groupDao;
    private final CommentDao commentDao;
    private final GroupInviteDao groupInviteDao;

    @Autowired
    public LoadDataInDataBaseImpl(UserDao userDao, FriendReqDao friendReqDao, StatusDao statusDao, GroupDao groupDao, CommentDao commentDao, GroupInviteDao groupInviteDao) {
        this.userDao = userDao;
        this.friendReqDao = friendReqDao;
        this.statusDao = statusDao;
        this.groupDao = groupDao;
        this.commentDao = commentDao;
        this.groupInviteDao = groupInviteDao;
    }


    @Override
    public boolean LoadListUser(List<User> userList) {

        for (User user : userList) {
            int insert = userDao.insert(user);
        }
        return true;
    }

    @Override
    public boolean LoadListFriendReq(List<FriendReq> friendReqList) {
        for (FriendReq friendReq : friendReqList) {
            int insert = friendReqDao.insert(friendReq);
        }
        return true;
    }

    @Override
    public boolean LoadListStatus(List<Status> statusList) {
        for (Status status : statusList) {
            int insert = statusDao.insert(status);

        }
        return true;
    }

    @Override
    public boolean LoadListGroup(List<Group> groupList) {
        for (Group group : groupList) {
            int insert = groupDao.insert(group);
        }
        return true;
    }

    @Override
    public boolean LoadListComment(List<Comment> commentList) {
        for (Comment comment : commentList) {
            int insert = commentDao.insert(comment);

        }

        return true;

    }

    @Override
    public boolean LoadListGroupInvite(List<GroupInvite> groupInviteList) {

        for (GroupInvite groupInvite : groupInviteList) {
            int insert = groupInviteDao.insert(groupInvite);

        }
        return true;
    }
}
