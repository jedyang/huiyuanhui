package com.yunsheng.huiyuanhui.mapper;

import com.yunsheng.huiyuanhui.model.Shop;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("shopMapper")
public interface ShopMapper {
    int deleteByPrimaryKey(Integer shopId);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Integer shopId);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);

    List<Shop> selectAllShopsOfUser(Integer userId);
}