package com.nart.service;

import com.nart.pojo.*;

import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: NART
 *
 * @className: LoadDataInDataBase
 * This class is used to add records of each table
 * @version: v1.17.0
 * @Author YUNZHOU LIU
 * @Date 2023-1-14
 */
public interface LoadDataInDataBase {

    /**
     * This method is used to insert multiple user records into the user table
     *
     * @param userList
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean LoadListUser(List<User> userList);

    /**
     * This method is used to insert multiple friend application records into the request table
     *
     * @param friendReqList
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean LoadListFriendReq(List<FriendReq> friendReqList);

    /**
     * This method is used to insert multiple status records into the status table
     *
     * @param statusList
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean LoadListStatus(List<Status> statusList);

    /**
     * This method is used to insert multiple group records into the group table
     *
     * @param groupList
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean LoadListGroup(List<Group> groupList);

    /**
     * This method is used to insert multiple Comment records into the Comment table
     *
     * @param commentList
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean LoadListComment(List<Comment> commentList);

    /**
     * This method is used to insert multiple GroupInvite records into the Invite table
     *
     * @param groupInviteList
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean LoadListGroupInvite(List<GroupInvite> groupInviteList);

    boolean LoadListFriendShips(List<Friend> friendList);

    boolean LoadListFriendChats(List<FriendChat> friendChatList);

    boolean LoadListLikes(List<UserLike> likeList);

    boolean LoadListGroupShips(List<UserGroup> userGroups);

    boolean LoadListGroupChats(List<GroupChat> groupChatList);
}
