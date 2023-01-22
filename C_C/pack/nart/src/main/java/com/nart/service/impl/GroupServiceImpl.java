package com.nart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nart.dao.*;
import com.nart.pojo.Group;
import com.nart.pojo.GroupInvite;
import com.nart.pojo.User;
import com.nart.pojo.*;
import com.nart.service.ChatService;
import com.nart.service.GroupService;
import com.nart.util.UserThreadLocal;
import com.nart.vo.GroupVo;
import com.nart.vo.InviteVo;
import com.nart.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Transactional
@Service
public class GroupServiceImpl implements GroupService {

    private final UserGroupDao userGroupDao;
    private final UserDao userDao;
    private final GroupDao groupDao;
    private final GroupInviteDao groupInviteDao;
    private final ChatService chatService;


    @Autowired
    public GroupServiceImpl(UserGroupDao userGroupDao,
                            UserDao userDao,
                            GroupDao groupDao,
                            GroupInviteDao groupInviteDao,
                            ChatService chatService) {
        this.userGroupDao = userGroupDao;
        this.userDao = userDao;
        this.groupDao = groupDao;
        this.groupInviteDao = groupInviteDao;
        this.chatService = chatService;
    }


    @Override
    public List<UserVo> showGroupMebList(String gid) {
        LambdaQueryWrapper<UserGroup> lqw = new LambdaQueryWrapper<UserGroup>();
        lqw.eq(UserGroup::getGid,gid);

//        IPage iPage = userGroupDao.selectPage(null, lqw);
//        List<UserGroup> records = iPage.getRecords();
        List<UserGroup> userGroups = userGroupDao.selectList(lqw);
        List<User> Users = new ArrayList<>();
        for (UserGroup userGroup : userGroups) {
            String uid = userGroup.getUid();
            User user = userDao.selectById(uid);
            String name = user.getName();
            String avatar = user.getAvatar();
            String id = user.getId();

            User user1 = new User();
            user1.setId(id);
            user1.setAvatar(avatar);
            user1.setName(name);
            Users.add(user1);
        }

//        for (UserGroup record : records) {
//            String uid = record.getUid();
//            User user = userDao.selectById(uid);
//            Users.add(user);
//        }

        UserVo userVo = new UserVo();
        List<UserVo> userVos = new ArrayList<>();
        for (User user : Users) {
            UserVo transfer = userVo.transfer(user);
            userVos.add(transfer);
        }

        return userVos;
    }

    @Override
    public boolean changeGroupInfo(Group group) {
        int i = groupDao.updateById(group);
        boolean a = false;
        if ( i == 1){
            a = true;
        }
        return true;
    }

    @Override
    public List<GroupVo> showGroupList(IPage page) {
        String id = UserThreadLocal.get().getId();
//        String id ="1574989639117316098";
        LambdaQueryWrapper<UserGroup> lqw = new LambdaQueryWrapper<UserGroup>();
        lqw.orderBy(true,false, UserGroup::getUserLevelTime);
        lqw.eq(UserGroup::getUid,id);
        IPage iPage = userGroupDao.selectPage(page, lqw);

        List<UserGroup> records = iPage.getRecords();

        List<Group> groupList = new ArrayList<>();
        for (UserGroup record : records) {
            String gid = record.getGid();
            Group group = groupDao.selectById(gid);
            boolean hasNewMsg = chatService.existNewMsg(gid, false, record.getUserLevelTime());
            group.setNewMessage(hasNewMsg);
            groupList.add(group);
        }

        GroupVo groupVo = new GroupVo();
        List<GroupVo> groupVos = new ArrayList<>();
        for (Group group : groupList) {
            GroupVo transfer = groupVo.transfer(group);
            groupVos.add(transfer);
        }

        return groupVos;
    }

    @Override
    public boolean leaveGroup(String gid, String uid) {
        LambdaQueryWrapper<UserGroup> lqw = new LambdaQueryWrapper<UserGroup>();
        lqw.eq(UserGroup::getGid,gid);
        lqw.eq(UserGroup::getUid,uid);
        int delete = userGroupDao.delete(lqw);
        boolean a = false;
        if (delete == 1){
            a = true;
        }
        return a;
    }

    @Override
    public boolean changeGroupState(String gid, String uid, int state) {

        LambdaQueryWrapper<UserGroup> lqw = new LambdaQueryWrapper<UserGroup>();
        lqw.eq(UserGroup::getGid,gid);
        lqw.eq(UserGroup::getUid,uid);

        UserGroup userGroup = new UserGroup();
        userGroup.setState(state);
        int update = userGroupDao.update(userGroup, lqw);
        boolean a = false;
        if (update == 1){
            a = true;
        }
        return a;
    }

    @Override
    public List<InviteVo> showInviteList(IPage page) {
        String id = UserThreadLocal.get().getId();
        LambdaQueryWrapper<GroupInvite> lqw = new LambdaQueryWrapper<GroupInvite>();
        lqw.eq(GroupInvite::getReceiverId,id);
        lqw.orderBy(true,false, GroupInvite::getDate);

        IPage iPage = groupInviteDao.selectPage(page, lqw);
        List<GroupInvite> records = iPage.getRecords();

        InviteVo inviteVo = new InviteVo();
        List<InviteVo> inviteVos =  new ArrayList<>();


        for (GroupInvite record : records) {
            InviteVo transfer = inviteVo.transfer(record);

            Group group = groupDao.selectById(record.getGroupId());
            transfer.setGroupName(group.getGroupName());
            transfer.setReceiverId(record.getReceiverId());
            transfer.setGroupAvatar(group.getAvatar());


            User user = userDao.selectById(record.getSenderId());
            transfer.setSenderName(user.getName());


            inviteVos.add(transfer);
        }


        return inviteVos;
    }

    // groupInvite contain gid rid msg,need sid
    @Override
    public boolean sendInvite(GroupInvite groupInvite) {
        groupInvite.setSenderId(UserThreadLocal.get().getId());
        groupInvite.setDate(new Date().getTime());
        int insert = groupInviteDao.insert(groupInvite);
        boolean a = false;
        if (insert == 1){
            a = true;
        }
        return a;
    }

    @Override
    public boolean respGroupInvite(String InviteId, Boolean agree) {

        GroupInvite groupInvite = groupInviteDao.selectById(InviteId);
        if(agree){

            String rid = groupInvite.getReceiverId();
            String groupId = groupInvite.getGroupId();

            UserGroup userGroup = new UserGroup();
            userGroup.setGid(groupId);
            userGroup.setUid(rid);
            Group group = groupDao.selectById(groupId);
            userGroup.setJoinLevel(group.getUserLevel()+1);
            int insert = userGroupDao.insert(userGroup);
            if (insert <= 0) return false;
            int i = groupInviteDao.deleteById(InviteId);
            return i>0;
        }else {
            int i = groupInviteDao.deleteById(InviteId);
            return i>0;
        }

    }

    @Override
    public boolean createGroup(String groupName,String uid) {
        LambdaQueryWrapper<Group> lqw = new LambdaQueryWrapper<Group>();
        lqw.eq(Group::getGroupName,groupName);
        String notic = String.valueOf(Math.random() * 100);
//        System.out.println(notic);
        Group group = new Group();

        if (groupName.equals("-1")){
            group.setGroupName(null);
            group.setNotice(notic);
        }else{
            group.setGroupName(groupName);
        }


        int insert = groupDao.insert(group);

        if (groupName.equals("-1")){
            LambdaQueryWrapper<Group> lqw1 = new LambdaQueryWrapper<Group>();
            lqw1.eq(Group::getNotice,notic);

            Group group2 = groupDao.selectOne(lqw1);
//            System.out.println(group2);
            String id = group2.getId();
            UserGroup userGroup = new UserGroup();
            userGroup.setGid(id);
            userGroup.setUid(uid);
            userGroup.setJoinLevel(1);
            int insert1 = userGroupDao.insert(userGroup);
//            System.out.println(insert1);

        }else{
            Group group1 = groupDao.selectOne(lqw);
            String id = group1.getId();
            UserGroup userGroup = new UserGroup();
            userGroup.setGid(id);
            userGroup.setUid(uid);
            userGroup.setJoinLevel(1);
            int insert1 = userGroupDao.insert(userGroup);
        }

        boolean q = false;
        if (insert == 1){
            q = true;
        }
        return q;
    }

    @Override
    public boolean joinGroup(String groupId) {
        Group group = groupDao.selectById(groupId);
        int userLevel = group.getUserLevel();

        String id = UserThreadLocal.get().getId();
        UserGroup userGroup = new UserGroup();
        userGroup.setGid(groupId);
        userGroup.setUid(id);
        userGroup.setJoinLevel(userLevel+1);

        int insert = userGroupDao.insert(userGroup);

        group.setUserLevel(userLevel+1);
        groupDao.updateById(group);

        boolean a =false;
        if (insert == 1){
            a = true;
        }

        return a;
    }

    @Override
    public Set<String> findAllMembers(String gid) {
        LambdaQueryWrapper<UserGroup> lqw = new LambdaQueryWrapper<UserGroup>();
        lqw.eq(UserGroup::getGid,gid);

        List<UserGroup> userGroups = userGroupDao.selectList(lqw);
        Set<String> ids = new HashSet<>();
        for (UserGroup record : userGroups) {
            String uid = record.getUid();
            ids.add(uid);
        }

        return ids;
    }

    @Override
    public Group findGroup(String groupName) {
        LambdaQueryWrapper<Group> lqw = new LambdaQueryWrapper<Group>();
        lqw.eq(Group::getGroupName,groupName);
        Group group = groupDao.selectOne(lqw);
        return group;
    }


}
