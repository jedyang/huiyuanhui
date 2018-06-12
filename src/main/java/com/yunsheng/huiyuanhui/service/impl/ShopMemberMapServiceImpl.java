package com.yunsheng.huiyuanhui.service.impl;

import com.yunsheng.huiyuanhui.mapper.ShopMemberMapMapper;
import com.yunsheng.huiyuanhui.model.Member;
import com.yunsheng.huiyuanhui.model.ShopMemberMap;
import com.yunsheng.huiyuanhui.service.ShopMemberMapService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("shopMemberMapService")
public class ShopMemberMapServiceImpl implements ShopMemberMapService {

    @Autowired
    private ShopMemberMapMapper shopMemberMapMapper;

    @Override
    public int insertRecord(ShopMemberMap shopMemberMap) {
        int insertSelective = shopMemberMapMapper.insertSelective(shopMemberMap);
        return insertSelective;
    }

    @Override
    public List<ShopMemberMap> findRecord(ShopMemberMap shopMemberMap) {
        List<ShopMemberMap> shopMemberMaps = shopMemberMapMapper.selectBySelective(shopMemberMap);
        return shopMemberMaps;
    }

    @Override
    public int updateRecord(ShopMemberMap shopMemberMap) {
        int i = shopMemberMapMapper.updateByPrimaryKeySelective(shopMemberMap);
        return i;
    }

}