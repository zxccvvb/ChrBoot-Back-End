package com.chr.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.chr.*")
public class ChrbootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChrbootAdminApplication.class, args);
    }

}
