package com.yunsheng.huiyuanhui.controller;

import com.yunsheng.huiyuanhui.model.Shop;
import com.yunsheng.huiyuanhui.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;


    @PostMapping("/add")
    @ResponseBody
    public Map addMember(@RequestBody Shop shop) {
        Map<String, Boolean> result = new HashMap<>();
        boolean insertShop = shopService.insertShop(shop);
        result.put("success", insertShop);
        return result;
    }

}
