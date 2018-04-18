package com.yunsheng.huiyuanhui.mapper;

import com.yunsheng.huiyuanhui.model.ShopUserMap;
import org.springframework.stereotype.Repository;

@Repository("shopUserMapMapper")
public interface ShopUserMapMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopUserMap record);

    int insertSelective(ShopUserMap record);

    ShopUserMap selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopUserMap record);

    int updateByPrimaryKey(ShopUserMap record);
}