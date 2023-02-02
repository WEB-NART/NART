package com.nart.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nart.dao.*;
import com.nart.pojo.*;
import com.nart.service.ChatService;
import com.nart.service.DataCounterService;
import com.nart.util.RedisUtil;
import com.nart.util.UserThreadLocal;
import com.nart.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Transactional
@Service
public class ChatServiceImpl implements ChatService {
    private final FriendChatDao friendChatDao;
    private final GroupChatDao groupChatDao;
    private final UserDao userDao;
    private final GroupDao groupDao;
    private final DataCounterService dataCounterService;
    private final RedisUtil redisUtil;
    private final FriendDao friendDao;
    private final UserGroupDao userGroupDao;

    @Autowired
    public ChatServiceImpl(FriendChatDao friendChatDao,
                           GroupChatDao groupChatDao,
                           UserDao userDao,
                           GroupDao groupDao,
                           DataCounterService dataCounterService,
                           RedisUtil redisUtil,
                           FriendDao friendDao,
                           UserGroupDao userGroupDao) {
        this.friendChatDao = friendChatDao;
        this.groupChatDao = groupChatDao;
        this.userDao = userDao;
        this.groupDao = groupDao;
        this.dataCounterService = dataCounterService;
        this.redisUtil = redisUtil;
        this.friendDao = friendDao;
        this.userGroupDao = userGroupDao;
    }

    @Override
    public boolean sendFriendMsg(FriendChat friendChat) {

//        String id = UserThreadLocal.get().getId();
//        Object o = redisUtil.get(id);

        int insert = friendChatDao.insert(friendChat);
        dataCounterService.updateMessageAmount(true);
        boolean a = false;
        if (insert == 1){
            a = true;
        }
        return a;
    }



    @Override
    public boolean sendGroupMsg(GroupChat groupChat) {
        String groupId = groupChat.getGroupId();

        Group group = groupDao.selectById(groupId);
        int userLevel = group.getUserLevel();
        groupChat.setLevel(userLevel);
        int insert = groupChatDao.insert(groupChat);
        dataCounterService.updateMessageAmount(true);
        boolean a = false;
        if (insert == 1){
            a = true;
        }
        return a;
    }

    @Override
    public List<FriendChat> receiveFriendMsg(String receiveId, IPage page) {

        LambdaQueryWrapper<FriendChat> lqw = new LambdaQueryWrapper<FriendChat>();
        lqw.eq(FriendChat::getReceiverId, receiveId);
        IPage iPage = friendChatDao.selectPage(page, lqw);
        List<FriendChat> records = iPage.getRecords();
        return records;
    }

    @Override
    public List<GroupChat> receiveGroupMsg(String receiveId, IPage page) {
        LambdaQueryWrapper<GroupChat> lqw = new LambdaQueryWrapper<GroupChat>();
        lqw.eq(GroupChat::getGroupId, receiveId);
        IPage iPage = groupChatDao.selectPage(page, lqw);
        List<GroupChat> records = iPage.getRecords();
        return records;
    }

    @Override
    public List<MessageVo> showFriendHistory(String Id, IPage page) {
        LambdaQueryWrapper<FriendChat> lqw = new LambdaQueryWrapper<FriendChat>();
        lqw.eq(FriendChat::getSenderId, Id).eq(FriendChat::getReceiverId,UserThreadLocal.get().getId()).or();
        lqw.eq(FriendChat::getSenderId, UserThreadLocal.get().getId()).eq(FriendChat::getReceiverId,Id);
        lqw.orderBy(true,false, FriendChat::getDate);

        IPage iPage = friendChatDao.selectPage(page, lqw);
        List<FriendChat> records = iPage.getRecords();
        //System.out.println(records);
        MessageVo messageVo = new MessageVo();
        List<MessageVo> messageVos = new ArrayList<>();

        for (FriendChat record : records) {
            MessageVo ftransfer = messageVo.Ftransfer(record);

            User user = userDao.selectById(record.getSenderId());
//            System.out.println(user);
            if(user!=null){
                ftransfer.setSenderName(user.getName());
                ftransfer.setSenderAvatar(user.getAvatar());
            }

            messageVos.add(ftransfer);
        }
        return messageVos;
    }

    @Override
    public List<MessageVo> showGroupHistory(String gId, IPage page) {

        LambdaQueryWrapper<GroupChat> lqw = new LambdaQueryWrapper<GroupChat>();
        lqw.eq(GroupChat::getGroupId, gId);
        // output chat logs by most recent and level max
        lqw.orderBy(true,false, GroupChat::getLevel, GroupChat::getDate);

        IPage iPage = groupChatDao.selectPage(page, lqw);
        List<GroupChat> records = iPage.getRecords();

        MessageVo messageVo = new MessageVo();
        List<MessageVo> messageVos = new ArrayList<>();

        for (GroupChat record : records) {
            MessageVo ftransfer = messageVo.Gtransfer(record);

            User user = userDao.selectById(record.getSenderId());
            if (user != null) {
                ftransfer.setSenderName(user.getName());
                ftransfer.setSenderAvatar(user.getAvatar());
            }
            messageVos.add(ftransfer);
        }

        return messageVos;

    }

    @Override
    public boolean leaveRoom(String roomId, Boolean isF) {
        if (isF){
            LambdaQueryWrapper<Friend> lqw = new LambdaQueryWrapper<Friend>();
            lqw.eq(Friend::getFid, roomId);
            lqw.eq(Friend::getUid, UserThreadLocal.get().getId());

            Friend friend = friendDao.selectOne(lqw);
            long timeStamp = getTimeStamp();
            friend.setLeaveTime(timeStamp);
            int i = friendDao.updateById(friend);
            return i >= 1;
        }else {
            LambdaQueryWrapper<UserGroup> lqw = new LambdaQueryWrapper<UserGroup>();
            lqw.eq(UserGroup::getGid, roomId);
            lqw.eq(UserGroup::getUid, UserThreadLocal.get().getId());
            UserGroup userGroup = userGroupDao.selectOne(lqw);

            long t = getTimeStamp();

            userGroup.setUserLevelTime(t);
            int i = userGroupDao.updateById(userGroup);
            return i >= 1;
        }

    }

    @Override
    public boolean existNewMsg(String id, Boolean isF, Long leaveTime) {
        if (isF){
            LambdaQueryWrapper<FriendChat> lqw = new LambdaQueryWrapper<>();
            lqw.eq(FriendChat::getSenderId, id)
                    .eq(FriendChat::getReceiverId, UserThreadLocal.get().getId())
                    .ge(FriendChat::getDate, leaveTime);

            List<FriendChat> friendChat = friendChatDao.selectList(lqw);
            return friendChat != null && friendChat.size() != 0;
        }else {
            LambdaQueryWrapper<GroupChat> lqw = new LambdaQueryWrapper<>();
            lqw.eq(GroupChat::getGroupId, id)
                    .ge(GroupChat::getDate, leaveTime);

            List<GroupChat> groupChats = groupChatDao.selectList(lqw);
//            System.out.println(groupChats);
            int size = groupChats.size();
//            System.out.println(size);
            return size != 0;
        }
    }

    private long getTimeStamp() {
        String currentDate = getCurrentDate();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = sf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Long.parseLong(String.valueOf(date.getTime()));
    }

    private static String getCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sf.format(d);
    }




}
