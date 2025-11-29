package com.cloudferm.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class RecordResponse {
    private Long id;
    private Long batchId;
    private LocalDateTime recordedAt;
    private Double tempMass;
    private Double tempAmbient;
    private Double humidityMass;
    private Double humidityAmbient;
    private Double phLevel;
    private boolean isTurned;
    private String operatorObservations;
}
