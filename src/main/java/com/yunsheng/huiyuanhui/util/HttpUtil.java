package com.yunsheng.huiyuanhui.util;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * post请求（用于请求json格式的参数）
     */
    public static String sendPost4QrCode(String url, JSONObject params) {
        String result = "";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params.toJSONString(), charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {
            response = client.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                InputStream content = responseEntity.getContent();
                String scene = params.getString("scene");
                String filePath = saveToImgByInputStream(content, scene + ".jpeg");

                // 存到七牛云
                String qrCodeUrl = QiniuUtil.uploadFile2Qiniu(filePath, "invite:");
                return qrCodeUrl;
            } else {
                logger.error("请求返回:" + state + "(" + url + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * post请求(用于key-value格式的参数)
     */
    public static String doPost(String url, Map params) {

        BufferedReader in = null;
        try {
            // 定义HttpClient
            HttpClient client = HttpClients.createDefault();
            // 实例化HTTP方法
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));

            //设置参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Iterator iter = params.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String value = String.valueOf(params.get(name));
                nvps.add(new BasicNameValuePair(name, value));

                //System.out.println(name +"-"+value);
            }
            request.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));

            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {    //请求成功
                in = new BufferedReader(new InputStreamReader(response.getEntity()
                        .getContent(), "utf-8"));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }

                in.close();

                return sb.toString();
            } else {    //
                System.out.println("状态码：" + code);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String sendGet(String url) {
        String result = "";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse httpResponse = client.execute(httpGet);
            HttpEntity responseEntity = httpResponse.getEntity();
            result = EntityUtils.toString(responseEntity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
     * @param instreams 二进制流
     *
     * @param imgPath 图片的保存路径
     *
     * @param imgName 图片的名称
     *
     * @return 1：保存正常 0：保存失败
     */
    private static String saveToImgByInputStream(InputStream instreams, String imgName) {

        File file = new File(imgName);// 可以是任何图片格式.jpg,.png等
        if (instreams != null) {
            try {
                FileOutputStream fos = new FileOutputStream(file);

                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = instreams.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    instreams.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return file.getPath();
    }
}
