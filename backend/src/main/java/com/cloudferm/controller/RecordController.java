package com.cloudferm.controller;

import com.cloudferm.dto.CreateRecordRequest;
import com.cloudferm.dto.RecordResponse;
import com.cloudferm.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping
    public ResponseEntity<RecordResponse> createRecord(@RequestBody CreateRecordRequest request) {
        return ResponseEntity.ok(recordService.createRecord(request));
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<RecordResponse>> getRecordsByBatch(@PathVariable Long batchId) {
        return ResponseEntity.ok(recordService.getRecordsByBatch(batchId));
    }
}
