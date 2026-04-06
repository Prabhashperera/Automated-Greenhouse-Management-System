package com.project.zonemanagementservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    // These should be set in your Git Config repo or application.properties
    @Value("${iot.server.url}")
    private String iotUrl;
    @Value("${iot.server.username}")
    private String username;
    @Value("${iot.server.password}")
    private String password;

    private String cachedToken;

    public String getValidToken() {
        if (cachedToken != null) return cachedToken;

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", username);
        loginRequest.put("password", password);

        // Call the login endpoint defined in the documentation [cite: 150]
        Map<String, Object> response = restTemplate.postForObject(
                iotUrl + "/auth/login", loginRequest, Map.class);

        cachedToken = (String) response.get("accessToken");
        return cachedToken;
    }
}