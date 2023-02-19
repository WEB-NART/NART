package com.nart.util;

import com.google.gson.Gson;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: GsonFormatter
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/01/01 13:22
 */
public class GsonFormatter {
    private static final Gson gson = new Gson();

    public static String toJsonString(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJsonToObj(String str, Class<T> type) {
        return gson.fromJson(str, type);
    }
}
