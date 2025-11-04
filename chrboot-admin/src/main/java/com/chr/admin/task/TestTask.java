package com.chr.admin.task;


import com.chr.admin.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestTask {

    @Autowired
    private WebSocketServer webSocketServer;

    @Scheduled(cron = "0/5 * * * * ?") //每5秒执行一次
    public void test1(){
        log.info("定时任务开始执行...");
        webSocketServer.sendToAllClient("测试websocket");

    }


}
