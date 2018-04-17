package com.yunsheng.huiyuanhui.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class WeiXinUtilTest {
    @Test
    public void getAccessToken() throws Exception {
        String accessToken = new WeiXinUtil().getAccessToken();
        System.out.println(accessToken);
    }

}