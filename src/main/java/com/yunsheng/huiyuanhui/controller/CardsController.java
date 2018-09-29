package com.yunsheng.huiyuanhui.controller;

import com.yunsheng.huiyuanhui.dto.MyResult;
import com.yunsheng.huiyuanhui.model.ConsumeLog;
import com.yunsheng.huiyuanhui.model.HyCards;
import com.yunsheng.huiyuanhui.model.Member;
import com.yunsheng.huiyuanhui.model.Pay;
import com.yunsheng.huiyuanhui.model.ShopMemberMap;
import com.yunsheng.huiyuanhui.service.CardService;
import com.yunsheng.huiyuanhui.service.ShopMemberMapService;
import com.yunsheng.huiyuanhui.util.PageRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cards")
public class CardsController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private CardService cardService;

    @Autowired
    private ShopMemberMapService shopMemberMapService;

    /**
     * 查询我的所有会员卡
     */
    @RequestMapping("/myCards")
    public MyResult getAllMember(@RequestParam(name = "memberId") Integer memberId) {
        MyResult result = new MyResult();

        try {
            ShopMemberMap shopMemberMap = new ShopMemberMap();
            shopMemberMap.setMemberId(memberId);
            List<ShopMemberMap> record = shopMemberMapService.findRecord(shopMemberMap);

            result.setResult(record);
            result.setSuccess(true);
            result.setStatus(0);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("查询会员卡失败");
            result.setStatus(1);
        }


        return result;
    }

    /**
     * 支付
     */
    @PostMapping("/pay")
    public MyResult<ConsumeLog> pay(@RequestBody Pay pay) {
        MyResult<ConsumeLog> result = new MyResult<>();

        try {
            ConsumeLog payResult = shopMemberMapService.pay(pay);

            if (payResult != null) {
                result.setResult(payResult);
                result.setSuccess(true);
                result.setStatus(0);
            } else {
                result.setSuccess(false);
                result.setMsg("支付失败");
                result.setStatus(1);
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("支付失败");
            result.setStatus(1);
        }


        return result;
    }

    /**
     * 查询我的所有会员卡
     */
    @RequestMapping("/cardInfo")
    public MyResult getCardInfo(@RequestParam(name = "cardId") Integer cardId) {
        MyResult result = new MyResult();

        try {
            ShopMemberMap record = shopMemberMapService.findByPK(cardId);

            result.setResult(record);
            result.setSuccess(true);
            result.setStatus(0);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("查询会员卡失败");
            result.setStatus(1);
        }


        return result;
    }


}
