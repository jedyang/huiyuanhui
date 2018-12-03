package com.yunsheng.huiyuanhui;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yunsheng.huiyuanhui.mapper")
public class HuiyuanhuiApplication {
    private static final Logger logger = LoggerFactory.getLogger(HuiyuanhuiApplication.class);

    public static void main(String[] args) {
        logger.info("HuiyuanhuiApplication start===");
        SpringApplication.run(HuiyuanhuiApplication.class, args);
    }
}
