package com.yunsheng.huiyuanhui.mapper;

import com.yunsheng.huiyuanhui.model.ShopUser;

import java.util.List;

public interface ShopUserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(ShopUser record);

    int insertSelective(ShopUser record);

    ShopUser selectByPrimaryKey(Integer userId);

    ShopUser selectByOpenId(String openId);

    List<ShopUser> selectBySelective(ShopUser record);



    int updateByPrimaryKeySelective(ShopUser record);

    int updateByPrimaryKey(ShopUser record);
}