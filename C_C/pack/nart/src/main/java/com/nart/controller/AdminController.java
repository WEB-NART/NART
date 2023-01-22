package com.nart.controller;

import com.nart.common.LogA;
import com.nart.pojo.Comment;
import com.nart.pojo.Status;
import com.nart.pojo.User;
import com.nart.service.AdminService;
import com.nart.service.LoginService;
import com.nart.service.UserService;
import com.nart.util.ErrorCode;
import com.nart.util.Result;
import com.nart.util.UserThreadLocal;
import com.nart.vo.CommentVo;
import com.nart.vo.StatusVo;
import com.nart.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @LogA
    @GetMapping("allUser")

    public Result showAllUserInfo() {
        List<User> users = adminService.showAllUserInfo();
        List<UserVo> userVos = new ArrayList<>();
        UserVo userVo = new UserVo();
        for (User user : users) {
            UserVo transfer = userVo.transfer(user);
            userVos.add(transfer);
        }
        if (userVos == null) {
            Result.fail(ErrorCode.USER_NOT_EXIST);
        }

        return Result.success(userVos);
    }

    @LogA
    @GetMapping("allUserNum")
    public Result showAllUserNum() {
        int i = adminService.showAllUserNum();
        if (i < 0) {
            Result.fail(ErrorCode.UNDEFINED);
        }

        return Result.success(i);
    }

    @LogA
    @GetMapping("onlineUser")
    public Result showOnlineUserInfo() {
        List<User> users = adminService.showOnlineUserInfo();

        List<UserVo> userVos = new ArrayList<>();
        UserVo userVo = new UserVo();
        for (User user : users) {
            UserVo transfer = userVo.transfer(user);
            userVos.add(transfer);
        }
        if (userVos == null) {
            Result.fail(ErrorCode.USER_NOT_EXIST);
        }
        return Result.success(userVos);
    }

    @LogA
    @GetMapping("onlineUserNum")
    public Result showOnlineUserNum() {
        int i = adminService.showOnlineUserNum();

        if (i < 0) {
            Result.fail(ErrorCode.UNDEFINED);
        }

        return Result.success(i);
    }

    @LogA
    @GetMapping("allStatus")
    public Result showAllStatusInfo() {

        List<Status> statuses = adminService.showAllStatusInfo();
        List<StatusVo> statusVos = new ArrayList<>();
        StatusVo statusVo = new StatusVo();
        for (Status status : statuses) {
            StatusVo transfer = statusVo.transfer(status);
            statusVos.add(transfer);
        }
        if (statusVos == null) {
            Result.fail(ErrorCode.UNDEFINED);
        }
        return Result.success(statusVos);
    }

    @LogA
    @GetMapping("allStatusNum")
    public Result showAllStatusNum() {
        int i = adminService.showAllStatusNum();
        if (i < 0) {
            Result.fail(ErrorCode.UNDEFINED);
        }
        return Result.success(i);
    }

    @LogA
    @GetMapping("allComment")
    public Result showAllCommentInfo() {

        List<Comment> comments = adminService.showAllCommentInfo();

        List<CommentVo> commentVos = new ArrayList<>();
        CommentVo commentVo = new CommentVo();
        for (Comment comment : comments) {
            CommentVo transfer = commentVo.transfer(comment);
            commentVos.add(transfer);
        }
        if (commentVos == null) {
            Result.fail(ErrorCode.UNDEFINED);
        }
        return Result.success(commentVos);
    }

    @LogA
    @GetMapping("allCommentNum")
    public Result showAllCommentNum() {
        int i = adminService.showAllCommentNum();
        if (i < 0) {
            Result.fail(ErrorCode.UNDEFINED);
        }
        return Result.success(i);
    }

    @LogA
    @GetMapping("searchUser/{id}")
    public Result searchUser(@PathVariable("id") String id) {
        User user = adminService.searchUser(id);

        if (user == null) {
            Result.fail(ErrorCode.UNDEFINED);
        }
        return Result.success(user);
    }

    @LogA
    @GetMapping("blockUser/{id}")
    public Result blockUser(@PathVariable("id") String id) {
        boolean b = adminService.blockUser(id);
        if (b == false) {
            Result.fail(ErrorCode.UNDEFINED);
        }
        return Result.success(b);
    }

    @LogA
    @GetMapping("deleteStatus/{id}")
    public Result deleteStatus(@PathVariable("id") String id) {

        boolean b = adminService.deleteStatus(id);
        if (b == false) {
            Result.fail(ErrorCode.UNDEFINED);
        }
        return Result.success(b);
    }

    @LogA
    @GetMapping("deleteComment/{id}")
    public Result deleteComment(@PathVariable("id") String id) {
        boolean b = adminService.deleteComment(id);

        if (b == false) {
            Result.fail(ErrorCode.UNDEFINED);
        }
        return Result.success(b);
    }


//
//    @LogA
//    @PutMapping("login")
//    public Result login(@RequestBody UserVo uInfo, HttpSession session) {
////        System.out.println(uInfo);
//        return loginService.login(uInfo.getUname(), uInfo.getPwd(), session);
//    }
//
//    @LogA
//    @PutMapping("logout")
//    public Result logout(@RequestHeader("Authorization") String token) {
//
//        userService.upDatetime(UserThreadLocal.get().getId());
//        return loginService.logout(token);
//
//        //应该在这个时候更新friend和group里的离开时间
//    }
//
//    @LogA
//    @PostMapping("register")
//    public Result register(@RequestBody UserVo uInfo, HttpSession session){
//        return loginService.register(uInfo.getEmail(), uInfo.getUname(), uInfo.getPwd(), session);
//    }
//
//    @LogA
//    @GetMapping("info/{id}")
//    public Result showUserInfo(@PathVariable("id") String id) {
//        System.out.println(id);
//        User user;
//        if(id.equals("-1")) {
//            user = userService.showUserInfo(UserThreadLocal.get().getId());
//        } else {
//            user = userService.showUserInfo(id);
//        }
//        if(user == null) {
//            Result.fail(ErrorCode.USER_NOT_EXIST);
//        }
//        UserVo userVo = new UserVo();
//        UserVo transfer = userVo.transfer(user);
//        return Result.success(transfer);
//    }

//    @LogA
//    @GetMapping
//    public Result showUnameAvatar() {
//        User user = userService.showUnameAvatar(UserThreadLocal.get().getId());
////        System.out.println(user);
//        UserVo userVo = new UserVo();
//        UserVo transfer = userVo.transfer(user);

//        userService.upDatetime(UserThreadLocal.get().getId());

//        return Result.success(transfer);
//    }

//    @LogA
//    @PutMapping("changeInfo")
//    public Result changeUserInfo(@RequestBody UserVo uInfo){
//        boolean b = userService.changeUserInfo(uInfo, UserThreadLocal.get().getId());
//        if(b) {
//            return Result.success(null);
//        }
//        return Result.fail(ErrorCode.CHANGE_INFO_ERROR);
//    }
//}
    }
