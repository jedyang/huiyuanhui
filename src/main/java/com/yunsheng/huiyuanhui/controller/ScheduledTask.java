package com.yunsheng.huiyuanhui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScheduledTask {
    private final Logger logger = LoggerFactory.getLogger(getClass());
 
    /**
     * 每间隔10秒输出时间
     */
    @Scheduled(fixedRate = 10000)
    public void logTime(){
        log.info("定时任务，现在时间："+System.currentTimeMillis());
    }
}
