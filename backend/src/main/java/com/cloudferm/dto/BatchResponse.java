package com.cloudferm.dto;

import com.cloudferm.model.BatchStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class BatchResponse {
    private Long id;
    private String code;
    private String cocoaVariety; // Keep as string for display name
    private Long varietyId;
    private String farmName;
    private Long farmId;
    private Double grossWeight;
    private LocalDate startDate;
    private LocalDate endDate;
    private BatchStatus status;
    private String operatorName;
    private Long operatorId;
}
