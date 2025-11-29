package com.cloudferm.service;

import com.cloudferm.dto.CreateRecordRequest;
import com.cloudferm.dto.RecordResponse;
import com.cloudferm.model.Batch;
import com.cloudferm.model.DailyRecord;
import com.cloudferm.repository.BatchRepository;
import com.cloudferm.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final BatchRepository batchRepository;

    public RecordResponse createRecord(CreateRecordRequest request) {
        Batch batch = batchRepository.findById(request.getBatchId())
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        DailyRecord record = DailyRecord.builder()
                .batch(batch)
                .recordedAt(LocalDateTime.now())
                .tempMass(request.getTempMass())
                .tempAmbient(request.getTempAmbient())
                .humidityMass(request.getHumidityMass())
                .humidityAmbient(request.getHumidityAmbient())
                .phLevel(request.getPhLevel())
                .isTurned(request.isTurned())
                .operatorObservations(request.getOperatorObservations())
                .build();

        DailyRecord savedRecord = recordRepository.save(record);
        return mapToResponse(savedRecord);
    }

    public List<RecordResponse> getRecordsByBatch(Long batchId) {
        return recordRepository.findByBatchIdOrderByRecordedAtAsc(batchId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private RecordResponse mapToResponse(DailyRecord record) {
        return RecordResponse.builder()
                .id(record.getId())
                .batchId(record.getBatch().getId())
                .recordedAt(record.getRecordedAt())
                .tempMass(record.getTempMass())
                .tempAmbient(record.getTempAmbient())
                .humidityMass(record.getHumidityMass())
                .humidityAmbient(record.getHumidityAmbient())
                .phLevel(record.getPhLevel())
                .isTurned(record.isTurned())
                .operatorObservations(record.getOperatorObservations())
                .build();
    }
}
