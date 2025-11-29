package com.cloudferm.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ChartData {
    private List<String> labels; // Dates/Times
    private List<Double> data;   // Values
    private String label;        // Dataset label (e.g. "Temp Mass")
}
