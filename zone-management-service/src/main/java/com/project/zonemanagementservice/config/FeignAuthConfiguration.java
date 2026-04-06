package com.project.zonemanagementservice.config;

import com.project.zonemanagementservice.service.TokenService;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignAuthConfiguration {
    @Autowired
    private TokenService tokenService;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = tokenService.getValidToken();
            // Automatically adds the required header [cite: 163]
            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }
}