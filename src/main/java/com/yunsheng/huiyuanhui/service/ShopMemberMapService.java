package com.yunsheng.huiyuanhui.service;

import com.yunsheng.huiyuanhui.model.ShopMemberMap;

import java.util.List;

public interface ShopMemberMapService {

    int insertRecord(ShopMemberMap shopMemberMap);

    List<ShopMemberMap> findRecord(ShopMemberMap shopMemberMap);

    int updateRecord(ShopMemberMap shopMemberMap);
}
