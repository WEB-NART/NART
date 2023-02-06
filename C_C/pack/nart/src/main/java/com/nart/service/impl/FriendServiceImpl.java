package com.nart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nart.dao.FriendDao;
import com.nart.dao.FriendReqDao;
import com.nart.dao.UserDao;
import com.nart.pojo.Friend;
import com.nart.pojo.FriendReq;
import com.nart.pojo.User;
import com.nart.service.ChatService;
import com.nart.service.FriendService;
import com.nart.service.UserService;
import com.nart.util.UserThreadLocal;
import com.nart.vo.FriendVo;
import com.nart.vo.PageVo;
import com.nart.vo.RequestVo;
import com.nart.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class FriendServiceImpl implements FriendService {

    private final FriendDao friendDao;
    private final UserDao userDao;
    private final UserService userService;
    private final FriendReqDao friendReqDAO;
    private final ChatService chatService;

    @Autowired
    public FriendServiceImpl(FriendDao friendDao,
                             UserDao userDao,
                             UserService userService,
                             FriendReqDao friendReqDAO,
                             ChatService chatService) {
        this.friendDao = friendDao;
        this.userDao = userDao;
        this.userService = userService;
        this.friendReqDAO = friendReqDAO;
        this.chatService = chatService;
    }


    @Override
    public List<FriendVo> showFriendList(IPage page, String userId) {

        LambdaQueryWrapper<Friend> lqw = new LambdaQueryWrapper<Friend>();
        lqw.eq(Friend::getUid, userId);
        lqw.orderBy(true,false, Friend::getLeaveTime);

        IPage iPage = friendDao.selectPage(page, lqw);
        List<Friend> records = iPage.getRecords();
        for (Friend record : records) {
            String fid = record.getFid();
            User user = userDao.selectById(fid);
            record.setName(user.getName());
            record.setEmail(user.getEmail());
            record.setAvatar(user.getAvatar());
            int userOnline = user.getUserOnline();
            //System.out.println(userOnline);
            if(userOnline==1){
                record.setOnline(true);
            }else {
                record.setOnline(false);
            }
            boolean hasNewMsg = chatService.existNewMsg(fid, true, record.getLeaveTime());
            record.setNewMessage(hasNewMsg);
//            record.setStatusList(statusService.showStatusList(userId ,page));
//            record.setChatHistory(chatService.showFriendHistory(user.getId(),page));

        }
        FriendVo friendVo = new FriendVo();
        List<FriendVo> friendVos = new ArrayList<>();
        for (Friend record : records) {
            FriendVo transfer = friendVo.transfer(record);

//            User user = userDao.selectById(record.getUid());
//            transfer.setUname(user.getName());

            friendVos.add(transfer);
        }

        return friendVos;
    }

    @Override
    public List<UserVo> searchFriend(String name, IPage page) {
//        PageVo pageVo = new PageVo();
//        pageVo.setPageNum((int) page.getCurrent());
//        pageVo.setPageSize((int) page.getSize());

//        IPage<User> userIPage = userService.searchNew(name, pageVo);
//        List<User> records = userIPage.getRecords();

        String id = UserThreadLocal.get().getId();
//        String id = "1574989638660136961";
//        System.out.println(id);
        LambdaQueryWrapper<Friend> lqw = new LambdaQueryWrapper<Friend>();
        lqw.eq(Friend::getUid, id);
        List<Friend> friends = friendDao.selectList(lqw);
        List<User> friendName = new ArrayList<>();

//        System.out.println(friends);
//        for (Friend friend : friends) {
//
//        }
        for (Friend friend : friends) {
            String fid = friend.getFid();
            User user = userDao.selectById(fid);
            String name1 = user.getName();
            if (name1.toLowerCase().contains(name.toLowerCase())){
                friendName.add(user);
            }
        }
        UserVo userVo = new UserVo();
        List<UserVo> userVos = new ArrayList<>();
        for (User record : friendName) {
            UserVo transfer = userVo.transfer(record);
            userVos.add(transfer);
        }

        int pageNumber = Math.toIntExact(page.getCurrent());
        int size = Math.toIntExact(page.getSize());
        int start = (pageNumber -1) * size;
        int end  = size + start;
        //System.out.println(userVos);
        List<UserVo> userVos1;
        if(start > userVos.size()) {
            userVos1 = new ArrayList<>();
            return userVos1;
        }
        if(end > userVos.size()) {
            end = userVos.size();
        }
        userVos1 = userVos.subList(start,end);

        return userVos1;
    }

    @Override
    public boolean delFriend(String fid,String uid) {
        LambdaQueryWrapper<Friend> lqw = new LambdaQueryWrapper<Friend>();
        lqw.eq(Friend::getFid, fid);
        lqw.eq(Friend::getUid, uid).or();
        lqw.eq(Friend::getFid, uid);
        lqw.eq(Friend::getUid, fid);
        int delete = friendDao.delete(lqw);
        boolean a = false;
        if (delete == 1){
            a = true;
        }
        return a;
    }

    @Override
    public boolean changeFriendState(String fid, String uid,int state) {
        LambdaQueryWrapper<Friend> lqw = new LambdaQueryWrapper<Friend>();
        lqw.eq(Friend::getFid, fid);
        lqw.eq(Friend::getUid, uid);
        Friend friend = new Friend();
        friend.setState(state);
        int update = friendDao.update(friend, lqw);
        boolean a = false;
        if (update == 1){
            a = true;
        }
        return a;
    }

    @Override
    public List<RequestVo> showReqList(IPage page, String sid) {
//        System.out.println(sid);
        LambdaQueryWrapper<FriendReq> lqw = new LambdaQueryWrapper<FriendReq>();
        lqw.eq(FriendReq::getReceiverId, sid);

        lqw.orderBy(true,false, FriendReq::getDate);

        IPage iPage = friendReqDAO.selectPage(page, lqw);
        List<FriendReq> records = iPage.getRecords();

        RequestVo requestVo = new RequestVo();
        List<RequestVo> requestVos = new ArrayList<>();
        for (FriendReq record : records) {
            RequestVo transfer = requestVo.transfer(record);

            User user = userDao.selectById(record.getSenderId());
            transfer.setFriendName(user.getName());
            transfer.setFriendAvatar(user.getAvatar());

            User user1 = userDao.selectById(record.getSenderId());
            transfer.setSenderName(user1.getName());


            requestVos.add(transfer);
        }
        
        return requestVos;
    }

    @Override
    public boolean sendFriendReq(String rid, String sid, String msg) {

        FriendReq friendReq = new FriendReq();
        friendReq.setMsg(msg);
        friendReq.setReceiverId(rid);
        friendReq.setSenderId(sid);
        friendReq.setDate(new Date().getTime());
        int insert = friendReqDAO.insert(friendReq);
        boolean a = false;
        if (insert == 1){
            a = true;
        }
        return a;
    }

    @Override
    public boolean respFriendReq(String reqId, Boolean agree) {
        FriendReq friendReq = friendReqDAO.selectById(reqId);

        if(agree){
            String senderId = friendReq.getSenderId();
            String ReceiverId = friendReq.getReceiverId();

            Friend friend = new Friend();
            friend.setFid(senderId);
            friend.setUid(ReceiverId);
            friendDao.insert(friend);
            friend.setId(null);
            friend.setFid(ReceiverId);
            friend.setUid(senderId);
            int insert = friendDao.insert(friend);
            if (insert <= 0) return false;
            int i = friendReqDAO.deleteById(reqId);
            return i > 0;
        }else{
            int i = friendReqDAO.deleteById(reqId);
            return i > 0;
        }
    }

    @Override
    public List<UserVo> searchNew(String name, PageVo pageVo) {
        IPage<User> userIPage = userService.searchNew(name, pageVo);
        List<User> records = userIPage.getRecords();

        UserVo userVo = new UserVo();
        List<UserVo> userVos = new ArrayList<>();
        for (User record : records) {
            UserVo transfer = userVo.transfer(record);
            userVos.add(transfer);
        }


        return userVos;
    }

    @Override
    public List<Friend> findAllFriends(String uid) {
        String id = UserThreadLocal.get().getId();
        LambdaQueryWrapper<Friend> lqw = new LambdaQueryWrapper<Friend>();
        lqw.select(Friend::getFid).eq(Friend::getUid, uid);
        lqw.orderBy(true,false, Friend::getLeaveTime);
        return friendDao.selectList(lqw);
    }
}
