package com.yunsheng.huiyuanhui.service.impl;

import com.yunsheng.huiyuanhui.mapper.ShopMemberMapMapper;
import com.yunsheng.huiyuanhui.model.Member;
import com.yunsheng.huiyuanhui.model.Pay;
import com.yunsheng.huiyuanhui.model.ShopMemberMap;
import com.yunsheng.huiyuanhui.service.ShopMemberMapService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ShopMemberMap findByPK(Integer id) {
        ShopMemberMap shopMemberMap = shopMemberMapMapper.selectByPrimaryKey(id);
        return shopMemberMap;
    }

    @Override
    public int updateRecord(ShopMemberMap shopMemberMap) {
        int i = shopMemberMapMapper.updateByPrimaryKeySelective(shopMemberMap);
        return i;
    }

    @Transactional
    @Override
    public boolean pay(Pay payInfo) {
        ShopMemberMap record = this.findByPK(Integer.valueOf(payInfo.getCardId()));
        // 减掉卡里的钱或积分
        record.setMoney(record.getMoney() - Double.valueOf(payInfo.getUseMoney()));
        record.setPoint(record.getPoint() - Integer.valueOf(payInfo.getUsePoint()));

        this.updateRecord(record);

        throw new RuntimeException();
        // 记录log
//        return false;
    }

}
