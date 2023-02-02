package com.nart.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nart.common.LogA;

import com.nart.dao.UserDao;
import com.nart.pojo.Status;
import com.nart.pojo.User;
import com.nart.service.StatusService;
import com.nart.util.ErrorCode;
import com.nart.util.UserThreadLocal;
import com.nart.util.Result;
import com.nart.vo.PageVo;
import com.nart.vo.StatusVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: StatusController
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/8/31 14:56
 */
@RestController
@RequestMapping("status")
@LogA
public class StatusController {

    @Autowired
    private StatusService statusService;

    @Autowired
    private UserDao userDao;

    @LogA
    @PostMapping("list/{type}/{uid}")
    public Result showStatusList(@PathVariable("type") String type,
                                 @PathVariable("uid") String uid,
                                 @RequestBody PageVo pageVo) {
        IPage<User> page = new Page<>(pageVo.getPageNum(), pageVo.getPageSize());

        StatusVo statusVo = new StatusVo();

        if (type.equals("my")){
            if(uid.equals("-1")) {
//                show current User's Status
                uid = UserThreadLocal.get().getId();
                List<Status> statuses = statusService.showStatusList(uid, page);
//                System.out.println(statuses);
                List<StatusVo> statusVos = new ArrayList<>();
                for (Status status : statuses) {
                    StatusVo transfer = statusVo.transfer(status);

                    User user = userDao.selectById(status.getSenderId());
//                    System.out.println(user);
                    transfer.setAvatar(user.getAvatar());
                    transfer.setUname(user.getName());

                    statusVos.add(transfer);
                }
                return Result.success(statusVos);
            } else {
//                show uid User's Status
                List<Status> statuses = statusService.showStatusList(uid, page);
//                System.out.println(statuses);
                List<StatusVo> statusVos = new ArrayList<>();
                for (Status status : statuses) {
                    StatusVo transfer = statusVo.transfer(status);

                    User user = userDao.selectById(status.getSenderId());
                    //System.out.println(user);
                    transfer.setAvatar(user.getAvatar());
                    transfer.setUname(user.getName());
                    statusVos.add(transfer);
                }
                return Result.success(statusVos);
            }
        } else {
            uid = UserThreadLocal.get().getId();
            List<Status> statuses;
            statuses = statusService.showAllStatusList(uid, page);
            //List<Status> statuses = statusService.showAllStatusList(uid);
//            List<Status> statuses = statusService.showStatusList(uid, page);
//            return Result.success(statuses);
            List<StatusVo> statusVos = new ArrayList<>();
            for (Status status : statuses) {
                StatusVo transfer = statusVo.transfer(status);

                User user = userDao.selectById(status.getSenderId());
                //System.out.println(user);
                transfer.setAvatar(user.getAvatar());
                transfer.setUname(user.getName());
                statusVos.add(transfer);
            }
            return Result.success(statusVos);
        }
        //return Result.fail(ErrorCode.UNDEFINED);
    }

    @LogA
    @PutMapping("like/{statusId}/{like}")
    public Result likeStatus(@PathVariable("statusId") String statusId,
                             @PathVariable("like") Boolean like) {
        boolean b = statusService.likeStatus(statusId, like);
        if(b) {
            return Result.success(null);
        } else {
            return Result.fail(ErrorCode.LIKE_STATUS_ERROR);
        }
    }

    @LogA
    @PostMapping("post")
    public Result postStatus(@RequestBody StatusVo statusInfo) {
        //System.out.println(statusInfo);
        Status status = new Status();
        status.setSenderId(UserThreadLocal.get().getId());
        status.setText(statusInfo.getMsg());


        List<String> pics = statusInfo.getPics();
        if(pics != null) {
            String join = StringUtils.join(pics.toArray(), ";");
            //System.out.println(join);
            status.setPics(join);
//       status.setPics(GsonFormatter.toJsonString(statusInfo.getPics()));
        }
        status.setLikes(0);
        status.setCreateDate(new Date().getTime());
        boolean b = statusService.postStatus(status);
        if(b) {
            return Result.success(null);
        } else {
            return Result.fail(ErrorCode.POST_STATUS_ERROR);
        }
    }

    @LogA
    @DeleteMapping("del/{statusId}")
    public Result delStatus(@PathVariable("statusId") String statusId) {
        boolean b = statusService.delStatus(statusId);
        if(b) {
            return Result.success(null);
        } else {
            return Result.fail(ErrorCode.DEL_STATUS_ERROR);
        }
    }
}
