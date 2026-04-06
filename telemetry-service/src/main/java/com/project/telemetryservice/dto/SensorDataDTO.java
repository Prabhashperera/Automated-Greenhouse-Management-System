package com.project.telemetryservice.dto;

import lombok.Data;

@Data
public class SensorDataDTO {
    private String deviceId;
    private String zoneId;
    private ValueDTO value;
    private String capturedAt;

    @Data
    public static class ValueDTO {
        private Double temperature;
        private String tempUnit;
        private Double humidity;
        private String humidityUnit;
    }
}
