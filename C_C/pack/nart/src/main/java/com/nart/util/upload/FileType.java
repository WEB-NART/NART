package com.nart.util.upload;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: FileType
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/01/03 17:40
 */
public class FileType{
    public static ContentType checkType(String name) {
        String suffix = StringUtils.substringAfterLast(name, ".");
        switch (suffix) {
            case "jpg":
            case "jpeg":
                return ContentType.IMAGE_JPEG;
            case "png":
                return ContentType.IMAGE_PNG;
            case "gif":
                return ContentType.IMAGE_GIF;
            default:
                return null;
        }
    }
}
