package com.yunsheng.huiyuanhui.controller;

import com.yunsheng.huiyuanhui.dto.Member;
import com.yunsheng.huiyuanhui.dto.MyResult;


import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    @RequestMapping("/{userId}/allMember")
    @ResponseBody
    public MyResult<List<Member>> getAllMember(@PathVariable String userId) {
        MyResult result = new MyResult();

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

        result.setData(membersResult);
        result.setSuccess(true);

        return result;
    }

    private Member getOneMock(String dd) {
        Member one = new Member();
        one.setName(dd);
        one.setAge(23);
        one.setMemId(String.valueOf(new Random().nextInt()));
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

    @RequestMapping("/add")
    @ResponseBody
    public String addMember(Member member) {

        System.out.println(member);

        return "ok";
    }

}
