package com.yunsheng.huiyuanhui.mapper;

import com.yunsheng.huiyuanhui.model.ShopUser;

public interface ShopUserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(ShopUser record);

    int insertSelective(ShopUser record);

    ShopUser selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(ShopUser record);

    int updateByPrimaryKey(ShopUser record);
}