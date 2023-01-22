package com.nart.controller;

import com.nart.common.LogA;
import com.nart.pojo.Group;
import com.nart.pojo.GroupInvite;
import com.nart.pojo.UserGroup;
import com.nart.service.GroupService;
import com.nart.util.ErrorCode;
import com.nart.util.Result;
import com.nart.util.UserThreadLocal;
import com.nart.vo.GroupVo;
import com.nart.vo.InviteVo;
import com.nart.vo.PageVo;
import com.nart.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: GroupController
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/8/31 14:07
 */
@RestController
@RequestMapping("group")
@LogA
public class GroupController {

    @Autowired
    private GroupService groupService;

    @LogA
    @GetMapping("memberList/{groupId}")
    public Result showGroupMemberList(@PathVariable("groupId") String gid) {
        List<UserVo> userVos = groupService.showGroupMebList(gid);
        if(userVos == null) {
            Result.fail(ErrorCode.SHOW_GROUP_MEMBER_ERROR);
        }

        return Result.success(userVos);
    }

    @LogA
    @PutMapping("changeInfo")
    public Result changeGroupInfo(@RequestBody GroupVo gInfo) {
        Group group = new Group();
        group.setId(gInfo.getId());
        group.setGroupName(gInfo.getName());
        group.setAvatar(gInfo.getAvatar());
        group.setNotice(gInfo.getNotice());
        boolean b = groupService.changeGroupInfo(group);
        if(b) {
            return Result.success(null);
        }
        return Result.fail(ErrorCode.CHANGE_GROUP_INFO_ERROR);
    }

    @LogA
    @PostMapping("list")
    public Result showGroupList(@RequestBody PageVo page) {
        List<GroupVo> groupVos = groupService.showGroupList(page.toIPage(UserGroup.class));
        if(groupVos == null) {
            Result.fail(ErrorCode.SHOW_GROUP_LIST_ERROR);
        }
        return Result.success(groupVos);
    }

    @LogA
    @DeleteMapping("del/{groupId}")
    public Result leaveGroup(@PathVariable("groupId") String gid) {
        boolean b = groupService.leaveGroup(gid, UserThreadLocal.get().getId());
        if(b) {
            return Result.success(null);
        }
        return Result.fail(ErrorCode.LEAVE_GROUP_ERROR);
    }

    @LogA
    @PutMapping("state/{groupId}/{state}")
    public Result changeGroupState(@PathVariable("groupId") String gid,
                                   @PathVariable("state") Integer state) {
        boolean b = groupService.changeGroupState(gid, UserThreadLocal.get().getId(), state);
        if(b) {
            return Result.success(null);
        }
        return Result.fail(ErrorCode.CHANGE_GROUP_STATE_ERROR);
    }

    @LogA
    @PostMapping("inviteList")
    public Result showInviteList(@RequestBody PageVo page) {
        List<InviteVo> inviteVos = groupService.showInviteList(page.toIPage(GroupInvite.class));


        if(inviteVos == null) {
            Result.fail(ErrorCode.SHOW_GROUP_INVITE_LIST_ERROR);
        }

        return Result.success(inviteVos);
    }

    @LogA
    @PostMapping("send")
    public Result sendInvite(@RequestBody GroupInvite inviteInfo) {
        boolean b = groupService.sendInvite(inviteInfo);
        if(b) {
            return Result.success(null);
        }
        return Result.fail(ErrorCode.SEND_GROUP_INVITE_ERROR);
    }

    @LogA
    @PutMapping("resp/{inviteId}/{agree}")
    public Result respGroupInvite(@PathVariable("inviteId") String inviteId,
                                  @PathVariable("agree") Boolean agree) {
        boolean b = groupService.respGroupInvite(inviteId, agree);
        if(b) {
            return Result.success(null);
        }
        return Result.fail(ErrorCode.RESP_GROUP_INVITE_ERROR);
    }

    @LogA
    @PostMapping("create/{groupName}")
    public Result createGroup(@PathVariable("groupName") String groupName) {
        boolean b = groupService.createGroup(groupName, UserThreadLocal.get().getId());

        if(b) {
            Group group = groupService.findGroup(groupName);
            String id = group.getId();

            return Result.success(id); // return gid
        }
        return Result.fail(ErrorCode.CREATE_GROUP_ERROR);
    }
}
