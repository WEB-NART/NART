package com.nart.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: Result
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/01/01 13:27
 */
@Data
@AllArgsConstructor
public class Result {
    private Boolean success;
    private Integer code;
    private String msg;
    private Object data;

    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }

    public static Result fail(int code, String msg) {
        return new Result(false, code, msg, null);
    }

    public static Result fail(ErrorCode errorCode) {
        return new Result(false, errorCode.getCode(), errorCode.getMsg(), null);
    }
}
