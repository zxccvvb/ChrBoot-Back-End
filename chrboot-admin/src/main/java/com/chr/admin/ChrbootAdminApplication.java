package com.chr.admin;

import com.chr.admin.config.WebSocketConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement //开启事务
@EnableCaching //开启缓存
@EnableScheduling //开启任务调度
@ComponentScan("com.chr.*")
@ImportAutoConfiguration(exclude = {WebSocketConfig.class}) // 或者
public class ChrbootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChrbootAdminApplication.class, args);
    }

}
