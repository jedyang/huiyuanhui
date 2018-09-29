package com.yunsheng.huiyuanhui.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsumeLog {
    private Integer logId;

    private Integer cardId;
    private Integer memberId;

    private Integer shopId;

    private Double money;

    private Double oldMoney;

    private Double newMoney;

    private Integer points;

    private Integer oldPoints;

    private Integer newPoints;

    //    类型：1，消费；2，充值；3，奖励
    private Integer type;

    // 前台使用
    private String typeName;

    private Date logTime;

    // 前台使用
    private String logTimeStr;

    public String getLogTimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(this.logTime);
    }

    public void setLogTimeStr(String logTimeStr) {
        this.logTimeStr = logTimeStr;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
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

    public Double getOldMoney() {
        return oldMoney;
    }

    public void setOldMoney(Double oldMoney) {
        this.oldMoney = oldMoney;
    }

    public Double getNewMoney() {
        return newMoney;
    }

    public void setNewMoney(Double newMoney) {
        this.newMoney = newMoney;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getOldPoints() {
        return oldPoints;
    }

    public void setOldPoints(Integer oldPoints) {
        this.oldPoints = oldPoints;
    }

    public Integer getNewPoints() {
        return newPoints;
    }

    public void setNewPoints(Integer newPoints) {
        this.newPoints = newPoints;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    // 类型：1，消费；2，充值；3，奖励
    public String getTypeName() {
        String name = "";
        switch (this.getType()) {
            case 1:
                name = "消费";
                break;
            case 2:
                name = "充值";
                break;
            case 3:
                name = "奖励";
                break;
        }
        return name;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}