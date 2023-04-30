package com.cfive.pinnacle.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setName("FatttSnake");
        contact.setUrl("https://fatweb.top");
        contact.setEmail("fatttsnake@gmail.com");
        return new OpenAPI().info(new Info().title("Pinnacle 接口文档").description("Pinnacle OA 后端接口文档，包含各个 Controller 类接口调用信息").contact(contact).version("1.0.0"));
    }
}
