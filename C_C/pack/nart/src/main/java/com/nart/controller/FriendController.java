package com.nart.controller;

import com.nart.common.LogA;
import com.nart.pojo.Friend;
import com.nart.pojo.FriendReq;
import com.nart.service.FriendService;
import com.nart.util.ErrorCode;
import com.nart.util.Result;
import com.nart.util.UserThreadLocal;
import com.nart.vo.FriendVo;
import com.nart.vo.PageVo;
import com.nart.vo.RequestVo;
import com.nart.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: FriendController
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/8/31 14:40
 */
@RestController
@RequestMapping("friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @LogA
    @PostMapping("list")
    public Result showFriendList(@RequestBody PageVo page) {
        List<FriendVo> friendVos = friendService.showFriendList(
                page.toIPage(Friend.class), UserThreadLocal.get().getId());
        if(friendVos == null) {
            return Result.fail(ErrorCode.SHOW_FRIEND_LIST_ERROR);
        }
        return Result.success(friendVos);
    }

    @LogA
    @PostMapping("search/{input}")
    public Result searchFriend(@RequestBody PageVo page,
                               @PathVariable("input") String input) {
        if(input.equals("-1")) {
            List<FriendVo> userVos = friendService.showFriendList(
                    page.toIPage(Friend.class),
                    UserThreadLocal.get().getId()
            );
            if(userVos == null) {
                return Result.success(ErrorCode.SEARCH_FRIEND_ERROR);
            }
            return Result.success(userVos);
        } else {
            List<UserVo> userVos = friendService.searchFriend(input, page.toIPage(Friend.class));
            if(userVos == null) {
                return Result.success(ErrorCode.SEARCH_FRIEND_ERROR);
            }
            return Result.success(userVos);
        }
    }

    @LogA
    @DeleteMapping("del/{friendId}")
    public Result delFriend(@PathVariable("friendId") String fid) {
        boolean b = friendService.delFriend(fid, UserThreadLocal.get().getId());
        if(b) {
            return Result.success(null);
        }
        return Result.fail(ErrorCode.DEL_FRIEND_ERROR);
    }

    @LogA
    @PutMapping("state/{friendId}/{state}")
    public Result changeFriendState(@PathVariable("friendId") String fid,
                                    @PathVariable("state") Integer state) {
        boolean b = friendService.changeFriendState(fid, UserThreadLocal.get().getId(), state);
        if(b) {
            return Result.success(null);
        }
        return Result.fail(ErrorCode.CHANGE_FRIEND_STATE_ERROR);
    }

    @LogA
    @PostMapping("reqlist")
    public Result showReqList(@RequestBody PageVo page) {
        List<RequestVo> requestVos = friendService.showReqList(
                page.toIPage(FriendReq.class), UserThreadLocal.get().getId());
        if(requestVos == null) {
            return Result.fail(ErrorCode.SHOW_FRIEND_REQS_ERROR);
        }
        return Result.success(requestVos);
    }

    @LogA
    @PostMapping("send")
    public Result sendFriendReq(@RequestBody FriendReq reqInfo) {
        boolean b = friendService.sendFriendReq(
                reqInfo.getReceiverId(), UserThreadLocal.get().getId(), reqInfo.getMsg());
        if(b) {
            return Result.success(null);
        }
        return Result.fail(ErrorCode.SEND_FRIEND_REQ_ERROR);
    }

    @LogA
    @GetMapping("resp/{requestId}/{agree}")
    public Result respFriendReq(@PathVariable("requestId") String reqId,
                                @PathVariable("agree") Boolean agree) {
        boolean b = friendService.respFriendReq(reqId, agree);
        if(b) {
            return Result.success(null);
        }
        return Result.fail(ErrorCode.RESP_FRIEND_REQ_ERROR);
    }

    @LogA
    @PostMapping("searchNew/{input}")
    public Result searchNew(@RequestBody PageVo page,
                              @PathVariable("input") String input) {
        List<UserVo> userVos = friendService.searchNew(input, page);
        if(userVos == null) {
            return Result.success(ErrorCode.SEARCH_NEW_ERROR);
        }
        return Result.success(userVos);
    }
}
