package com.yunsheng.huiyuanhui.model.front;

import com.yunsheng.huiyuanhui.model.Shop;

/**
 * 供前台页面使用的模型
 */
public class ShopInfo extends Shop {
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "ShopInfo{" +
                "openId='" + openId + '\'' +
                '}';
    }
}
