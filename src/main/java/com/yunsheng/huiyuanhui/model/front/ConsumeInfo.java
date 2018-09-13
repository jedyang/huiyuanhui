package com.yunsheng.huiyuanhui.model.front;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 消费信息
 */
public class ConsumeInfo {
    private Integer cardId;

    private Integer memberId;

    private Integer shopId;

    private Double money;

    private Integer points;

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
