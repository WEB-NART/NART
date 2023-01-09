package com.nart.vo;

import lombok.Data;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: WSMsg
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/9/3 11:47
 */
@Data
public class WSMsg {
    private String msg;
    private String sender;
    private String senderName;
    private String senderAvatar;
    private String msgType;
    private String receiver;
    private String receiverType;
}
