package com.nart.util;

import lombok.AllArgsConstructor;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: ErrorCode
 *  show what error it is
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/8/31 13:29
 */
@AllArgsConstructor
public enum ErrorCode {
    UNDEFINED(99999, "undefined error"),

    ACCOUNT_EXIST(10001, "account already exists"),
    PARAMS_ERROR(10101, "username or password format incorrect"),
    REGISTER_ERROR(10901, "register failed: unknown reason"),
    CHANGE_INFO_ERROR(10901, "change personal information failed: unknown reason"),
    ACCOUNT_PWD_NOT_MATCH(10501, "username and password not match"),
    USER_NOT_EXIST(10502, "User not exists"),
    SESSION_TIME_OUT(11001, "session time out"),
    NO_LOGIN(10301, "need login"),
    SHOW_FRIEND_LIST_ERROR(20901, "show friends list failed: unknown reason"),
    SEARCH_FRIEND_ERROR(20902, "search friends failed: unknown reason"),
    DEL_FRIEND_ERROR(20903, "delete Friend failed: unknown reason"),
    CHANGE_FRIEND_STATE_ERROR(20904, "change Friend state failed: unknown reason"),
    SHOW_FRIEND_REQS_ERROR(20905, "show friends requests failed: unknown reason"),
    SEND_FRIEND_REQ_ERROR(20906, "send Friend request failed: unknown reason"),
    RESP_FRIEND_REQ_ERROR(20907, "response to Friend request failed: unknown reason"),
    SEARCH_NEW_ERROR(20908, "search new friends failed: unknown reason"),
    SHOW_GROUP_MEMBER_ERROR(30901, "show group members list failed: unknown reason"),
    CHANGE_GROUP_INFO_ERROR(30902, "change group information failed: unknown reason"),
    SHOW_GROUP_LIST_ERROR(30903, "show group list failed: unknown reason"),
    LEAVE_GROUP_ERROR(30904, "leave group failed: unknown reason"),
    CHANGE_GROUP_STATE_ERROR(30905, "change group state failed: unknown reason"),
    SHOW_GROUP_INVITE_LIST_ERROR(30906, "show group invite list failed: unknown, reason"),
    SEND_GROUP_INVITE_ERROR(30907, "send group invite failed: unknown reason"),
    RESP_GROUP_INVITE_ERROR(30908, "response to group invite failed: unknown reason"),
    CREATE_GROUP_ERROR(30909, "create group failed: unknown reason"),
    LIKE_STATUS_ERROR(40901, "like status failed: unknown reason"),
    POST_STATUS_ERROR(40902, "post status failed: unknown reason"),
    DEL_STATUS_ERROR(40903, "delete status failed: unknown reason"),
    SHOW_COMMENT_LIST_ERROR(50901, "show comment list failed: unknown reason"),
    POST_COMMENT_ERROR(50902, "post comment failed: unknown reason"),
    SHOW_FRIEND_CHAT_HISTORY_ERROR(60901, "show friend chat history failed: unknown reason"),
    SHOW_GROUP_CHAT_HISTORY_ERROR(70901, "show group chat history failed: unknown reason"),
    SEND_FRIEND_CHAT_HISTORY_ERROR(60902, "send friend chat failed: unknown reason"),
    SEND_GROUP_CHAT_HISTORY_ERROR(70902, "send group chat failed: unknown reason"),
    LEAVE_FRIEND_CHAT_ERROR(60903, "update leave friend chatRoom time failed: unknown reason"),
    LEAVE_GROUP_CHAT_ERROR(70903, "update leave group chatRoom time failed: unknown reason"),

    UPLOAD_ERROR(80401, "upload picture failed"),
    UPLOAD_REPEAT_ERROR(80402, "upload repeat"),
    UPLOAD_DELETE_ERROR(80403, "delete picture fail"),
    ALREADY_DELETE_ERROR(80404, "already deleted"),
    TOKEN_ERROR(90001, "token illegal"),

    /**
     * Error code rules.
     * 5 digits.
     * The 10,000 and 1,000 digits represent the type of service in which the error occurred.
     * 10: User
     * 20: Friend
     * 30: Group
     * 40: Status
     * 50: Comment
     * 60: FriendChat
     * 70: GroupChat
     * 80: Upload
     * 90: Token
     * 11: Session
     * 99: Unknown
     *, more types will be added as we come across them, just write them down
     * 01 and 02 are not used because int types cannot start with 0
     *
     * Hundreds of digits represent the error type.
     * 0: Matching error (login information match error, registration information match already exists, token match failed ...)
     * 1: Type error (message format/type is incorrect...)
     * 3: Permission error (No permission/not logged in....)
     * 4: http request error (currently only occurs with Upload.)
     * 5: Not present (user, dynamic, group, etc. does not exist, id does not exist)
     * 9: Unknown
     * more types to be added as you encounter them, just write them down
     *, more types are encountered, just write them down.
     * The tens and single digits represent errors, with a different number for each error
     * e.g. 10001 is the first error of the user match error
     * 10002 is the second error of this type, each new error encountered is stacked upwards in turn
     * 99: Unknown
     *
     * This rule can be changed if you think something is wrong, just leave a record!
     */
    ;

    private Integer code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
