package com.yunsheng.huiyuanhui.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
//@Slf4j
public class AdminIndexController {
    @RequestMapping(value = "/index")
    public String login() {
        return "index";
    }

    @RequestMapping(value = "/{page}")
    public String page(@PathVariable("page") String page) {
//        log.info("show:" + page);
        return page;
    }
}
