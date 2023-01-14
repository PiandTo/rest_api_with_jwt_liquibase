package com.example.restful_test.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public OpenAPI customOpenAPI(@Value("${api.description}") String description,
                                 @Value("${api.version}") String version) {
        return new OpenAPI().info(new Info()
                .title("User Rest API")
                .version(version)
                .description(description)
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
