package com.yunsheng.huiyuanhui.controller;

import com.alibaba.fastjson.JSONObject;
import com.yunsheng.huiyuanhui.dto.MyResult;
import com.yunsheng.huiyuanhui.model.Member;
import com.yunsheng.huiyuanhui.model.ShopUser;
import com.yunsheng.huiyuanhui.service.ConsumeLogService;
import com.yunsheng.huiyuanhui.service.MemberService;
import com.yunsheng.huiyuanhui.service.ShopMemberMapService;
import com.yunsheng.huiyuanhui.service.ShopUserService;
import com.yunsheng.huiyuanhui.util.Constants;
import com.yunsheng.huiyuanhui.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * app用户处理controller
 */
@RestController
@RequestMapping("/api/appUser")
public class AppUserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private ShopMemberMapService shopMemberMapService;

    @Autowired
    private ConsumeLogService consumeLogService;

    @Autowired
    private ShopUserService shopUserService;

    @RequestMapping("/onLogin")
    @ResponseBody
    public MyResult<HashMap> onLogin(String code) {
        MyResult result = new MyResult();
        HashMap<String, String> auth = new HashMap<>();

        // https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        StringBuilder urlPath = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session"); // 微信提供的API，这里最好也放在配置文件
        urlPath.append(String.format("?appid=%s", Constants.APPID));
        urlPath.append(String.format("&secret=%s", Constants.APPSECRET));
        urlPath.append(String.format("&js_code=%s", code));
        urlPath.append(String.format("&grant_type=%s", "authorization_code")); // 固定值
        String data = HttpUtil.sendGet(urlPath.toString());
        System.out.println("请求结果：" + data);
//        {"session_key":"c1BSzC0xC2VFUvh3pwI9hg==","openid":"otzPb4iLljrVcrwYU0lIDcwBy0vc"}
        String openId = (String) JSONObject.parseObject(data).get("openid");
        // 会话密钥session_key 是对用户数据进行加密签名的密钥。为了应用自身的数据安全，开发者服务器不应该把会话密钥下发到小程序，也不应该对外提供这个密钥。
//        String sessionkey = (String) JSONObject.parseObject(data).get("session_key");
        auth.put("openId", openId);
//        auth.put("loginSessionKey", openId);

        // 查询memberId
        // 先去店主表查
        ShopUser shopUser = shopUserService.findByOpenId(openId);
        if (null != shopUser) {
            auth.put("isShopUser", "true");
            auth.put("shopUserId", shopUser.getUserId().toString());
        } else {
            auth.put("isShopUser", "false");
        }
        // 去会员表查
        Member byOpenId = memberService.findByOpenId(openId);
        if (null != byOpenId) {
            auth.put("isMember", "true");
            auth.put("memberId", byOpenId.getMemberId().toString());
        } else {
            auth.put("isMember", "false");
        }

        result.setSuccess(true);
        result.setResult(auth);
        return result;
    }
}
