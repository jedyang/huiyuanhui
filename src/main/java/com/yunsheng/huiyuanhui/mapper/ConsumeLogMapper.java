package com.yunsheng.huiyuanhui.mapper;

import com.yunsheng.huiyuanhui.model.ConsumeLog;
import com.yunsheng.huiyuanhui.util.PageRequest;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("consumeLogMapper")
public interface ConsumeLogMapper {
    int deleteByPrimaryKey(Integer logId);

    int insert(ConsumeLog record);

    int insertSelective(ConsumeLog record);

    ConsumeLog selectByPrimaryKey(Integer logId);

    List<ConsumeLog> selectBySelective(ConsumeLog record, PageRequest<ConsumeLog> pageRequest);

    int updateByPrimaryKeySelective(ConsumeLog record);

    int updateByPrimaryKey(ConsumeLog record);

}