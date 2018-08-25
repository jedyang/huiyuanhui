package com.yunsheng.huiyuanhui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/index")
public class IndexController {

    @GetMapping("/index")
    public Map<String, Object> indexInfo(){
        Map<String, Object> result = new HashMap<>();
        List data = new ArrayList();
        Banner banner = new Banner();
        banner.setLink("");
        banner.setImage_url("http://yanxuan.nosdn.127.net/bff2e49136fcef1fd829f5036e07f116.jpg");
        data.add(banner);
        Banner banner2 = new Banner();
        banner2.setLink("");
        banner2.setImage_url("http://yanxuan.nosdn.127.net/8e50c65fda145e6dd1bf4fb7ee0fcecc.jpg");
        data.add(banner2);

        result.put("status", 0);
        result.put("data", data);
        return result;
    }

    class Banner{
        private String image_url;
        private String link;

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
