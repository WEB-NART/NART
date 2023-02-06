package com.nart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nart.dao.*;
import com.nart.pojo.*;
import com.nart.service.LoadDataInDataBase;
import com.nart.util.ProgressBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final FriendDao friendDao;
    private final FriendChatDao friendChatDao;
    private final UserLikeDao userLikeDao;
    private UserGroupDao userGroupDao;
    private GroupChatDao groupChatDao;

    private final ProgressBar pb = new ProgressBar();

    @Autowired
    public LoadDataInDataBaseImpl(UserDao userDao,
                                  FriendReqDao friendReqDao,
                                  StatusDao statusDao,
                                  GroupDao groupDao,
                                  CommentDao commentDao,
                                  GroupInviteDao groupInviteDao,
                                  FriendDao friendDao,
                                  FriendChatDao friendChatDao, UserLikeDao userLikeDao, UserGroupDao userGroupDao, GroupChatDao groupChatDao) {
        this.userDao = userDao;
        this.friendReqDao = friendReqDao;
        this.statusDao = statusDao;
        this.groupDao = groupDao;
        this.commentDao = commentDao;
        this.groupInviteDao = groupInviteDao;
        this.friendDao = friendDao;
        this.friendChatDao = friendChatDao;
        this.userLikeDao = userLikeDao;
        this.userGroupDao = userGroupDao;
        this.groupChatDao = groupChatDao;
    }


    @Override
    public boolean LoadListUser(List<User> userList) {
        boolean flag = true;
        int total = userList.size();
        int count = 0;
        System.out.println("=========加载 UserList, 共" + total + "条数据=========");
        pb.printProgress();
        for (User user : userList) {
            int insert = userDao.insert(user);
            if (insert <= 0) flag = false;
            if (++count >= total/100) {
                pb.load();
                count = 0;
            }
        }
        return flag;
    }

    @Override
    public boolean LoadListFriendReq(List<FriendReq> friendReqList) {
        boolean flag = true;
        int total = friendReqList.size();
        int count = 0;
        System.out.println("=========加载 FriendRequest, 共" + total + "条数据=========");
        pb.printProgress();
        for (FriendReq friendReq : friendReqList) {
            int insert = friendReqDao.insert(friendReq);
            if (insert <= 0) flag = false;
            if (++count >= total/100) {
                pb.load();
                count = 0;
            }
        }
        return flag;
    }

    @Override
    public boolean LoadListStatus(List<Status> statusList) {
        boolean flag = true;
        int total = statusList.size();
        int count = 0;
        System.out.println("=========加载 StatusList, 共" + total + "条数据=========");
        pb.printProgress();
        for (Status status : statusList) {
            int insert = statusDao.insert(status);
            if (insert <= 0) flag = false;
            if (++count >= total/100) {
                pb.load();
                count = 0;
            }
        }
        return flag;
    }

    @Override
    public boolean LoadListGroup(List<Group> groupList) {
        boolean flag = true;
        int total = groupList.size();
        int count = 0;
        System.out.println("=========加载 GroupList, 共" + total + "条数据=========");
        for (Group group : groupList) {
            int insert = groupDao.insert(group);
            if (insert <= 0) flag = false;
            if (++count >= total/100) {
                pb.load();
                count = 0;
            }
        }
        return flag;
    }

    @Override
    public boolean LoadListComment(List<Comment> commentList) {
        boolean flag = true;
        System.out.println("写入 Comment" + commentList.size());
        for (Comment comment : commentList) {
            int insert = commentDao.insert(comment);
            if (insert <= 0) flag = false;
        }

        return flag;

    }

    @Override
    public boolean LoadListGroupInvite(List<GroupInvite> groupInviteList) {
        boolean flag = true;
        int total = groupInviteList.size();
        int count = 0;
        System.out.println("=========加载 InviteList, 共" + total + "条数据=========");
        for (GroupInvite groupInvite : groupInviteList) {
            int insert = groupInviteDao.insert(groupInvite);
            if (insert <= 0) flag = false;
            if (++count >= total/100) {
                pb.load();
                count = 0;
            }
        }
        return flag;
    }

    @Override
    public boolean LoadListFriendShips(List<Friend> friendList) {
        List<Integer> totalDelete = new ArrayList<>();
        boolean flag = true;
        for (Friend friend : friendList) {
            Friend friendReverse = new Friend();
            friendReverse.setFid(friend.getUid());
            friendReverse.setUid(friend.getFid());
            friendReverse.setState(0);
            friendReverse.setLeaveTime(0L);
            int insert = friendDao.insert(friend);
            int insert1 = friendDao.insert(friendReverse);
            if (insert < 1 || insert1 < 1) flag = false;

            LambdaQueryWrapper<FriendReq> lqw = new LambdaQueryWrapper<FriendReq>();
            lqw.select(FriendReq::getSenderId)
                    .eq(FriendReq::getSenderId, friend.getFid())
                    .eq(FriendReq::getReceiverId, friend.getUid())
                    .or()
                    .eq(FriendReq::getSenderId, friend.getUid())
                    .eq(FriendReq::getReceiverId, friend.getFid());
            int delete = friendReqDao.delete(lqw);
            totalDelete.add(delete);
        }
        int sum = totalDelete.stream().mapToInt(x -> x).sum();
        return flag;
    }

    @Override
    public boolean LoadListFriendChats(List<FriendChat> friendChatList) {
        boolean flag = true;
        int total = friendChatList.size();
        int count = 0;
        System.out.println("=========加载 FriendChatList, 共" + total + "条数据=========");
        for (FriendChat friendChat : friendChatList) {
            int insert = friendChatDao.insert(friendChat);
            if (insert < 1) flag = false;
            if (++count >= total/100) {
                pb.load();
                count = 0;
            }
        }
        return flag;
    }

    @Override
    public boolean LoadListLikes(List<UserLike> likeList) {
        boolean flag = true;
        for (UserLike userLike : likeList) {
            System.out.println("写入 Likes" + likeList.size());
            int insert = userLikeDao.insert(userLike);
            if (insert < 1) flag = false;
        }
        return flag;
    }

    @Override
    public boolean LoadListGroupShips(List<UserGroup> userGroups) {
        boolean flag = true;
        int total = userGroups.size();
        int count = 0;
        System.out.println("=========加载 GroupShips, 共" + total + "条数据=========");
        for (UserGroup userGroup : userGroups) {
            int insert = userGroupDao.insert(userGroup);
            if (insert < 1) flag = false;
            if (++count >= total/100) {
                pb.load();
                count = 0;
            }
        }
        return flag;
    }

    @Override
    public boolean LoadListGroupChats(List<GroupChat> groupChatList) {
        boolean flag = true;
        int total = groupChatList.size();
        int count = 0;
        System.out.println("=========加载 GroupChatList, 共" + total + "条数据=========");
        for (GroupChat groupChat : groupChatList) {
            int insert = groupChatDao.insert(groupChat);
            if (insert < 1) flag = false;
            if (++count >= total/100) {
                pb.load();
                count = 0;
            }
        }
        return flag;
    }


}
