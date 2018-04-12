package com.yunsheng.huiyuanhui.service.impl;

import com.yunsheng.huiyuanhui.mapper.ShopMapper;
import com.yunsheng.huiyuanhui.model.Shop;
import com.yunsheng.huiyuanhui.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "shopService")
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public boolean insertShop(Shop shop) {
        int insertSelective = shopMapper.insertSelective(shop);
        return insertSelective == 1 ? true : false;
    }
}
