package com.nart.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nart.pojo.FriendChat;
import com.nart.pojo.GroupChat;
import com.nart.vo.MessageVo;

import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: NART
 * @className: ChatService
 *  This class provides various operations related to sending information
 *
 * @version: v1.17.0
 * @Author YUNZHOU LIU
 * @Date 2023-1-14
 *
 */
public interface ChatService {

    /**
     *   This method allows users to send messages between friends
     * @param friendChat
      * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean sendFriendMsg(FriendChat friendChat);

    /**
     *  This method allows users to send group information
     * @param groupChat
      * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean sendGroupMsg(GroupChat groupChat);

    /**
     *  This method allows users to accept messages sent by friends
     * @param reciviceId
     * @param page
     * @return List of FriendChat
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    List<FriendChat> receiveFriendMsg(String reciviceId, IPage page);

    /**
     *  This method allows users to accept information sent by members of the group
     * @param reciviceId
     * @param page
      * @return List of GroupChat
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    List<GroupChat> receiveGroupMsg(String reciviceId, IPage page);

    /**
     *  This method displays the chat records between friends
     * @param Id
     * @param page
      * @return List of MessageVo
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    List<MessageVo> showFriendHistory(String Id, IPage page);

    /**
     *  This method displays the chat records in the group
     * @param gId
     * @param page
      * @return List of MessageVo
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    List<MessageVo> showGroupHistory(String gId, IPage page);

    /**
     *  This method updates the time when users leave the group to chat or chat with friends
     * @param roomId
     * @param isF
      * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean leaveRoom (String roomId, Boolean isF);

    /**
     *  This method determines whether there is the latest information in the chat
     * @param id
     * @param isF
     * @param leaveTime
      * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean existNewMsg(String id, Boolean isF, Long leaveTime);
}
