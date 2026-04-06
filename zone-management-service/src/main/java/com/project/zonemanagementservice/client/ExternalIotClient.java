package com.project.zonemanagementservice.client;

import com.project.zonemanagementservice.config.FeignAuthConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Objects;

@FeignClient(
        name = "external-iot-api",
        url = "${iot.server.url}",
        configuration = FeignAuthConfiguration.class // Add this
)
public interface ExternalIotClient {
    @PostMapping("/devices")
    Map<String, Object> registerDevice(@RequestBody Map<String, Object> request);
}
