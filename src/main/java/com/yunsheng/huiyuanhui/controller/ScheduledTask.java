package com.yunsheng.huiyuanhui.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScheduledTask {

    /**
     * 每间隔1秒输出时间
     */
//    @Scheduled(fixedRate = 1000)
//    @Async
//    public void logTime() {
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        log.info("定时任务，现在时间：" + new Date());
//    }

    /**
     * cron  每5秒 会受执行时间的影响，如果方法的执行能在间隔期内完成，那么每5秒执行一次可以做到 如果间隔期内执行不完，那么要从上次执行完后，再间隔5秒触发。比如方法里sleep10秒，那么执行效果是每15秒一次
     *
     * 但是,如果使用异步线程池,一般没有这个问题 另外要注意,异步线程池,多个方法之间是共用的 需要合理设置线程池参数
     */
//    @Scheduled(cron = "0/5 * * * * *")
//    @Async
//    public void cronTask() {
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        log.info("Cron定时任务，现在时间：" + new Date());
//    }
}
