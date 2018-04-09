package com.yunsheng.huiyuanhui.controller;

import com.yunsheng.huiyuanhui.dto.MyResult;
import com.yunsheng.huiyuanhui.model.Member;
import com.yunsheng.huiyuanhui.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    @Autowired
    private MemberService memberService;


    @PostMapping("/add")
    @ResponseBody
    public Map addMember(@RequestBody Shop shop) {
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
