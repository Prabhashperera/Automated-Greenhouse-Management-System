package com.project.zonemanagementservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Objects;

@FeignClient(name = "external-iot-api", url = "http://104.211.95.241:8080/api")
public interface ExternalIotClient {
    @PostMapping("/devices")
    Map<String, Object> registerDevice(@RequestBody Map<String, Object> deviceRequest);
}
