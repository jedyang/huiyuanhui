package com.yunsheng.huiyuanhui.controller;

import com.alibaba.fastjson.JSONObject;
import com.yunsheng.huiyuanhui.dto.MyResult;


import com.yunsheng.huiyuanhui.model.Member;
import com.yunsheng.huiyuanhui.service.MemberService;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;


    private String appId = "wxdb03a70e4c13e84b";

    private String secret = "b6e47be14d284856617db379aa16be4d";

    @RequestMapping("/onLogin")
    @ResponseBody
    public MyResult<HashMap> onLogin(String code) {
        MyResult result = new MyResult();
        HashMap<String, String> auth = new HashMap<>();

        // https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        StringBuilder urlPath = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session"); // 微信提供的API，这里最好也放在配置文件
        urlPath.append(String.format("?appid=%s", appId));
        urlPath.append(String.format("&secret=%s", secret));
        urlPath.append(String.format("&js_code=%s", code));
        urlPath.append(String.format("&grant_type=%s", "authorization_code")); // 固定值
        String data = sendPost(urlPath.toString());
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

    @RequestMapping("/{userId}/allMember")
    @ResponseBody
    public MyResult<List<Member>> getAllMember(@PathVariable String userId, HttpServletRequest request) {
        MyResult result = new MyResult();

        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();

        List<Member> membersResult = new ArrayList<>();
        if (StringUtils.isBlank(userId)) {
            return result;
        }

        // mock
        Member a = getOneMock("aa");
        membersResult.add(a);
        Member b = getOneMock("bb");
        membersResult.add(b);
        Member c = getOneMock("cc");
        membersResult.add(c);
        Member d = getOneMock("dd");
        membersResult.add(d);

//        memberService.selectAllMember(userId);

        result.setData(membersResult);
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

    @PostMapping("/add")
    @ResponseBody
    public Map addMember(@RequestBody Member member) {
        Map<String, Boolean> result = new HashMap<>();
        int record = memberService.insertRecord(member);
        if (record == 1) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }

        return result;
    }


    public static String sendPost(String url) {
        String result = "";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);

        try {
            HttpResponse httpResponse = client.execute(httpPost);
            HttpEntity responseEntity = httpResponse.getEntity();
            result = EntityUtils.toString(responseEntity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
