package com.yunsheng.huiyuanhui.service.impl;

import com.yunsheng.huiyuanhui.mapper.ShopUserMapMapper;
import com.yunsheng.huiyuanhui.model.ShopUserMap;
import com.yunsheng.huiyuanhui.service.ShopUserMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("shopUserMapService")
public class ShopUserMapServiceImpl implements ShopUserMapService {

    @Autowired
    private ShopUserMapMapper shopUserMapMapper;

    @Override
    public int insertRecord(ShopUserMap shopUserMap) {
        int i = shopUserMapMapper.insertSelective(shopUserMap);
        return i;
    }
}
