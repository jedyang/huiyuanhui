package com.yunsheng.huiyuanhui.controller;

import com.yunsheng.huiyuanhui.dto.MyResult;
import com.yunsheng.huiyuanhui.model.Shop;
import com.yunsheng.huiyuanhui.model.ShopUser;
import com.yunsheng.huiyuanhui.model.ShopUserMap;
import com.yunsheng.huiyuanhui.model.front.ShopInfo;
import com.yunsheng.huiyuanhui.service.ShopService;
import com.yunsheng.huiyuanhui.service.ShopUserMapService;
import com.yunsheng.huiyuanhui.service.ShopUserService;
import com.yunsheng.huiyuanhui.util.WeiXinUtil;

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

    @Autowired
    private ShopUserMapService shopUserMapService;

    @Autowired
    private WeiXinUtil weiXinUtil;

    @PostMapping("/add")
    @ResponseBody
    public MyResult addShop(@RequestBody ShopInfo shopInfo) {
        logger.info(shopInfo.toString());
        MyResult result = new MyResult();

        String openId = shopInfo.getOpenId();
        // 存店铺关联用户信息
        // 先查询该微信用户是否已经注册过
        // 正常到这步的应该已经注册过了
        ShopUser userByOpenId = shopUserService.findByOpenId(openId);
        if (null == userByOpenId) {
            ShopUser shopUser = new ShopUser();
            shopUser.setOpenId(openId);
            shopUserService.addShopUser(shopUser);
            userByOpenId = shopUserService.findByOpenId(openId);
        }


        if (userByOpenId != null) {
            // 存店铺信息
            Integer insertResult = shopService.insertShop(shopInfo);

            Integer shopId = shopInfo.getShopId();
            // 存店铺和user的对应关系，支持多对多
            ShopUserMap shopUserMap = new ShopUserMap();
            shopUserMap.setShopid(shopId);
            shopUserMap.setUserid(userByOpenId.getUserId());
            shopUserMapService.insertRecord(shopUserMap);

            // 为店铺生成二维码

        }

        result.setSuccess(true);
        return result;
    }

    /**
     * 获取店铺信息
     */
    @GetMapping("/myShops")
    @ResponseBody
    public MyResult<List> getShopInfo(Integer userId) {
        MyResult result = new MyResult();

        List<Shop> allShopsOfUser = shopService.findAllShopsOfUser(userId);

        result.setResult(allShopsOfUser);
        result.setSuccess(true);
        result.setMsg("查询店铺信息成功");
        return result;

    }

    @GetMapping("/getAccessToken")
    @ResponseBody
    public String getAccessToken() {
        logger.info("===getAccessToken===");
        String accessToken = weiXinUtil.getAccessToken();
        return accessToken;
    }

    /**
     * 给店铺添加用户 一个店铺可以有多个用户管理
     */
    @PostMapping("/addShopUser")
    @ResponseBody
    public MyResult addShopUser(@RequestBody ShopInfo shopInfo) {
        logger.info(shopInfo.toString());
        MyResult result = new MyResult();


        Integer shopId = shopInfo.getShopId();
        // 存店铺和user的对应关系，支持多对多
        ShopUserMap shopUserMap = new ShopUserMap();
        shopUserMap.setShopid(shopId);
        shopUserMap.setUserid(shopInfo.getUserId());
        shopUserMapService.insertRecord(shopUserMap);


        result.setSuccess(true);
        return result;
    }
}
