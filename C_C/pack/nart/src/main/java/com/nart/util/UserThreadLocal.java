package com.nart.util;

import com.nart.pojo.User;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: UserThreadLocal
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/01/03 22:49
 */
public class UserThreadLocal {
    private UserThreadLocal(){}

    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();

    public static void put(User user) {
        LOCAL.set(user);
    }
    public static User get() {
        return LOCAL.get();
    }
    public static void remove() {
        LOCAL.remove();
    }
}
