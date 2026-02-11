package com.chr.admin.customize.async;


import com.chr.admin.controller.common.WebSocketController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestTask {

    @Autowired
    private WebSocketController webSocketController;

//    @Scheduled(cron = "0/5 * * * * ?") //每5秒执行一次
//    public void test1(){
//        log.info("定时任务开始执行...");
//        webSocketController.sendToAllClient("测试websocket");
//
//    }


}
