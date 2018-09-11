package com.yunsheng.huiyuanhui.controller;

import com.yunsheng.huiyuanhui.dto.MyResult;
import com.yunsheng.huiyuanhui.model.Member;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardsController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询该店铺下所有会员
     */
    @RequestMapping("/myCards")
    @ResponseBody
    public MyResult getAllMember(@RequestParam(name = "shopId") Integer shopId,
                                 @RequestParam(name = "words", required = false) String words) {
        MyResult result = new MyResult();

        List<Member> members = new ArrayList<>();
//        if (StringUtils.isBlank(words)) {
//            members = memberService.queryAllMembersOfShop(shopId);
//        } else {
//            members = memberService.queryAllMembersByShopAndKeyWord(shopId, words);
//        }

        result.setResult(members);
        result.setSuccess(true);

        return result;
    }


}
