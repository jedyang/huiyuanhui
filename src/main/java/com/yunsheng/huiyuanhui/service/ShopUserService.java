package com.yunsheng.huiyuanhui.service;

import com.yunsheng.huiyuanhui.model.ShopUser;

import java.util.List;

public interface ShopUserService {
    int addShopUser(ShopUser shopUser);

    List<ShopUser> findBySelect(ShopUser shopUser);

    ShopUser findByOpenId(String openId);
}
