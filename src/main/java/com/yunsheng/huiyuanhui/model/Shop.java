package com.yunsheng.huiyuanhui.model;

import java.util.List;

public class Shop {
    private Integer shopId;

    private String shopName;

    private String shopAddr;

    private String shopDesc;

    private String invitePicUrl;

    private String picUrls;

    private String mainPic;

    public String getMainPic() {
        return mainPic;
    }

    public void setMainPic(String mainPic) {
        this.mainPic = mainPic;
    }

    public String getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr == null ? null : shopAddr.trim();
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc == null ? null : shopDesc.trim();
    }

    public String getInvitePicUrl() {
        return invitePicUrl;
    }

    public void setInvitePicUrl(String invitePicUrl) {
        this.invitePicUrl = invitePicUrl;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", shopAddr='" + shopAddr + '\'' +
                ", shopDesc='" + shopDesc + '\'' +
                '}';
    }
}