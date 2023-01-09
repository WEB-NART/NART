package com.nart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nart.pojo.DataCounter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataCounterDao extends BaseMapper<DataCounter> {
    int updateUserAmount(@Param("id")int id);
    int updateUserAmountm(@Param("id")int id);

    int updateOnlineUserAmount(@Param("id")int id);
    int updateOnlineUserAmountm(@Param("id")int id);

    int updateStatusAmount(@Param("id")int id);
    int updateStatusAmountm(@Param("id")int id);

    int updateCommentAmount(@Param("id")int id);
    int updateCommentAmountm(@Param("id")int id);

    int updateMessageAmount(@Param("id")int id);
    int updateMessageAmountm(@Param("id")int id);


}
