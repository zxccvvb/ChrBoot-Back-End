package com.chr.admin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .openapi("3.0.0")
                .info(new Info()
                        .title("chrboot接口文档")
                        .description("chrboot接口文档")
                        .version("1.0"));
    }
}