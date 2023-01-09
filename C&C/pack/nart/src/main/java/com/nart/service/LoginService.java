package com.nart.service;

import com.nart.pojo.User;
import com.nart.util.Result;

import javax.servlet.http.HttpSession;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: NART
 * @className: LoginService
 *  This class provides operations such as user login
 *
 * @version: v1.17.0
 * @Author YUNZHOU LIU
 * @Date 2023-1-14
 *
 */
public interface LoginService {
    /**
     *  In this method, users can log in to the system
        @param uname
        @param pwd
        @param session
      * @return Result
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    Result login(String uname, String pwd, HttpSession session);

    /**
     *  This method checks the user's token
       @param token
      * @return Result
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    User checkToken(String token);

    /**
     *  This method logs out the user
       @param token
      * @return Result
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    Result logout(String token);

    /**
     *  This method can register users
        @param email
        @param uname
        @param pwd
        @param session
      * @return Result
     * @Author Yunzhou Liu
     * @Date 2023-1-14
     */
    Result register(String email, String uname, String pwd, HttpSession session);
}
