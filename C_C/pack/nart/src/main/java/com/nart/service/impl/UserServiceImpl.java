package com.nart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nart.dao.FriendDao;
import com.nart.dao.UserDao;
import com.nart.dao.UserGroupDao;
import com.nart.pojo.Friend;
import com.nart.pojo.User;
import com.nart.pojo.UserGroup;
import com.nart.service.DataCounterService;
import com.nart.service.UserService;
import com.nart.util.EncryptUtil;
import com.nart.util.UserThreadLocal;
import com.nart.vo.PageVo;
import com.nart.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final DataCounterService dataCounterService;

    private final FriendDao friendDao;

    private final UserGroupDao userGroupDao;

    @Autowired
    public UserServiceImpl(UserDao userDao,
                           DataCounterService dataCounterService,
                           FriendDao friendDao,
                           UserGroupDao userGroupDao) {
        this.userDao = userDao;
        this.dataCounterService = dataCounterService;
        this.friendDao = friendDao;
        this.userGroupDao = userGroupDao;
    }


    @Override
    public User findUser(String uname, String pwd) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getName, uname).eq(User::getPwd, pwd);
        User user = userDao.selectOne(lqw);

        if (user != null) {
            user.setUserOnline(1);
            userDao.updateById(user);
            return user;
        }
        return null;
    }

    @Override
    public User findUserByName(String uname) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getName, uname);
        return userDao.selectOne(lqw);
    }

    @Override
    public boolean logout(String userId) {

        User user1 = userDao.selectById(userId);
        int userOnline = user1.getUserOnline();
        if (userOnline == 1) {
            User user = new User();
            user.setId(userId);
            user.setUserOnline(0);
            int i = userDao.updateById(user);
            dataCounterService.updateOnlineUserAmount(false);
            boolean a = false;
            if (i == 1){
                a =true;
            }
            return a;
        }
        return false;
    }

    @Override
    public User register(String email, String name, String pwd) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPwd(pwd);
        user.setUserOnline(0);
        user.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        int insert = userDao.insert(user);
        int i = dataCounterService.updateUserAmount(true);
        return user;

    }

    @Override
    public User showUserInfo(String userId) {
        return userDao.selectById(userId);
    }

    @Override
    public User showUnameAvatar(String userId) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.select(User::getName, User::getAvatar).eq(User::getId, userId);
        return userDao.selectOne(lqw);
    }

    @Override
    public boolean changeUserInfo(UserVo userVo, String id) {
        User user = userDao.selectById(id);
        if(!userVo.getUname().isEmpty()){
            user.setName(userVo.getUname());
        }
        String tpwd = user.getTpwd();
//        System.out.println(tpwd);
        String pwd = userVo.getPwd();
//        System.out.println(pwd);
        if(!userVo.getPwd().isEmpty() && userVo.getPwd().equals(tpwd)) {
            user.setTpwd(userVo.getPwd());
            String password = EncryptUtil.encryptPwd(userVo.getPwd());
            user.setPwd(password);
        }
        if(!userVo.getAvatar().isEmpty()) {
            user.setAvatar(userVo.getAvatar());
        }
        if(userVo.getPhone() != null && !userVo.getPhone().isEmpty()) {
            user.setTel(userVo.getPhone());
        }
        if(userVo.getEmail() != null && !userVo.getEmail().isEmpty()) {
            user.setEmail(userVo.getEmail());
        }
        if(userVo.getAddress() != null && !userVo.getAddress().isEmpty()) {
            user.setAddress(userVo.getAddress());
        }
        if(userVo.getBirthday() != null && !userVo.getBirthday().isEmpty()) {
            user.setAge(userVo.getBirthday());
        }
        int i = userDao.updateById(user);
        boolean a = false;
        if (i == 1){
            a = true;
        }
        return a;
    }

    @Override
    public IPage<User> searchNew(String name, PageVo pageVo) {

        LambdaQueryWrapper<Friend> lqwFriend = new LambdaQueryWrapper<>();
        lqwFriend.eq(Friend::getUid, UserThreadLocal.get().getId());
        List<Friend> myFriends = friendDao.selectList(lqwFriend);
        List<String> myFriendIds = new ArrayList<>();
        myFriends.forEach(f -> myFriendIds.add(f.getFid()));

        IPage<User> page = new Page<>(pageVo.getPageNum(), pageVo.getPageSize());
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.like(User::getName, name);
        if(myFriendIds.size() != 0) {
            lqw.notIn(User::getId, myFriendIds);
        }

        return userDao.selectPage(page, lqw);
    }

    @Override
    public boolean upDatetime(String userId) {
//        String id = UserThreadLocal.get().getId();
//        System.out.println(id);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long time = timestamp.getTime();
        System.out.println(userId);
        System.out.println("现在的时间是  "+time);

//        String userId = "1574989632599367682";
        LambdaQueryWrapper<Friend> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Friend::getUid, userId);
        List<Friend> friends = friendDao.selectList(lqw);

        if (friends.size()>0){
            for (Friend friend : friends) {
                friend.setLeaveTime(time);
                int i = friendDao.updateById(friend);
            }
        }

        LambdaQueryWrapper<UserGroup> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(UserGroup::getUid, userId);
        List<UserGroup> userGroups = userGroupDao.selectList(lqw1);

        if (userGroups.size()>0){
            for (UserGroup userGroup : userGroups) {
                userGroup.setUserLevelTime(time);
                userGroupDao.updateById(userGroup);
            }
        }
        return true;
    }
}
