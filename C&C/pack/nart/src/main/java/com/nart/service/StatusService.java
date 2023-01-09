package com.nart.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nart.pojo.Status;

import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: NART
 *
 * @className: StatusService
 * This class contains a variety of methods for modifying the status table in the database,
 * including adding, deleting, modifying, and querying methods.
 * @version: v1.17.0
 * @Author YUNZHOU LIU
 * @Date 2023-1-14
 */
public interface StatusService {
    /**
     * This method can find the status sent by the user and his friends.
     *
     * @param uid   user id, The id of user
     * @param page
     * @return List of Status
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    List<Status> showAllStatusList(String uid, IPage page);

    /**
     * This method can find the status sent by the incoming user ID.
     *
     * @param sid   user id, The id of user
     * @param page
     * @return List of Status
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    List<Status> showStatusList(String sid, IPage page);

    /**
     * This method adds the incoming status to the status table
     *
     * @param status
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean postStatus(Status status);

    /**
     * This method deletes the status of a specific ID.
     *
     * @param id The id of Status
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean delStatus(String id);

    /**
     * This method adds or deletes the like data of the status of a specific ID according to the passed in Boolean parameters
     *
     * @param id   The id of Status
     * @param like
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean likeStatus(String id, boolean like);

}
