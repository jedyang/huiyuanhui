package com.yunsheng.huiyuanhui.model.front;

import com.yunsheng.huiyuanhui.model.Shop;

/**
 * 供前台页面使用的模型
 */
public class ShopInfo extends Shop {
    private String openId;

    private Integer userId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ShopInfo{" +
                "openId='" + openId + '\'' +
                '}';
    }
}
