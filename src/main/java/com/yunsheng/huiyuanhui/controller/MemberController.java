package com.yunsheng.huiyuanhui.controller;

import com.alibaba.fastjson.JSONObject;
import com.yunsheng.huiyuanhui.dto.MyResult;
import com.yunsheng.huiyuanhui.model.ConsumeLog;
import com.yunsheng.huiyuanhui.model.Member;
import com.yunsheng.huiyuanhui.model.ShopMemberMap;
import com.yunsheng.huiyuanhui.model.ShopUser;
import com.yunsheng.huiyuanhui.model.front.ConsumeInfo;
import com.yunsheng.huiyuanhui.model.front.ReChargeInfo;
import com.yunsheng.huiyuanhui.service.ConsumeLogService;
import com.yunsheng.huiyuanhui.service.MemberService;
import com.yunsheng.huiyuanhui.service.ShopMemberMapService;
import com.yunsheng.huiyuanhui.service.ShopUserService;
import com.yunsheng.huiyuanhui.util.Constants;
import com.yunsheng.huiyuanhui.util.HttpUtil;
import com.yunsheng.huiyuanhui.util.PageRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员controller
 */

@RestController
@RequestMapping("/api/member")
public class MemberController {
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
        String sessionkey = (String) JSONObject.parseObject(data).get("session_key");
        auth.put("openId", openId);
        auth.put("loginSessionKey", openId);
        // 将sessionkey对应生成第三方session，存到redis。用于交互

        // 查询memberId
        // 先去店主表查
        ShopUser shopUser = shopUserService.findByOpenId(openId);
        if (null != shopUser) {
            auth.put("shopUserId", shopUser.getUserId().toString());
        }
        // 去会员表查
        Member byOpenId = memberService.findByOpenId(openId);
        if (null != byOpenId) {
            auth.put("memberId", byOpenId.getMemberId().toString());
        }

        result.setSuccess(true);
        result.setResult(auth);
        return result;
    }

    /**
     * 查询该店铺下所有会员
     */
    @RequestMapping("/allMember")
    @ResponseBody
    public MyResult<List<ShopMemberMap>> getAllMember(@RequestParam(name = "shopId") String shopId,
                                                      @RequestParam(name = "words", required = false) String words) {
        MyResult result = new MyResult();

        if (StringUtils.isBlank(shopId)) {
            return result;
        }

        ShopMemberMap shopMemberMap = new ShopMemberMap();
        shopMemberMap.setShopId(Integer.parseInt(shopId));
        List<ShopMemberMap> records = shopMemberMapService.findRecord(shopMemberMap);

        List<Member> record = new ArrayList<>();
        if (null != records && !records.isEmpty()) {
            for (ShopMemberMap i : records) {
                Member byMemberId = memberService.findByMemberId(i.getMemberId().toString());
                record.add(byMemberId);
            }
        }
        result.setResult(record);
        result.setSuccess(true);

        return result;
    }


    @GetMapping("/detail")
    @ResponseBody
    public MyResult getMember(@RequestParam(name = "memberId") String memberId) {
        MyResult result = new MyResult();
        if (StringUtils.isBlank(memberId)) {
            return result;
        }

        Member a = memberService.findByMemberId(memberId);
        result.setSuccess(true);
        result.setResult(a);
        return result;
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


    /**
     * 充值
     */
    @PostMapping("/reCharge")
    @ResponseBody
    public MyResult modifyMoney(@RequestBody ReChargeInfo reChargeInfo) {
        MyResult myResult = new MyResult();

        try {

            Member memberById = memberService.findByMemberId(reChargeInfo.getMemberId().toString());

            if (null == memberById) {
                myResult.setSuccess(false);
                myResult.setMsg("该会员未注册");
                return myResult;
            }

            ShopMemberMap queryParam = new ShopMemberMap();
            queryParam.setShopId(reChargeInfo.getShopId());
            queryParam.setMemberId(reChargeInfo.getMemberId());
            List<ShopMemberMap> shopMemberMaps = shopMemberMapService.findRecord(queryParam);
            if (null == shopMemberMaps || shopMemberMaps.size() == 0) {
                myResult.setSuccess(false);
                myResult.setMsg("该会员不属于该店铺");
                return myResult;
            }

            ShopMemberMap shopMemberMap = shopMemberMaps.get(0);
            // 充值
            BigDecimal oldMoney = new BigDecimal(shopMemberMap.getMoney());
            BigDecimal money = new BigDecimal(reChargeInfo.getMoney());
            BigDecimal newMoney = oldMoney.add(money);
            shopMemberMap.setMoney(newMoney.doubleValue());

            Integer oldPoint = shopMemberMap.getPoint();
            Integer point = reChargeInfo.getPoints();
            Integer newPoint = oldPoint + point;
            shopMemberMap.setPoint(newPoint);


            int updateRecord = shopMemberMapService.updateRecord(shopMemberMap);
            if (updateRecord == 0) {
                myResult.setSuccess(false);
                myResult.setMsg("充值失败");
                return myResult;
            }

            // 记录消费历史
            ConsumeLog log = new ConsumeLog();
            log.setMemberId(reChargeInfo.getMemberId());
            log.setShopId(reChargeInfo.getShopId());
            log.setMoney(money.doubleValue());
            log.setOldMoney(oldMoney.doubleValue());
            log.setNewMoney(newMoney.doubleValue());
            log.setPoints(point);
            log.setOldPoints(oldPoint);
            log.setNewPoints(newPoint);
            log.setType(2);
            int i = consumeLogService.insertRecord(log);
            if (i != 1) {
                logger.error("insert consume log error:" + log.toString());
            }

            myResult.setSuccess(true);
            myResult.setMsg("充值成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            myResult.setSuccess(false);
            myResult.setMsg("充值失败，请重试");
        }


        return myResult;
    }

    /**
     * 消费
     */
    @PostMapping("/consume")
    @ResponseBody
    public MyResult consume(@RequestBody ConsumeInfo consumeInfo) {
        MyResult myResult = new MyResult();
        try {

            Member memberById = memberService.findByMemberId(consumeInfo.getMemberId().toString());
            if (null == memberById) {
                myResult.setSuccess(false);
                myResult.setMsg("该会员未注册");
                return myResult;
            }

            ShopMemberMap queryParam = new ShopMemberMap();
            queryParam.setShopId(consumeInfo.getShopId());
            queryParam.setMemberId(consumeInfo.getMemberId());
            List<ShopMemberMap> shopMemberMaps = shopMemberMapService.findRecord(queryParam);
            if (null == shopMemberMaps || shopMemberMaps.size() == 0) {
                myResult.setSuccess(false);
                myResult.setMsg("该会员不属于该店铺");
                return myResult;
            }

            ShopMemberMap shopMemberMap = shopMemberMaps.get(0);
            // 消费
            BigDecimal oldMoney = new BigDecimal(shopMemberMap.getMoney());
            BigDecimal money = new BigDecimal(consumeInfo.getMoney());
            BigDecimal subMoney = oldMoney.subtract(money);
            if (subMoney.compareTo(new BigDecimal(0)) < 0) {
                myResult.setSuccess(false);
                myResult.setMsg("余额不足，请充值");
                return myResult;
            }
            shopMemberMap.setMoney(subMoney.doubleValue());

            Integer oldPoint = shopMemberMap.getPoint();
            Integer point = consumeInfo.getPoints();
            Integer subPoint = oldPoint - point;
            if (subPoint < 0) {
                myResult.setSuccess(false);
                myResult.setMsg("积分不足，请修改");
                return myResult;
            }
            shopMemberMap.setPoint(subPoint);


            int updateRecord = shopMemberMapService.updateRecord(shopMemberMap);
            if (updateRecord == 0) {
                myResult.setSuccess(false);
                myResult.setMsg("消费失败，请重试");
                return myResult;
            }

            // 记录消费历史
            ConsumeLog log = new ConsumeLog();
            log.setMemberId(consumeInfo.getMemberId());
            log.setShopId(consumeInfo.getShopId());
            log.setMoney(money.doubleValue());
            log.setOldMoney(oldMoney.doubleValue());
            log.setNewMoney(subMoney.doubleValue());
            log.setPoints(point);
            log.setOldPoints(oldPoint);
            log.setNewPoints(subPoint);
            log.setType(1);
            int i = consumeLogService.insertRecord(log);
            if (i != 1) {
                logger.error("insert consume log error:" + log.toString());
            }

            myResult.setSuccess(true);
            myResult.setMsg("消费成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            myResult.setSuccess(false);
            myResult.setMsg("消费失败，请重试");
        }


        return myResult;
    }

    /**
     * 消费和充值记录查询
     */
    @GetMapping("/consumeLogs")
    @ResponseBody
    public MyResult<List<ConsumeLog>> getConsumeLog(@RequestParam String memberId,
                                                    @RequestParam String shopId,
                                                    @RequestParam(value = "sortBy", required = false) String sortBy,
                                                    @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {

        MyResult<List<ConsumeLog>> result = new MyResult<>();

        PageRequest<ConsumeLog> pageRequest = new PageRequest<>(page, pageSize);

        ConsumeLog consumeLog = new ConsumeLog();
        consumeLog.setMemberId(Integer.parseInt(memberId));
        consumeLog.setShopId(Integer.parseInt(shopId));
        PageRequest<ConsumeLog> recordsByPage = consumeLogService.queryRecordsByPage(consumeLog, pageRequest);

        List<ConsumeLog> dataList = recordsByPage.getDataList();

        result.setResult(dataList);
        result.setSuccess(true);

        return result;
    }
}
