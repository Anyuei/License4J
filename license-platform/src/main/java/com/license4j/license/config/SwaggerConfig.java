package com.license4j.license.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private Info info(){
        return new Info()
                .title("数通魔方许可授权平台API接口文档")
                .description("A project for License")
                .version("v1.0.0");
    }
    private ExternalDocumentation externalDocumentation() {
        return new ExternalDocumentation()
                .description("官网")
                .url("https://www.shutongmofang.cn/index.html");
    }
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(info())
                .externalDocs(externalDocumentation());
    }
}