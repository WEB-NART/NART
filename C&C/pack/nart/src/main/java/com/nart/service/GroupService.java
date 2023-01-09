package com.nart.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nart.pojo.Group;
import com.nart.pojo.GroupInvite;
import com.nart.vo.GroupVo;
import com.nart.vo.InviteVo;
import com.nart.vo.UserVo;

import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: NART
 *
 * @className: GroupService
 * This class contains various operations on the group and user group tables
 * This method can also upload new comments.
 * @version: v1.17.0
 * @Author YUNZHOU LIU
 * @Date 2023-1-14
 */
public interface GroupService {
    /**
     * This method returns the group members
     *
     * @param gid
     * @return List of UserVo
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    List<UserVo> showGroupMebList(String gid);

    /**
     * This method changes the attributes of the group
     *
     * @param group
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean changeGroupInfo(Group group);

    /**
     * This method displays the group information that the user participates in
     *
     * @param page
     * @return List of GroupVo
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    List<GroupVo> showGroupList(IPage page);

    /**
     * This method deletes the records of a specific user in a specific group
     *
     * @param gid
     * @param uid
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean leaveGroup(String gid, String uid);

    /**
     * This method changes the status of a specific user in a specific group
     *
     * @param gid
     * @param uid
     * @param state
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean changeGroupState(String gid, String uid, int state);

    /**
     * This method shows that the recipient is the current user's group invitation
     *
     * @param page
     * @return List of InviteVo
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    List<InviteVo> showInviteList(IPage page);

    /**
     * This method sends group invitations
     *
     * @param groupInvite invitation object
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean sendInvite(GroupInvite groupInvite);

    /**
     * This method accepts the group invitation
     *
     * @param InviteId invitation id
     * @param agree agree or reject
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean respGroupInvite(String InviteId, Boolean agree);

    /**
     * This method creates a team
     *
     * @param groupName group name
     * @param uid user id
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean createGroup(String groupName, String uid);

    /**
     * This method joins the group
     *
     * @param groupId group Id
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean joinGroup(String groupId);

    /**
     * This method displays the names of all team members
     *
     * @param gid group Id
     * @return Set of String
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    Set<String> findAllMembers(String gid);

    /**
     * This method returns a specific group
     *
     * @param groupName group name
     * @return Group
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    Group findGroup(String groupName);
}
