package com.example._2025_bucket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 외부 디렉토리(C:/uploads/images/)를 정적 리소스 경로(/uploads/)로 매핑
        registry.addResourceHandler("/uploads/images/**") // URL 패턴
                .addResourceLocations("file:C:/uploads/images/"); // 실제 디렉토리 경로
    }
}