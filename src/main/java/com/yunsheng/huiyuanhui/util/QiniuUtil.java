package com.yunsheng.huiyuanhui.util;

import com.google.gson.Gson;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class QiniuUtil {
    private static final Logger logger = LoggerFactory.getLogger(QiniuUtil.class);

    //文件上传
    public static String uploadFile2Qiniu(String filePath, String pre) throws IOException {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());

        UploadManager uploadManager = new UploadManager(cfg);
        String upToken = getQiniuToken();
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        File file = new File(filePath);
        String fileName = file.getName();
        String key = pre + fileName;

        try {
            Response response = uploadManager.put(filePath, key, upToken);
            if (response.statusCode != 200){
                return "err";
            }
            //解析上传成功的结果
//            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
            Response r = ex.response;
            logger.error(r.toString());
            try {
                logger.error(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return key;
    }

    public static String getQiniuToken(){
        //...生成上传凭证，然后准备上传
        Auth auth = Auth.create(Constants.ACCESSKEY, Constants.SECRETKEY);
        String upToken = auth.uploadToken(Constants.BUCKET);

        return upToken;
    }

}
