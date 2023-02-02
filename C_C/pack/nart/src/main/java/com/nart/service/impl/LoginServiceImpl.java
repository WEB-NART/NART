package com.nart.service.impl;

import com.nart.pojo.User;
import com.nart.service.DataCounterService;
import com.nart.service.LoginService;
import com.nart.service.UserService;
import com.nart.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: LoginServiceImpl
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/9/1 12:19
 */
@Transactional
@Service
public class LoginServiceImpl implements LoginService {

    private final UserService userService;
    private final RedisUtil redisUtil;
    private final DataCounterService dataCounterService;

    @Autowired
    public LoginServiceImpl(UserService userService, RedisUtil redisUtil, DataCounterService dataCounterService) {
        this.userService = userService;
        this.redisUtil = redisUtil;
        this.dataCounterService = dataCounterService;
    }

    @Override
    public Result login(String uname, String pwd, HttpSession session) {
        if (StringUtils.isBlank(uname) || StringUtils.isBlank(pwd)) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.findUserByName(uname);
        String password = EncryptUtil.encryptPwd(pwd, user.getSalt());
        boolean correct_pwd = Objects.equals(user.getPwd(), password);

        if (!correct_pwd) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_MATCH);
        }
        String token = EncryptUtil.createToken(Long.parseLong(user.getId()));
        redisUtil.set("TOKEN_" + token, user, RedisUtil.DEFAULT_EXPIRE);

        //System.out.println("user: " + (String) session.getAttribute("user"));
        //System.out.println("session isNew? " + session.isNew() );
        session.setAttribute("uid", "uid: " + user.getId());
        //System.out.println((String) session.getAttribute("uid"));

        int i = dataCounterService.updateOnlineUserAmount(true);
        boolean login = userService.login(user.getId());
        return Result.success(token);
    }

    @Override
    public User checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = EncryptUtil.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        User user = redisUtil.get("TOKEN_" + token, User.class);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public Result logout(String token) {
        redisUtil.delete("TOKEN_" + token);
        userService.logout(UserThreadLocal.get().getId());
        dataCounterService.updateOnlineUserAmount(false);
        return Result.success(null);
    }

    @Override
    public Result register(String email, String uname, String pwd, HttpSession session) {
        if (email.length() == 0 || uname.length() == 0 || pwd.length() == 0) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.findUserByName(uname);
        if (user != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST);
        }
        String salt = EncryptUtil.getRandLengthSalt();
        String password = EncryptUtil.encryptPwd(pwd, salt);
        User user1 = userService.register(email, uname, password, salt);

        if(user1 == null) {
            return Result.fail(ErrorCode.REGISTER_ERROR);
        }
        String token = EncryptUtil.createToken(Long.parseLong(user1.getId()));

        redisUtil.set("TOKEN_" + token, user1, RedisUtil.DEFAULT_EXPIRE);
        session.setAttribute("uid", "uid: " + user1.getId());

        dataCounterService.updateUserAmount(true);
        dataCounterService.updateOnlineUserAmount(true);
        return Result.success(token);
    }
}
