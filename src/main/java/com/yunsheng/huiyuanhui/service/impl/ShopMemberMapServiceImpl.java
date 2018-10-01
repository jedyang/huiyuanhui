package com.yunsheng.huiyuanhui.service.impl;

import com.yunsheng.huiyuanhui.mapper.ConsumeLogMapper;
import com.yunsheng.huiyuanhui.mapper.ShopMemberMapMapper;
import com.yunsheng.huiyuanhui.model.ConsumeLog;
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

    @Autowired
    private ConsumeLogMapper consumeLogMapper;

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
    public ConsumeLog pay(Pay payInfo) {
        ShopMemberMap record = this.findByPK(Integer.valueOf(payInfo.getCardId()));


        // 记录log
        ConsumeLog consumeLog = new ConsumeLog();
        consumeLog.setShopId(record.getShopId());
        consumeLog.setCardId(record.getId());
        consumeLog.setMemberId(record.getMemberId());
        consumeLog.setType(1);
        consumeLog.setMoney(Double.valueOf(payInfo.getUseMoney()));
        consumeLog.setPoints(Integer.valueOf(payInfo.getUsePoint()));
        consumeLog.setOldPoints(record.getPoint());
        consumeLog.setOldMoney(record.getMoney());
        consumeLog.setNewPoints(record.getPoint() - Integer.valueOf(payInfo.getUsePoint()));
        consumeLog.setNewMoney(record.getMoney() - Double.valueOf(payInfo.getUseMoney()));

        consumeLogMapper.insertSelective(consumeLog);

        // 减掉卡里的钱或积分
        record.setMoney(record.getMoney() - Double.valueOf(payInfo.getUseMoney()));
        record.setPoint(record.getPoint() - Integer.valueOf(payInfo.getUsePoint()));

        this.updateRecord(record);

        // 重新从数据库里查出来，为了得到时间数据
        ConsumeLog consumeLog1 = consumeLogMapper.selectByPrimaryKey(consumeLog.getLogId());
        return consumeLog1;
    }

}
