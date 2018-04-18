package com.yunsheng.huiyuanhui.controller;

import com.alibaba.fastjson.JSONObject;
import com.yunsheng.huiyuanhui.dto.MyResult;
import com.yunsheng.huiyuanhui.model.Member;
import com.yunsheng.huiyuanhui.model.ShopMemberMap;
import com.yunsheng.huiyuanhui.service.MemberService;
import com.yunsheng.huiyuanhui.service.ShopMemberMapService;
import com.yunsheng.huiyuanhui.util.Constants;
import com.yunsheng.huiyuanhui.util.HttpUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private ShopMemberMapService shopMemberMapService;


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
        String sessionkey = (String) JSONObject.parseObject(data).get("session_key");
        auth.put("openId", openId);
        auth.put("loginSessionKey", openId);
        // 将sessionkey对应生成第三方session，存到redis。用于交互

        result.setSuccess(true);
        result.setData(auth);
        return result;
    }

    /**
     * 查询该店铺下所有会员
     */
    @RequestMapping("/{shopId}/allMember")
    @ResponseBody
    public MyResult<List<Member>> getAllMember(@PathVariable String shopId) {
        MyResult result = new MyResult();

        if (StringUtils.isBlank(shopId)) {
            return result;
        }

        List<Member> members = memberService.queryAllMembersOfShop(Integer.parseInt(shopId));

        result.setData(members);
        result.setSuccess(true);

        return result;
    }

    private Member getOneMock(String dd) {
        Member one = new Member();
        one.setName(dd);
        one.setMemberId(Integer.valueOf(new Random().nextInt()));
        one.setPhone("1234568");
        return one;
    }

    @RequestMapping("/{userId}/detail")
    @ResponseBody
    public Member getMember(@PathVariable String userId) {
        Member result = new Member();
        if (StringUtils.isBlank(userId)) {
            return result;
        }

        // mock
        Member a = getOneMock("aa");

        return a;
    }

    @RequestMapping("/{userId}/status")
    @ResponseBody
    public String getMemberStaus(@PathVariable String userId) {

        return "ok";
    }

    /**
     * 新增用户可能通过商家的二维码进来 或者推广的平台二维码
     */
    @PostMapping("/add")
    @ResponseBody
    public Map addMember(@RequestBody Member member) {
        Map<String, Boolean> result = new HashMap<>();
        // 一个用户可以注册多个店铺的会员
        // 先查询是否以注册过用户
        try {
            Member memberByOpenId = memberService.findByOpenId(member.getOpenId());

            if (null == memberByOpenId) {
                memberService.insertRecord(member);
                memberByOpenId = memberService.findByOpenId(member.getOpenId());
            }

            // 存储对应关系
            Integer shopId = member.getShopId();
            if (null != shopId) {
                // 先查询店铺是否存在
                // 非空说明是扫店铺码过来的
                ShopMemberMap shopMemberMap = new ShopMemberMap();
                shopMemberMap.setMemberId(memberByOpenId.getMemberId());
                shopMemberMap.setShopId(shopId);
                shopMemberMapService.insertRecord(shopMemberMap);
            }
            result.put("success", true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.put("success", false);
//            result.put("msg", "注册失败，请重试");
        }


        return result;
    }


}
