package com.nart.controller;

import com.nart.common.LogA;
import com.nart.util.ErrorCode;
import com.nart.util.FakeDataGenerator;
import com.nart.util.upload.ImgtuUtil;
import com.nart.util.Result;
import com.nart.service.LoadDataInDataBase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: UploadController
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/8/31 15:27
 */
@RestController
@RequestMapping("upload")
@LogA
public class UploadController {

    @Autowired
    private LoadDataInDataBase ld;


    /**
     * @Description: upload picture to cloud
     * @param file picture list
     * @param album album
     *              0: Status album
     *              1: user avatar album
     *              2: group avatar album
     *              others: chat album
     * @return: com.nart.util.Result
     * @Author: Zirui Qiao
     * @Date: 2023-01-31 2:31 p.m.
     */
    @PostMapping("{album}")
    public Result upload(@RequestPart("file") MultipartFile file, @PathVariable("album") Integer album) throws IOException {
        String name = file.getOriginalFilename();
        String suffix = StringUtils.substringAfterLast(name, ".");
        String fileName = UUID.randomUUID().toString() + "." + suffix;
        String s = ImgtuUtil.uploadPic(file.getBytes(), fileName, album);

        if(StringUtils.isNotBlank(s)) {
            if(s.equals("400")) {
                return Result.fail(ErrorCode.UPLOAD_REPEAT_ERROR);
            }
            return Result.success(s);
        }
        return Result.fail(ErrorCode.UPLOAD_ERROR);
    }

    @PutMapping("delete/{id}")
    public Result delete(@PathVariable("id") String id) throws IOException {
        String s = ImgtuUtil.deletePic(id);

        if(StringUtils.isNotBlank(s)) {
            if(s.equals("400"))
                return Result.fail(ErrorCode.ALREADY_DELETE_ERROR);
            else if (s.equals("200"))
                return Result.success(s);
        }
        return Result.fail(ErrorCode.UPLOAD_DELETE_ERROR);
    }

    @GetMapping("test")
    public Result test() {
        FakeDataGenerator fakeDataGenerator = new FakeDataGenerator();
        fakeDataGenerator.generateTestData(1);
        return Result.success("yes");
    }


}
