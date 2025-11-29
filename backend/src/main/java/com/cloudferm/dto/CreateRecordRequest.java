package com.cloudferm.dto;

import lombok.Data;

@Data
public class CreateRecordRequest {
    private Long batchId;
    private Double tempMass;
    private Double tempAmbient;
    private Double humidityMass;
    private Double humidityAmbient;
    private Double phLevel;
    private boolean isTurned;
    private String operatorObservations;
}
