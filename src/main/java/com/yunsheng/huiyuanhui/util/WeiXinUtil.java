package com.yunsheng.huiyuanhui.util;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class WeiXinUtil {
    private static AccessTokenKeep keep = new AccessTokenKeep();

    public static String getAccessToken() {
        // {"access_token":"8_rXLd8cv-Zsv6fltl7-dVGLv5JoE3jjM2DazBdEmez2aMi_7KY8YZGaYceV9IGtxRL4YPmCwiS3uoH8nSYnnb5Gs-DYiQawxHgCUhdpoujs6WTtlMG4RgDKB0gtFwvM5if7xxA83x9ubjN8K9UWHeAAACEP","expires_in":7200}
        String accessToken = keep.getAccessToken();
        if (StringUtils.isBlank(accessToken)) {
            accessToken = getAndUpdate();

        } else {
            long getTime = keep.getGetTime();
            long now = System.currentTimeMillis();
            long expires = keep.getExpires();
            // 是否已经超时
            if (now - getTime >= expires) {
                accessToken = getAndUpdate();
            }
        }

        return accessToken;
    }

    private static String getAndUpdate() {
        String accessToken = HttpUtil.sendGet(Constants.ACCESS_TOKEN_URL);
        JSONObject jsonObject = JSONObject.parseObject(accessToken);
        String accessTokenValue = jsonObject.getString("access_token");
        String expiresTime = jsonObject.getString("expires_in");
        keep.setAccessToken(accessTokenValue);
        keep.setExpires(Long.parseLong(expiresTime) * 1000);
        keep.setGetTime(System.currentTimeMillis());

        return accessTokenValue;
    }

    // 生成小程序码
    public static String getQRCode(String scene, String page, String prePath) {
        String token = getAccessToken();
        String url = Constants.WX_QRCODE_URL + "?access_token=" + token;

        JSONObject params = new JSONObject();
        params.put("scene", scene);
        params.put("page", page);// TODO上线后换成index

        String postResult = HttpUtil.sendPost4QrCode(url, params, prePath);
        return postResult;
    }


}
