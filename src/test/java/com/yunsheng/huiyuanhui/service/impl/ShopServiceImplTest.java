package com.yunsheng.huiyuanhui.service.impl;

import com.yunsheng.huiyuanhui.HuiyuanhuiApplicationTests;
import com.yunsheng.huiyuanhui.model.Shop;
import com.yunsheng.huiyuanhui.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ShopServiceImplTest extends HuiyuanhuiApplicationTests {
    @Autowired
    private ShopService shopService;
    @Test
    public void findNearShops() throws Exception {
        List<Shop> nearShops = shopService.findNearShops(100.00, 100.00);
        System.out.println(nearShops);
    }

}