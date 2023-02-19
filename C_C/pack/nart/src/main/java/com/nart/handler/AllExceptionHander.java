package com.nart.handler;

import com.nart.util.ErrorCode;
import com.nart.util.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: AllExceptionHander
 *  intercept all exceptions
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/1/07 14:22
 */
@ControllerAdvice
public class AllExceptionHander {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception e) {
        e.printStackTrace();
        return Result.fail(ErrorCode.UNDEFINED);
    }
}
