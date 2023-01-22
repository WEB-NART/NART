package com.nart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nart.pojo.Friend;
import com.nart.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface FriendDao extends BaseMapper<Friend> {

//    List<User> searchFriend(@Param("id")int id,@Param("id")int id,@Param("id")int id,@Param("id")int id);
}
