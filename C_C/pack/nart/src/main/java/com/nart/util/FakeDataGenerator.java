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
import static com.nart.util.EncryptUtil.getRandLengthSalt;
import static com.nart.util.RandomContentGenerator.getRandomId;
import static com.nart.util.RandomContentGenerator.getRandPics;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: FakeDataGenerator
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/01/16 15:43
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
    private LoadDataInDataBaseImpl loadDataInDataBase;

    /**
     *  generate users
     * @param num number of users
     * @return List of User
     * @Author Zirui Qiao
     * @Date 2022/01/16 20:11
     */
    private List<User> generateUsers(int num) {
        List<User> list = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < num; i++) {
            User u = new User();
            String password = faker.internet().password(6, 18, true);
            String salt = getRandLengthSalt();
            u.setTpwd(password);
            u.setPwd(encryptPwd(password, salt));
            u.setSalt(salt);
            u.setAddress(faker.address().fullAddress());
            u.setAge(df.format(faker.date().birthday()));
            u.setAvatar(getRandPics(AlbumType.USER_AVATAR));
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
     * @Date 2022/01/16 20:12
     */
    private List<FriendReq> generateReqs(int num) {
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
     *  generate Friend relationships between User and a random User
     * @param uid User id
     * @param num max number of Friend
     * @Author Zirui Qiao
     * @Date 2022/01/16 20:23
     */
    private List<Friend> generateFriendRelationships(List<String> userIds, String uid, int num) {
        List<String> userFriendIds = getUserFriendIds(uid);
//        System.out.println("pengy"+userFriendIds);
        List<Friend> friendList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            if(r.nextBoolean()) {
                String fid = getRandomId(userIds, uid, userFriendIds);
                Friend friend = new Friend();
                friend.setUid(uid);
                friend.setFid(fid);
                friend.setState(0);
                friend.setLeaveTime(0L);
                friendList.add(friend);
                userFriendIds.add(fid);
            }
        }
        return friendList;
    }

    /**
     *  generate Friend chat Message
     * @param uid User id
     * @param num max number of chat msg the User may send to each Friend
     * @Author Zirui Qiao
     * @Date 2022/01/16 19:50
     */
    private List<FriendChat> generateFriendsChats(String uid, int num) {
        List<String> userFriendIds = getUserFriendIds(uid);
        List<FriendChat> friendChatList = new ArrayList<>();
        for(String fid: userFriendIds) {
            for(int i=0; i<2; i++) {
                if(r.nextBoolean()) {
                    FriendChat friendChat = new FriendChat();
                    friendChat.setSenderId(uid);
                    friendChat.setReceiverId(fid);

                    if(r.nextBoolean()) {
                        friendChat.setType("text");
                        friendChat.setMsg(faker.harryPotter().quote());
                    } else {
                        friendChat.setType("img");
                        friendChat.setMsg(getRandPics(AlbumType.CHAT_PICS));
                    }
                    friendChat.setDate(faker.date().past(94608000, TimeUnit.SECONDS).getTime());
                    friendChatList.add(friendChat);
                }
            }
        }
        return friendChatList;
    }

    /**
     *  generate Status with random User as author
     * @param num number of Status
     * @return List of Status
     * @Author Zirui Qiao
     * @Date 2022/01/16 20:12
     */
    private List<Status> generateStatus(int num) {
        List<Status> list = new ArrayList<>();

        List<String> users = getUserIds();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < num; i++) {
            Status s = new Status();
            s.setSenderId(getRandomId(users));
            s.setLikes(0);
            s.setText(faker.shakespeare().asYouLikeItQuote());
            s.setPics(getRandPics(AlbumType.STATUS_PICS));
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
     * @Date 2022/01/16 20:13
     */
    private List<Comment> generateComment(String statusId, int num) {
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
     * @Date 2022/01/16 20:19
     */
    private List<UserLike> generateLikes(String statusId, int num) {
        String authorId = "";
        /**
         * select sid as authorId from tb_status where id = statusId
         */
        List<UserLike> likeList = new ArrayList<>();
        Status status = statusDao.selectById(statusId);
        authorId = status.getSenderId();
        List<String> userFriendIds = getUserFriendIds(authorId);
        List<String> likedUserId = new ArrayList<>();

        for(int i=0; i<num; i++) {
            String uid = getRandomId(userFriendIds, likedUserId);
            if ((uid==null)){
                continue;
            }
            if (uid.isEmpty()) return likeList;
            if(r.nextBoolean()) {
                /**
                 *  insert into tb_users_likes values (uid = uid, status_id = statusId);
                 */
                UserLike userLike = new UserLike();
                userLike.setUid(uid);
                userLike.setStatusId(statusId);
                likeList.add(userLike);
                likedUserId.add(uid);
            }
        }
        return likeList;
    }

    /**
     *  generate empty groups
     * @param num Group number
     * @return List of Group
     * @Author Zirui Qiao
     * @Date 2022/01/16 20:20
     */
    private List<Group> generateGroup(int num) {
        List<Group> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Group g = new Group();
            g.setAvatar(getRandPics(AlbumType.GROUP_AVATAR));
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
     * @Date 2022/01/16 20:21
     */
    private List<GroupInvite> generateInvites(int num) {
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
     * @Date 2022/01/16 20:22
     */
    private List<UserGroup> generateGroupRelationships(String gid, int num) {
        List<String> groupMemberIds = getGroupMemberIds(gid);
        List<String> userIds = getUserIds();
        List<UserGroup> userGroupList = new ArrayList<>();
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

                Group group1 = getGroupById(gid);
                int tempLevel = group1.getUserLevel();
                updateById(group);

                UserGroup userGroup = new UserGroup();
                userGroup.setUid(sid);
                userGroup.setGid(gid);
                userGroup.setState(0);
                userGroup.setJoinLevel(tempLevel);
                userGroup.setUserLevelTime(0L);
                userGroupList.add(userGroup);

                groupMemberIds.add(sid);
            }
        }
        return userGroupList;
    }

    /**
     *  generate Group chat Message
     * @param uid User id
     * @param num max number of chat msg the User may send to each Group
     * @Author Zirui Qiao
     * @Date 2022/01/16 19:50
     */
    private List<GroupChat> generateGroupsChats(String uid, int num) {
        List<String> userGroupIds = getUserGroupIds(uid);
        List<GroupChat> groupChatList = new ArrayList<>();

        for(String gid: userGroupIds) {
            for(int i=0; i<num+5; i++) {
                if(r.nextBoolean()) {
                    GroupChat groupChat = new GroupChat();
                    groupChat.setGroupId(gid);
                    groupChat.setSenderId(uid);
                    if(r.nextBoolean()) {
                        groupChat.setType("text");
                        groupChat.setMsg(faker.shakespeare().hamletQuote());

                    } else {
                        groupChat.setType("img");
                        groupChat.setMsg(getRandPics(AlbumType.CHAT_PICS));
                    }
                    groupChat.setDate(faker.date().past(94608000, TimeUnit.SECONDS).getTime());

                    Group group = getGroupById(gid);

                    int maxLevel = group.getUserLevel();
                    /**
                     * maxLevel = select last_level from tb_group where id = gid
                     */
                    groupChat.setLevel(r.nextInt(maxLevel));
                    groupChatList.add(groupChat);
                }
            }
        }
        return groupChatList;
    }

    /**
     *  find all users' id
     * @return List of String
     * @Author Zirui Qiao
     * @Date 2022/01/16 20:27
     */
    private List<String> getUserIds() {
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
     * @Date 2022/01/16 20:28
     */
    private List<String> getStatusIds() {
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
     * @Date 2022/01/16 20:28
     */
    private List<String> getGroupIds() {
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
     * @Date 2022/01/16 20:28
     */
    private List<String> getGroupMemberIds(String groupId) {
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
     * @Date 2022/01/16 20:29
     */
    private List<String> getUserFriendIds(String uid) {
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
     * @Date 2022/01/16 20:29
     */
    private List<String> getUserGroupIds(String uid) {
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

    private Group getGroupById(String gid) {
        return groupDao.selectById(gid);
    }

    private int updateById(Group group) {
        return groupDao.updateById(group);
    }

    /**
     *  use this to generate fake data and load to database
     * @param num a base number (1/10 of User number)
     * @Author Zirui Qiao
     * @Date 2022/01/16 20:30
     */

    //
    public void generateTestData(int num) {
        fakeFriendData(num);
        fakeStatusData(num);
        fakeGroupData(num);
    }

    public void fakeFriendData(int num) {
// generate users
        List<User> Users = generateUsers(num * 10);
        boolean b = loadDataInDataBase.LoadListUser(Users);
        System.out.println("user 加载"+ b + ": " + Users.size());
// generate friend requests
        List<FriendReq> FriendReqs = generateReqs(num * 20);
        boolean b1 = loadDataInDataBase.LoadListFriendReq(FriendReqs);
        System.out.println("FriendReqs 加载" + b1 + ": " + FriendReqs.size());
// generate friend relationships AND delete related friend requests
        List<String> userIds = getUserIds();
        int sum = 0;
        ProgressBar pb = new ProgressBar();
        pb.printProgress();

        int total = userIds.size();
        int count = 0;
        System.out.println("=========加载 FriendShip, 最多" + total * num * 2 + "条数据=========");
        for (String uid : userIds) {
            List<Friend> friendList = generateFriendRelationships(userIds, uid, num*2);
            boolean b2 = loadDataInDataBase.LoadListFriendShips(friendList);
            if (++count >= total/100) {
                pb.load();
                count = 0;
            }
            sum += friendList.size();
        }
        System.out.println("friendship 总共加载: " + sum);
// generate friend chats
        List<FriendChat> friendChatList = new ArrayList<>();
        for(String uid : getUserIds()) {
            List<FriendChat> fcTemp = generateFriendsChats(uid, num);
            friendChatList.addAll(fcTemp);
//            generateGroupsChats(uid, num);
        }
        boolean b2 = loadDataInDataBase.LoadListFriendChats(friendChatList);
        System.out.println("friendChat 加载"+ b2 + ": " + friendChatList.size());
    }

    public void fakeStatusData(int num) {
// generate Status lists
        List<Status> Statuses = generateStatus(num * 20);
        System.out.println(Statuses);
        boolean b2 = loadDataInDataBase.LoadListStatus(Statuses);
        System.out.println("Status加载"+ b2 + ": " + Statuses.size());

        int sum1 = 0;
        int sum2 = 0;
        ProgressBar pb = new ProgressBar();
        pb.printProgress();
        int total = Statuses.size();
        int count = 0;
// generate corresponding Likes and Comments
        System.out.println("==========加载Like和Comment, 最多" + total * num * 2 + "条数据=============");
        for (String statusId : getStatusIds()) {
            List<UserLike> likeList = generateLikes(statusId, num * 2);
            boolean b = loadDataInDataBase.LoadListLikes(likeList);
            List<Comment> Comments = generateComment(statusId, num * 2);
            boolean bt = loadDataInDataBase.LoadListComment(Comments);
            sum1 += likeList.size();
            sum2 += likeList.size();
            if (++count >= total/100) {
                pb.load();
                count = 0;
            }
        }
        System.out.println("Status Like 总共加载: " + sum1);
        System.out.println("Status Comment 总共加载: " + sum2);
    }

    public void fakeGroupData(int num) {
// generate group list
        List<Group> Groups = generateGroup(num+2);
        boolean b3 = loadDataInDataBase.LoadListGroup(Groups);
        System.out.println("Groups 加载"+b3 + ": " + Groups.size());
// generate group requests
        List<GroupInvite> GroupInvites = generateInvites(num * 10);
        boolean b4 = loadDataInDataBase.LoadListGroupInvite(GroupInvites);
        System.out.println("GroupInvites 加载"+b4 + ": " + GroupInvites.size());
// generate groupShips
        List<UserGroup> userGroupList = new ArrayList<>();
        for (String gid : getGroupIds()) {
            List<UserGroup> ugTemp = generateGroupRelationships(gid, num * 3);
            userGroupList.addAll(ugTemp);
        }
        boolean b = loadDataInDataBase.LoadListGroupShips(userGroupList);
        System.out.println("groupShip 总共加载: "+ b + ": " + userGroupList.size());
// generate group chats
        List<GroupChat> groupChatList = new ArrayList<>();
        for(String uid : getUserIds()) {
            List<GroupChat> gcTemp = generateGroupsChats(uid, num);
            groupChatList.addAll(gcTemp);
        }
        boolean b2 = loadDataInDataBase.LoadListGroupChats(groupChatList);
        System.out.println("groupChat 加载"+ b2 + ": " + groupChatList.size());
    }
}
