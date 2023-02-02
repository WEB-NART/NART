package com.nart.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nart.pojo.User;
import com.nart.vo.PageVo;
import com.nart.vo.UserVo;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: NART
 *
 * @className: UserService
 * @version: v1.17.0
 * @Author YUNZHOU LIU
 * @Date 2023-1-14
 */
public interface UserService {

    /**
     * This method set user state as login
     *
     * @param userId
     * @return User
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean login(String userId);

    /**
     * This method allows the user to find the user through the user name
     *
     * @param uname
     * @return User
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    User findUserByName(String uname);

    /**
     * This method changes the online status of a specific user
     *
     * @param userId
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean logout(String userId);

    /**
     * This method adds new users
     *
     * @param email
     * @param name
     * @param pwd
     * @param salt
     * @return User
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    User register(String email, String name, String pwd, String salt);

    /**
     * This method displays information for a specific user
     *
     * @param userId
     * @return User
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    User showUserInfo(String userId);

    /**
     * This method displays the avatar of a specific user
     *
     * @param userId
     * @return User
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    User showUnameAvatar(String userId);

    /**
     * This method changes the information of a specific user
     *
     * @param userVo
     * @param id
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean changeUserInfo(UserVo userVo, String id);

    /**
     * This method searches for specific friends through the passed in parameters
     *
     * @param name
     * @param pageVo
     * @return IPage of User
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    IPage<User> searchNew(String name, PageVo pageVo);

    /**
     * This method changes the departure time of a specific user
     *
     * @param uid
     * @return boolean
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    boolean upDatetime(String uid);

}
