package com.chr.admin.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestTask {



    @Scheduled(cron = "0/5 * * * * ?") //每5秒执行一次
    public void test1(){
        log.info("定时任务开始执行...");
    }


}
