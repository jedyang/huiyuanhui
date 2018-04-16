package com.yunsheng.huiyuanhui.service.impl;

import com.yunsheng.huiyuanhui.mapper.ShopUserMapper;
import com.yunsheng.huiyuanhui.model.ShopUser;
import com.yunsheng.huiyuanhui.service.ShopUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "shopUserService")
public class ShopUserServiceImpl implements ShopUserService {

    @Autowired
    ShopUserMapper shopUserMapper;

    @Override
    public int addShopUser(ShopUser shopUser) {
        int i = shopUserMapper.insertSelective(shopUser);
        return i;
    }

    @Override
    public List<ShopUser> findBySelect(ShopUser shopUser) {
        List<ShopUser> shopUserList = shopUserMapper.selectBySelective(shopUser);
        return shopUserList;
    }

    @Override
    public ShopUser findByOpenId(String openId) {
        shopUserMapper.selectByOpenId(ShopUser openId);
        return null;
    }
}
