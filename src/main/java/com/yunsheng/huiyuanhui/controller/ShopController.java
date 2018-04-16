package com.yunsheng.huiyuanhui.controller;

import com.yunsheng.huiyuanhui.model.Shop;
import com.yunsheng.huiyuanhui.model.ShopUser;
import com.yunsheng.huiyuanhui.model.front.ShopInfo;
import com.yunsheng.huiyuanhui.service.ShopService;
import com.yunsheng.huiyuanhui.service.ShopUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopUserService shopUserService;

    @PostMapping("/add")
    @ResponseBody
    public Map addShop(@RequestBody ShopInfo shopInfo) {
        logger.info(shopInfo.toString());
        Map<String, Boolean> result = new HashMap<>();

        String openId = shopInfo.getOpenId();
        // 存店铺关联用户信息
        // 先查询该微信用户是否已经注册过
        // 正常到这步的应该已经注册过了
        ShopUser userByOpenId = shopUserService.findByOpenId(openId);

        ShopUser shopUser = new ShopUser();
        shopUser.setOpenId(openId);
        int addResult = shopUserService.addShopUser(shopUser);

        if (addResult == 1){
            ShopUser param = new ShopUser();
            param.setOpenId(openId);
            List<ShopUser> shopUser1 = shopUserService.findBySelect(param);
            if (userByOpenId != null){
                // 存店铺信息
                shopInfo.setShopUserId(userByOpenId.getUserId());
                boolean insertShop = shopService.insertShop(shopInfo);
            }
        }


        result.put("success", true);
        return result;
    }

}
