package com.yunsheng.huiyuanhui.mapper;

import com.yunsheng.huiyuanhui.model.ShopMemberMap;

import org.springframework.stereotype.Repository;

@Repository("shopMemberMapMapper")
public interface ShopMemberMapMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopMemberMap record);

    int insertSelective(ShopMemberMap record);

    ShopMemberMap selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopMemberMap record);

    int updateByPrimaryKey(ShopMemberMap record);
}