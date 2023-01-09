package com.nart.vo;

import com.nart.pojo.User;
import lombok.Data;

@Data
public class UserVo {
    private String id;
    private String avatar;
    private String uname;
    private String pwd;
    private String email;
    private String birthday;
    private String phone;
    private String address;
    private Integer power;
    private int lock;

    public UserVo transfer(User user){
        UserVo userVo =  new UserVo();
        userVo.setId(user.getId());
        userVo.setAvatar(user.getAvatar());
        userVo.setUname(user.getName());
        userVo.setPwd(user.getPwd());
        userVo.setEmail(user.getEmail());
        userVo.setBirthday(user.getAge());
        userVo.setPhone(user.getTel());
        userVo.setAddress(user.getAddress());
        userVo.setPower(user.getPower());
        //userVo.setLock(user.getLock());
        return userVo;
    }
}