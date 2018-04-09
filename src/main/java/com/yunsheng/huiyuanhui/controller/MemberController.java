package com.yunsheng.huiyuanhui.controller;

import com.yunsheng.huiyuanhui.dto.MyResult;


import com.yunsheng.huiyuanhui.model.Member;
import com.yunsheng.huiyuanhui.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/{userId}/allMember/{phone}")
    @ResponseBody
    public MyResult<List<Member>> getAllMember(@PathVariable String userId, @PathVariable String phone) {
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

}
