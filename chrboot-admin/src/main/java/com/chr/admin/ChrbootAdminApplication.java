package com.chr.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement //开启事务
@EnableCaching //开启缓存
@ComponentScan("com.chr.*")
public class ChrbootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChrbootAdminApplication.class, args);
    }

}
