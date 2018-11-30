package com.yunsheng.huiyuanhui.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class WeiXinUtilTest {
    @Test
    public void getAccessToken() throws Exception {
        String accessToken =  WeiXinUtil.getAccessToken();
        System.out.println(accessToken);
    }

    @Test
    public void getQrCode() throws Exception {
        String accessToken =  WeiXinUtil.getQRCode("123", "", "invite");
        System.out.println(accessToken);
    }

}