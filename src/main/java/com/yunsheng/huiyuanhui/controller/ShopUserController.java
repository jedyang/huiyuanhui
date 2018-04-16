package com.yunsheng.huiyuanhui.controller;

import com.yunsheng.huiyuanhui.model.Shop;
import com.yunsheng.huiyuanhui.model.ShopUser;
import com.yunsheng.huiyuanhui.service.ShopService;
import com.yunsheng.huiyuanhui.service.ShopUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/shopUser")
public class ShopUserController {

    @Autowired
    private ShopUserService shopUserService;


    @PostMapping("/add")
    @ResponseBody
    public Map addMember(@RequestBody ShopUser shopUser) {
        Map<String, Boolean> result = new HashMap<>();
        int insertShopUser = shopUserService.addShopUser(shopUser);
        result.put("success", insertShopUser == 1 ? true : false);
        return result;
    }
}
