package com.yunsheng.huiyuanhui.service.impl;

import com.yunsheng.huiyuanhui.mapper.HyCardsMapper;
import com.yunsheng.huiyuanhui.model.HyCards;
import com.yunsheng.huiyuanhui.service.CardService;
import com.yunsheng.huiyuanhui.util.PageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Deprecated
@Service("cardServiceImpl")
public class CardServiceImpl implements CardService {

    @Autowired
    private HyCardsMapper hyCardsMapper;

    @Override
    public int insertRecord(HyCards hyCards) {
        int i = hyCardsMapper.insertSelective(hyCards);
        return i;
    }

    @Override
    public PageRequest<HyCards> queryRecordsByPage(HyCards hyCards, PageRequest<HyCards> pageRequest) {

        PageRequest<HyCards> data = pageRequest.data(hyCardsMapper.selectListByPage(hyCards, pageRequest));

        return data;
    }
}
