package com.cloudferm.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateBatchRequest {
    private String code;
    private Long varietyId;
    private Long farmId;
    private Double grossWeight;
    private LocalDate startDate;
    private Long operatorId;
}
