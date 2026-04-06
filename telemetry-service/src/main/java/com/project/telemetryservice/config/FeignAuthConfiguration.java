package com.project.telemetryservice.config;

import com.project.telemetryservice.service.TokenService;
import feign.RequestInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class FeignAuthConfiguration {
    private final TokenService tokenService;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = tokenService.getValidToken();
            // Automatically adds the required header [cite: 163]
            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }
}