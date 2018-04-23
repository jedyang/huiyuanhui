package com.yunsheng.huiyuanhui.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class ConsumeLog {
    private Integer logId;

    private Integer memberId;

    private Integer shopId;

    private Double money;

    private Double oldMoney;

    private Double newMoney;

    private Integer points;

    private Integer oldPoints;

    private Integer newPoints;

    private Integer type;

    private Date logTime;

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


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}