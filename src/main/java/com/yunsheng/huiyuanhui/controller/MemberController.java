package com.yunsheng.huiyuanhui.controller;

import com.yunsheng.huiyuanhui.dto.Member;


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
    public List<Member> getAllMember(@PathVariable String userId) {
        List<Member> result = new ArrayList<>();
        if (StringUtils.isBlank(userId)) {
            return result;
        }

        // mock
        Member a = getOneMock("aa");
        result.add(a);
        Member b = getOneMock("bb");
        result.add(b);
        Member c = getOneMock("cc");
        result.add(c);
        Member d = getOneMock("dd");
        result.add(d);

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

}
