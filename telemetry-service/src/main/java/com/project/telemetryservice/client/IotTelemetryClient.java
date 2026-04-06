package com.project.telemetryservice.client;

import com.project.telemetryservice.config.FeignAuthConfiguration;
import com.project.telemetryservice.dto.SensorDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "iot-telemetry-client", url = "${iot.server.url}", configuration = FeignAuthConfiguration.class)
public interface IotTelemetryClient {
    @GetMapping("/devices/telemetry/{deviceId}")
    SensorDataDTO getTelemetry(@PathVariable("deviceId") String deviceId);
}
