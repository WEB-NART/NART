package com.nart.controller;

import com.nart.common.LogA;
import com.nart.pojo.User;
import com.nart.service.LoginService;
import com.nart.service.UserService;
import com.nart.util.ErrorCode;
import com.nart.util.Result;
import com.nart.util.UserThreadLocal;
import com.nart.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: UserController
 *  user related request controller
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/01/12 13:25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;

    @LogA
    @PutMapping("login")
    public Result login(@RequestBody UserVo uInfo, HttpSession session) {
//        System.out.println(uInfo);
        return loginService.login(uInfo.getUname(), uInfo.getPwd(), session);
    }

    @LogA
    @PutMapping("logout")
    public Result logout(@RequestHeader("Authorization") String token) {
        userService.upDatetime(UserThreadLocal.get().getId());
        return loginService.logout(token);
    }

    @LogA
    @PostMapping("register")
    public Result register(@RequestBody UserVo uInfo, HttpSession session){
        return loginService.register(uInfo.getEmail(), uInfo.getUname(), uInfo.getPwd(), session);
    }

    @LogA
    @GetMapping("info/{id}")
    public Result showUserInfo(@PathVariable("id") String id) {
        //System.out.println(id);
        User user;
        if(id.equals("-1")) {
            user = userService.showUserInfo(UserThreadLocal.get().getId());
        } else {
            user = userService.showUserInfo(id);
        }
        if(user == null) {
            return Result.fail(ErrorCode.USER_NOT_EXIST);
        }
        UserVo userVo = new UserVo();
        UserVo transfer = userVo.transfer(user);
        return Result.success(transfer);
    }

    @LogA
    @GetMapping
    public Result showUnameAvatar() {
        User user = userService.showUnameAvatar(UserThreadLocal.get().getId());
//        System.out.println(user);
        UserVo userVo = new UserVo();
        UserVo transfer = userVo.transfer(user);

//        userService.upDatetime(UserThreadLocal.get().getId());

        return Result.success(transfer);
    }

    @LogA
    @PutMapping("changeInfo")
    public Result changeUserInfo(@RequestBody UserVo uInfo){
        boolean b = userService.changeUserInfo(uInfo, UserThreadLocal.get().getId());
        if(b) {
            return Result.success(null);
        }
        return Result.fail(ErrorCode.CHANGE_INFO_ERROR);
    }
}
