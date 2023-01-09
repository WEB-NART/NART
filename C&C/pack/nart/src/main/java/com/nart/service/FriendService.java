package com.nart.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nart.pojo.Friend;
import com.nart.vo.FriendVo;
import com.nart.vo.PageVo;
import com.nart.vo.RequestVo;
import com.nart.vo.UserVo;

import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: NART
 *
 * @className: FriendService
 * This class provides operations on friendship
 * @version: v1.17.0
 * @Author YUNZHOU LIU
 * @Date 2023-1-14
 */
public interface FriendService {

    /**
     * This method displays all friends of a specific user
     *
     * @param page
     * @param userId
     * @return List of FriendVo
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    List<FriendVo> showFriendList(IPage page, String userId);

    /**
     * This method is to find new friends
     *
     * @param name
     * @param page
     * @return List of UserVo
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    List<UserVo> searchFriend(String name, IPage page);

    /**
     * This method deletes a specific friend relationship
     *
     * @param fid
     * @param uid
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean delFriend(String fid, String uid);

    /**
     * This method changes the state of a particular friendship
     *
     * @param fid
     * @param uid
     * @param state
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean changeFriendState(String fid, String uid, int state);

    /**
     * This method displays the friend invitations accepted by a specific user
     *
     * @param page
     * @param rid
     * @return List of RequestVo
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    List<RequestVo> showReqList(IPage page, String rid);

    /**
     * This method allows users to send friend invitations
     *
     * @param rid
     * @param sid
     * @param msg
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean sendFriendReq(String rid, String sid, String msg);

    /**
     * This method allows users to accept invitations
     *
     * @param reqId
     * @param agree
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean respFriendReq(String reqId, Boolean agree);

    /**
     * This method allows users to search for new friends
     *
     * @param name
     * @param pageVo
     * @return List of UserVo
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    List<UserVo> searchNew(String name, PageVo pageVo);

    /**
     * This method displays the user's friends
     *
     * @param uid
     * @return List of Friend
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    List<Friend> findAllFriends(String uid);
}
