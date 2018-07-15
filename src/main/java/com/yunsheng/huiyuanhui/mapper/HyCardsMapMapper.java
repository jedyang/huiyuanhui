package com.yunsheng.huiyuanhui.mapper;

import com.yunsheng.huiyuanhui.model.HyCardsMap;

public interface HyCardsMapMapper {
    int deleteByPrimaryKey(Long cardId);

    int insert(HyCardsMap record);

    int insertSelective(HyCardsMap record);

    HyCardsMap selectByPrimaryKey(Long cardId);

    int updateByPrimaryKeySelective(HyCardsMap record);

    int updateByPrimaryKey(HyCardsMap record);
}