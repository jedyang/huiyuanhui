package com.yunsheng.huiyuanhui.controller;

import com.yunsheng.huiyuanhui.dto.MyResult;
import com.yunsheng.huiyuanhui.model.Shop;
import com.yunsheng.huiyuanhui.model.ShopUser;
import com.yunsheng.huiyuanhui.model.ShopUserMap;
import com.yunsheng.huiyuanhui.model.front.ShopInfo;
import com.yunsheng.huiyuanhui.service.ShopService;
import com.yunsheng.huiyuanhui.service.ShopUserMapService;
import com.yunsheng.huiyuanhui.service.ShopUserService;
import com.yunsheng.huiyuanhui.util.Constants;
import com.yunsheng.huiyuanhui.util.QiniuUtil;
import com.yunsheng.huiyuanhui.util.WeiXinUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
    private Shop theShop;


    @PostMapping("/add")
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
            List<String> pics = shopInfo.getPics();
            if (null != pics && pics.size() > 0) {
                StringBuilder picUrlsBuilder = new StringBuilder();
                for (String url : pics) {
                    picUrlsBuilder.append(url).append(";");
                }
                shopInfo.setPicUrls(picUrlsBuilder.toString());
            }
            // 存店铺信息
            Integer insertResult = shopService.insertShop(shopInfo);

            Integer shopId = shopInfo.getShopId();
            // 为店铺生成二维码
            String qrCodeUrl = WeiXinUtil.getQRCode(shopId.toString(), "", "invite:");//TODO
            if (!"err".equalsIgnoreCase(qrCodeUrl)) {
                shopInfo.setInvitePicUrl(qrCodeUrl);
            }

            // 存店铺和user的对应关系，支持多对多
            ShopUserMap shopUserMap = new ShopUserMap();
            shopUserMap.setShopid(shopId);
            shopUserMap.setUserid(userByOpenId.getUserId());
            shopUserMapService.insertRecord(shopUserMap);

        }

        result.setSuccess(true);
        return result;
    }

    @PostMapping("/modify")
    public MyResult modifyShop(@RequestBody ShopInfo shopInfo) {
        logger.info(shopInfo.toString());
        MyResult result = new MyResult();

        try {
            List<String> pics = shopInfo.getPics();
            if (CollectionUtils.isNotEmpty(pics)) {
                StringBuilder sb = new StringBuilder();
                for (String picUrl : pics) {
                    sb.append(picUrl).append(";");
                }
                shopInfo.setPicUrls(sb.toString());
            }
            shopService.modifyShop(shopInfo);

            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 获取店铺信息
     */
    @GetMapping("/myShops")
    public MyResult<List> getShopInfo(Integer userId) {
        MyResult<List> result = new MyResult<>();

        List<Shop> allShopsOfUser = shopService.findAllShopsOfUser(userId);

        result.setResult(allShopsOfUser);
        result.setSuccess(true);
        result.setMsg("查询店铺信息成功");
        return result;

    }

    /**
     * 获取店铺信息
     */
    @GetMapping("/queryShop")
    public MyResult<List> getShopInfoByPk(Integer shopId) {
        MyResult result = new MyResult();

        Shop theShop = shopService.findShopByPk(shopId);
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShopId(theShop.getShopId());
        shopInfo.setShopName(theShop.getShopName());
        shopInfo.setShopAddr(theShop.getShopAddr());
        shopInfo.setShopDesc(theShop.getShopDesc());
        String picUrls = theShop.getPicUrls();
        if (StringUtils.isNoneBlank(picUrls)) {
            shopInfo.setPics(Arrays.asList(theShop.getPicUrls().split(";")));
        }
        shopInfo.setMainPic(theShop.getMainPic());
        result.setResult(shopInfo);
        result.setSuccess(true);
        result.setMsg("查询店铺信息成功");
        return result;

    }

    @GetMapping("/getAccessToken")
    public MyResult<String> getAccessToken() {
        logger.info("===getAccessToken===");
        MyResult<String> result = new MyResult<>();
        String accessToken = null;
        try {
            accessToken = WeiXinUtil.getAccessToken();
            result.setSuccess(true);
            result.setResult(accessToken);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    @GetMapping("/getQrCode")
    public String getQrCode(Integer shopId) {
        logger.info("===getAccessToken===");
        Shop shopByPk = shopService.findShopByPk(shopId);
        String qrCode = shopByPk.getInvitePicUrl();

        return Constants.URL_DOMAIN + qrCode;
    }

    /**
     * 获取支付二维码
     *
     * @return
     */
    @GetMapping("/getPayQrCode")
    public MyResult<String> getPayQrCode(String scene) {
        logger.info("===getPayQrCode===");
        MyResult<String> result = new MyResult<>();
        String qrCode;
        try {
            qrCode = WeiXinUtil.getQRCode(scene, "", "pay:");//TODO
            result.setSuccess(true);
            result.setResult(Constants.URL_DOMAIN + qrCode);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    /**
     * 给店铺添加用户 一个店铺可以有多个用户管理
     */
    @PostMapping("/addShopUser")
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

    @GetMapping("/getQiniuToken")
    public String getQiniuToken() {
        logger.info("===getQiniuToken===");
        String accessToken = QiniuUtil.getQiniuToken();
        return accessToken;
    }

    // 获取流水信息
    @GetMapping("/getConsumeList")
    public MyResult getConsumeList() {
        logger.info("===getQiniuToken===");
        MyResult result = new MyResult();
        String accessToken = QiniuUtil.getQiniuToken();


        return result;
    }
}
