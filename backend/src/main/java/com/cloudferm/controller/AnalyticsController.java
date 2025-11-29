package com.cloudferm.controller;

import com.cloudferm.dto.ChartData;
import com.cloudferm.dto.RecordResponse;
import com.cloudferm.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final RecordService recordService;

    @GetMapping("/batch/{batchId}/temp-mass")
    public ResponseEntity<ChartData> getTempMassChart(@PathVariable Long batchId) {
        List<RecordResponse> records = recordService.getRecordsByBatch(batchId);
        return ResponseEntity.ok(buildChartData(records, "Temperature Mass (°C)", "tempMass"));
    }

    @GetMapping("/batch/{batchId}/ph")
    public ResponseEntity<ChartData> getPhChart(@PathVariable Long batchId) {
        List<RecordResponse> records = recordService.getRecordsByBatch(batchId);
        return ResponseEntity.ok(buildChartData(records, "pH Level", "phLevel"));
    }

    private ChartData buildChartData(List<RecordResponse> records, String label, String field) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        
        List<String> labels = records.stream()
                .map(r -> r.getRecordedAt().format(formatter))
                .collect(Collectors.toList());

        List<Double> data = records.stream()
                .map(r -> {
                    if ("tempMass".equals(field)) return r.getTempMass();
                    if ("phLevel".equals(field)) return r.getPhLevel();
                    return 0.0;
                })
                .collect(Collectors.toList());

        return ChartData.builder()
                .labels(labels)
                .data(data)
                .label(label)
                .build();
    }
}
