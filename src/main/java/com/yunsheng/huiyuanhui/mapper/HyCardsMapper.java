package com.yunsheng.huiyuanhui.mapper;

import com.yunsheng.huiyuanhui.model.HyCards;
import com.yunsheng.huiyuanhui.util.PageRequest;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("hyCardsMapper")
public interface HyCardsMapper {
    int deleteByPrimaryKey(Long cardId);

    int insert(HyCards record);

    int insertSelective(HyCards record);

    HyCards selectByPrimaryKey(Long cardId);

    int updateByPrimaryKeySelective(HyCards record);

    int updateByPrimaryKey(HyCards record);

    List<HyCards> selectListByPage(HyCards record, PageRequest<HyCards> pageRequest);
}