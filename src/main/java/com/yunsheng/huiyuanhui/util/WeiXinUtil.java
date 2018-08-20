package com.yunsheng.huiyuanhui.util;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class WeiXinUtil {
    private AccessTokenKeep keep = new AccessTokenKeep();

    public  String getAccessToken() {
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

    private  String getAndUpdate() {
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
    public String getQRCode() {
        String result = "";
        String token = this.getAccessToken();
        String url = Constants.WX_QRCODE_URL + "?access_token=" + token;
        String params = "{'path':'pages/index/index'}";
        HttpUtil.sendPost(url, params);
        return result;
    }

}
