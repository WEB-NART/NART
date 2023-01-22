package com.nart.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.javafaker.Faker;
import com.nart.dao.*;
import com.nart.pojo.*;
import com.nart.service.impl.LoadDataInDataBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.nart.util.EncryptUtil.encryptPwd;
import static com.nart.util.RandomContentGenerator.getRandomId;
import static com.nart.util.RandomContentGenerator.getRandomPics;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: FakeDataGenerator
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/8/30 15:43
 */

@Component
public class FakeDataGenerator {
    private final Faker faker = new Faker(Locale.CANADA);
    private final Random r = new Random();

    @Autowired
    private UserDao userDao;
    @Autowired
    private StatusDao statusDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private UserGroupDao userGroupDao;
    @Autowired
    private FriendDao friendDao;

    @Autowired
    private GroupChatDao groupChatDao;

    @Autowired
    private FriendChatDao friendChatDao;

    @Autowired
    private LoadDataInDataBaseImpl loadDataInDataBase;

    @Autowired
    private UserLikeDao userLikeDao;

    /**
     *  generate users
     * @param num number of users
     * @return List of User
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:11
     */
    public List<User> generateUsers(int num) {
        List<User> list = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < num; i++) {
            User u = new User();
            String password = faker.internet().password(6, 18, true);
            u.setTpwd(password);
            u.setPwd(encryptPwd(password));
            u.setAddress(faker.address().fullAddress());
            u.setAge(df.format(faker.date().birthday()));
            u.setAvatar(faker.avatar().image());
            u.setEmail(faker.internet().emailAddress());
            u.setName(faker.name().username());
            u.setTel(faker.phoneNumber().cellPhone());
            list.add(u);
        }
        return list;
    }

    /**
     *  generate friends requests
     * @param num number of requests
     * @return List of FriendReq
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:12
     */
    public List<FriendReq> generateReqs(int num) {
        List<FriendReq> list = new ArrayList<>();
        List<String> users = getUserIds();

        for (int i = 0; i < num; i++) {
            String sender = getRandomId(users);
            String receiver = getRandomId(users, sender);
            FriendReq frq = new FriendReq();
            frq.setSenderId(sender);
            frq.setReceiverId(receiver);
            frq.setMsg(faker.regexify("\\w{0,20}"));
            frq.setDate(faker.date().past(94608000, TimeUnit.SECONDS).getTime());
            list.add(frq);
        }
        return list;
    }

    /**
     *  generate Status with random User as author
     * @param num number of Status
     * @return List of Status
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:12
     */
    public List<Status> generateStatus(int num) {
        List<Status> list = new ArrayList<>();

        List<String> users = getUserIds();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < num; i++) {
            Status s = new Status();
            s.setSenderId(getRandomId(users));
            s.setLikes(0);
            s.setText(faker.shakespeare().asYouLikeItQuote());
            s.setPics(getRandomPics());
            s.setCreateDate(faker.date().past(94608000, TimeUnit.SECONDS).getTime());
            list.add(s);
        }
        return list;
    }

    /**
     *  generate comments with random statuses and users
     * @param num max number of comments
     * @param statusId Status Id
     * @return List of Comment
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:13
     */
    public List<Comment> generateComment(String statusId, int num) {
        List<Comment> list = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            if(r.nextBoolean()) {
                String authorId = "";
                /**
                 * select sid as authorId from tb_status where id = statusId
                 */
                Status status = statusDao.selectById(statusId);
                authorId = status.getSenderId();

                List<String> userFriendIds = getUserFriendIds(authorId);
                String uid = getRandomId(userFriendIds);
                Comment comment = new Comment();
                comment.setUserId(uid);
                comment.setStatusId(statusId);
                comment.setMsg(faker.shakespeare().romeoAndJulietQuote());
                comment.setCreateDate(faker.date().past(94608000, TimeUnit.SECONDS).getTime());
                list.add(comment);
            }
        }


        return list;
    }

    /**
     *  generate like relationship between Status and random User
     * @param statusId Status id
     * @param num max number of likes for each Status
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:19
     */
    public void generateLikes(String statusId, int num) {
        String authorId = "";
        /**
         * select sid as authorId from tb_status where id = statusId
         */
//        LambdaQueryWrapper<Status> lqw = new LambdaQueryWrapper<Status>();
//        lqw.eq(Status::getId, statusId);
        Status status = statusDao.selectById(statusId);
        System.out.println(status);

        authorId = status.getSenderId();

        System.out.println("id"+ authorId);

        List<String> userFriendIds = getUserFriendIds(authorId);

        System.out.println(userFriendIds);
        List<String> likedUserId = new ArrayList<>();
        for(int i=0; i<num; i++) {
            String uid = getRandomId(userFriendIds, likedUserId);
            if ((uid==null)){
                continue;
            }
            if (uid.isEmpty()) return;
            if(r.nextBoolean()) {
                /**
                 *  insert into tb_users_likes values (uid = uid, status_id = statusId);
                 */
                UserLike userLike = new UserLike();
                userLike.setUid(uid);
                userLike.setStatusId(statusId);
                userLikeDao.insert(userLike);
                likedUserId.add(uid);
            }
        }
    }

    /**
     *  generate empty groups
     * @param num Group number
     * @return List of Group
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:20
     */
    public List<Group> generateGroup(int num) {
        List<Group> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Group g = new Group();
            g.setAvatar(faker.avatar().image());
            g.setNotice(faker.regexify("\\w{20,200}"));
            g.setGroupName(faker.company().name());
            g.setUserLevel(0);
            list.add(g);
        }
        return list;
    }

    /**
     *  generate Group invitations with random User and Group
     * @param num total number of invitations
     * @return List of GroupInvite
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:21
     */
    public List<GroupInvite> generateInvites(int num) {
        List<GroupInvite> list = new ArrayList<>();
        List<String> users = getUserIds();
        List<String> groupIds = getGroupIds();

        for (int i = 0; i < num; i++) {
            String sid = getRandomId(users);
            String gid = getRandomId(groupIds);
            /**
             * TODO
             * select id from tb_user_group where uid = sid and gid = gid;
             * if not exists
             * update tb_group set last_level=last_level+1 where id = gid;
             * tempLevel = select last_level from tb_group where id = gid;
             * insert into tb_user_group values (sid, gid, 0, tempLevel, 0L);
             */
            GroupInvite gi = new GroupInvite();
            gi.setSenderId(sid);
            gi.setGroupId(gid);
            gi.setReceiverId(getRandomId(users, sid));
            gi.setMsg(faker.regexify("\\w{0,20}"));
            gi.setDate(faker.date().past(94608000, TimeUnit.SECONDS).getTime());
            list.add(gi);
        }
        return list;
    }

    /**
     *  generate Group relationships between Group and random User
     * @param gid Group id
     * @param num max number of Group member
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:22
     */
    public void generateGroupRelationships(String gid, int num) {
        List<String> groupMemberIds = getGroupMemberIds(gid);
        List<String> userIds = getUserIds();
        for (int i = 0; i < num; i++) {
            if(r.nextBoolean()) {
                String sid = getRandomId(userIds, groupMemberIds);
                /**
                 * update tb_group set last_level=last_level+1 where id = gid;
                 * tempLevel = select last_level from tb_group where id = gid;
                 * insert into tb_user_group values (sid, gid, 0, tempLevel, 0L);
                 */
                Group group = new Group();
                group.setId(gid);
                group.setUserLevel(group.getUserLevel()+1);
                groupDao.updateById(group);

                Group group1 = groupDao.selectById(gid);
                int tempLevel = group1.getUserLevel();

                UserGroup userGroup = new UserGroup();
                userGroup.setUid(sid);
                userGroup.setGid(gid);
                userGroup.setState(0);
                userGroup.setJoinLevel(tempLevel);
                userGroup.setUserLevelTime(0L);
                userGroupDao.insert(userGroup);

                groupMemberIds.add(sid);
            }
        }
    }

    /**
     *  generate Friend relationships between User and a random User
     * @param uid User id
     * @param num max number of Friend
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:23
     */
    public void generateFriendRelationships(String uid, int num) {
        List<String> userFriendIds = getUserFriendIds(uid);
//        System.out.println("pengy"+userFriendIds);
        List<String> userIds = getUserIds();
        for (int i = 0; i < num; i++) {
            if(r.nextBoolean()) {
                String fid = getRandomId(userIds, uid, userFriendIds);
                /**
                 * insert into tb_friends values (uid, gid, 0, 0L);
                 */
                Friend friend = new Friend();
                friend.setUid(uid);
                friend.setFid(fid);
                friend.setState(0);
                friend.setLeaveTime(0L);
                friendDao.insert(friend);
                userFriendIds.add(fid);
            }
        }
    }

    /**
     *  generate Friend chat Message
     * @param uid User id
     * @param num max number of chat msg the User may send to each Friend
     * @Author Zirui Qiao
     * @Date 2022/8/30 19:50
     */
    public void generateFriendsChats(String uid, int num) {
        List<String> userFriendIds = getUserFriendIds(uid);
//        System.out.println("朋友"+userFriendIds);
        for(String fid: userFriendIds) {
            for(int i=0; i<num; i++) {
                if(r.nextBoolean()) {
//                    Message msg = new Message();
                    FriendChat friendChat = new FriendChat();

//                    msg.setSenderId(uid);

                    friendChat.setSenderId(uid);

//                    msg.setReceiverId(fid);

                    friendChat.setReceiverId(fid);

                    if(r.nextBoolean()) {
//                        msg.setType("text");
                        friendChat.setType("text");
//                        msg.setMsg(faker.regexify("\\w{5,200}"));

                        friendChat.setMsg(faker.harryPotter().quote());
                    } else {
//                        msg.setType("picture");
                        friendChat.setType("picture");

//                        msg.setMsg(faker.internet().image());
                        friendChat.setMsg(faker.internet().image());
                    }
//                    msg.setDate(faker.date().past(94608000, TimeUnit.SECONDS).getTime());
                    friendChat.setDate(faker.date().past(94608000, TimeUnit.SECONDS).getTime());
                    int insert = friendChatDao.insert(friendChat);
                    if (insert<0){
                        break;
                    }
                }
            }
        }
    }

    /**
     *  generate Group chat Message
     * @param uid User id
     * @param num max number of chat msg the User may send to each Group
     * @Author Zirui Qiao
     * @Date 2022/8/30 19:50
     */
    public void generateGroupsChats(String uid, int num) {
        List<String> userGroupIds = getUserGroupIds(uid);

        for(String gid: userGroupIds) {
            for(int i=0; i<num+5; i++) {
                if(r.nextBoolean()) {
//                    Message msg = new Message();
                    GroupChat groupChat = new GroupChat();
                    groupChat.setGroupId(gid);
                    System.out.println(uid);
                    groupChat.setSenderId(uid);
//                    msg.setSenderId(uid);
//                    msg.setReceiverId(gid);
                    if(r.nextBoolean()) {
                        groupChat.setType("text");
                        groupChat.setMsg(faker.shakespeare().hamletQuote());
//                        msg.setType("text");
//                        msg.setMsg(faker.regexify("[\\w]{5,200}"));

                    } else {
                        groupChat.setType("picture");
                        groupChat.setMsg("picture");
//                        msg.setType("picture");
//                        msg.setMsg(faker.internet().image());
                    }
                    groupChat.setDate(faker.date().past(94608000, TimeUnit.SECONDS).getTime());
//                    msg.setDate(faker.date().past(94608000, TimeUnit.SECONDS).getTime());


                    Group group = groupDao.selectById(gid);

                    int maxLevel = group.getUserLevel();
                    /**
                     * maxLevel = select last_level from tb_group where id = gid
                     */
                    groupChat.setLevel(r.nextInt(maxLevel));
                    int insert = groupChatDao.insert(groupChat);
                    if (insert<0){
                        break;
                    }
                }
            }
        }
    }

    /**
     *  find all users' id
     * @return List of String
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:27
     */
    public List<String> getUserIds() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(User::getId);
        List<User> objList = this.userDao.selectList(queryWrapper);
        List<String> result = new ArrayList<>();
        objList.forEach((obj -> {
            result.add(obj.getId());
        }));
        return result;
    }

    /**
     *  find all statuses' ids
     * @return List of String
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:28
     */
    public List<String> getStatusIds() {
        LambdaQueryWrapper<Status> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Status::getId);
        List<Status> objList = statusDao.selectList(null);
        List<String> result = new ArrayList<>();
        objList.forEach((obj -> {
            result.add(obj.getId());
        }));
        return result;
    }

    /**
     *  find all groups' ids
     * @return List of String
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:28
     */
    public List<String> getGroupIds() {
        LambdaQueryWrapper<Group> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Group::getId);
        List<Group> objList = this.groupDao.selectList(queryWrapper);
        List<String> result = new ArrayList<>();
        objList.forEach((obj -> {
            result.add(obj.getId());
        }));
        return result;
    }

    /**
     *  find all members' ids in the Group
     * @param groupId Group id
     * @return List of String
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:28
     */

    public List<String> getGroupMemberIds(String groupId) {
//        List<Group> objList= this.GroupDao.selectAllMember(groupId);
        LambdaQueryWrapper<UserGroup> lqw = new LambdaQueryWrapper<UserGroup>();
        lqw.eq(UserGroup::getGid, groupId);
        List<UserGroup> userGroups = userGroupDao.selectList(lqw);
        List<String> result = new ArrayList<>();
        for (UserGroup userGroup : userGroups) {
            result.add(userGroup.getUid());
        }
//        objList.forEach((obj -> {
//            result.add(obj.getId());
//        }));
        return result;
    }

    /**
     *  find all friends' ids of the User
     * @param uid User id
     * @return List of String
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:29
     */
    public List<String> getUserFriendIds(String uid) {
//        List<User> objList= this.UserDao.selectFriends(uid);
        LambdaQueryWrapper<Friend> lqw = new LambdaQueryWrapper<Friend>();
        lqw.eq(Friend::getUid, uid);
        List<Friend> friends = friendDao.selectList(lqw);
        List<String> result = new ArrayList<>();
        for (Friend friend : friends) {
            result.add(friend.getFid());
        }
//        objList.forEach((obj -> {
//            result.add(obj.getId());
//        }));
        return result;
    }

    /**
     *  find all groups' id the User joined
     * @param uid User id
     * @return List of String
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:29
     */
    public List<String> getUserGroupIds(String uid) {
//        List<Group> objList= this.GroupDao.selectGroups(uid);

        LambdaQueryWrapper<UserGroup> lqw = new LambdaQueryWrapper<UserGroup>();
        lqw.eq(UserGroup::getUid, uid);
        List<UserGroup> userGroups = userGroupDao.selectList(lqw);

        List<String> result = new ArrayList<>();
        for (UserGroup userGroup : userGroups) {
            result.add(userGroup.getGid());
        }

//        objList.forEach((obj -> {
//            result.add(obj.getId());
//        }));
        return result;
    }

    /**
     *  use this to generate fake data and load to database
     * @param num a base number (1/10 of User number)
     * @Author Zirui Qiao
     * @Date 2022/8/30 20:30
     */

    //
    public void generateTestData(int num) {
//        List<User> Users = generateUsers(num * 10);
//        System.out.println(Users);
//        // write to database
//        boolean b = loadDataInDataBase.LoadListUser(Users);
//        System.out.println("user加载"+b);
//
//        List<FriendReq> FriendReqs = generateReqs(num * 20);
//        boolean b1 = loadDataInDataBase.LoadListFriendReq(FriendReqs);
//        System.out.println("FriendReqs加载"+b1);
//        // write to database
//
//        for (String uid : getUserIds()) {
//            generateFriendRelationships(uid, num*3);
//        }
//        List<Status> Statuses = generateStatus(num * 20);
//        System.out.println(Statuses);
//        // write to database
//        boolean b2 = loadDataInDataBase.LoadListStatus(Statuses);
//        System.out.println("Status加载"+b2);

//        for (String statusId : getStatusIds()) {
//
//            generateLikes(statusId, num*2);
//            System.out.println("statusId"+statusId);
//            List<Comment> Comments = generateComment(statusId, num * 2);
////            write to database
//            boolean bt = loadDataInDataBase.LoadListComment(Comments);
//            System.out.println("Comments加载"+bt);
        //在这里会出现死机
//        }
        //write to database
//        List<Group> Groups = generateGroup(num+2);
//        // write to database
//        boolean b3 = loadDataInDataBase.LoadListGroup(Groups);
//        System.out.println("Groups加载"+b3);
//
//        List<GroupInvite> GroupInvites = generateInvites(num * 10);
//        boolean b4 = loadDataInDataBase.LoadListGroupInvite(GroupInvites);
//        System.out.println("GroupInvites加载"+b4);
//        // write to database
//        for (String gid : getGroupIds()) {
//            generateGroupRelationships(gid, num*3);
//        }
        for(String uid : getUserIds()) {
            generateFriendsChats(uid, num);
//            generateGroupsChats(uid, num);
        }
    }
}
