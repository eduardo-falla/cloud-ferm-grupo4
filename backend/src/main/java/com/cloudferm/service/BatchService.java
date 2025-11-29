package com.cloudferm.service;

import com.cloudferm.dto.BatchResponse;
import com.cloudferm.dto.CreateBatchRequest;
import com.cloudferm.model.Batch;
import com.cloudferm.model.BatchStatus;
import com.cloudferm.model.User;
import com.cloudferm.repository.BatchRepository;
import com.cloudferm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final BatchRepository batchRepository;
    private final UserRepository userRepository;

    private final com.cloudferm.repository.CocoaVarietyRepository varietyRepository;
    private final com.cloudferm.repository.FarmRepository farmRepository;
    private final com.cloudferm.repository.AuditLogRepository auditLogRepository;
    private final com.cloudferm.repository.BatchAssignmentHistoryRepository assignmentHistoryRepository;

    public BatchResponse createBatch(CreateBatchRequest request) {
        User operator = userRepository.findById(request.getOperatorId())
                .orElseThrow(() -> new RuntimeException("Operator not found"));

        com.cloudferm.model.CocoaVariety variety = varietyRepository.findById(request.getVarietyId())
                .orElseThrow(() -> new RuntimeException("Variety not found"));

        com.cloudferm.model.Farm farm = null;
        if (request.getFarmId() != null) {
            farm = farmRepository.findById(request.getFarmId())
                    .orElseThrow(() -> new RuntimeException("Farm not found"));
        }

        Batch batch = Batch.builder()
                .code(request.getCode())
                .cocoaVariety(variety)
                .farm(farm)
                .grossWeight(request.getGrossWeight())
                .startDate(request.getStartDate())
                .status(BatchStatus.ACTIVE)
                .operator(operator)
                .build();

        Batch savedBatch = batchRepository.save(batch);

        // Audit Log
        createAuditLog("CREATE", "Batch", savedBatch.getId(), "Created batch " + savedBatch.getCode());

        // Assignment History
        createAssignmentHistory(savedBatch, operator);

        return mapToResponse(savedBatch);
    }

    public List<BatchResponse> getAllBatches() {
        return batchRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public BatchResponse getBatchById(Long id) {
        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found with id: " + id));
        return mapToResponse(batch);
    }

    public List<BatchResponse> getActiveBatches() {
        return batchRepository.findByStatus(BatchStatus.ACTIVE).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BatchResponse> getFinishedBatches() {
        return batchRepository.findByStatus(BatchStatus.FINISHED).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BatchResponse> getFinishedBatchesByOperator(Long operatorId) {
        User operator = userRepository.findById(operatorId)
                .orElseThrow(() -> new RuntimeException("Operator not found"));
        return batchRepository.findByStatusAndOperator(BatchStatus.FINISHED, operator).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    private BatchResponse mapToResponse(Batch batch) {
        return BatchResponse.builder()
                .id(batch.getId())
                .code(batch.getCode())
                .cocoaVariety(batch.getCocoaVariety().getName())
                .varietyId(batch.getCocoaVariety().getId())
                .farmName(batch.getFarm() != null ? batch.getFarm().getName() : null)
                .farmId(batch.getFarm() != null ? batch.getFarm().getId() : null)
                .grossWeight(batch.getGrossWeight())
                .startDate(batch.getStartDate())
                .endDate(batch.getEndDate())
                .status(batch.getStatus())
                .operatorName(batch.getOperator().getFullName())
                .operatorId(batch.getOperator().getId())
                .build();
    }

    public BatchResponse closeBatch(Long batchId) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        
        if (batch.getStatus() != BatchStatus.ACTIVE) {
            throw new RuntimeException("Only ACTIVE batches can be closed");
        }
        
        batch.setStatus(BatchStatus.FINISHED);
        batch.setEndDate(java.time.LocalDate.now());
        batchRepository.save(batch);

        // Audit Log
        createAuditLog("UPDATE", "Batch", batch.getId(), "Closed batch " + batch.getCode());
        
        return mapToResponse(batch);
    }

    private void createAuditLog(String action, String entityName, Long entityId, String details) {
        // In a real app, we would get the current user from SecurityContext
        // For now, we'll assume system or admin action if not available
        // Or we can pass the user explicitly.
        // Let's try to get from SecurityContext if possible, or use a placeholder ID (e.g. 1 for Admin)
        
        Long userId = 1L; // Default to Admin ID 1 for now as we don't have easy access to current user ID here without passing it
        
        com.cloudferm.model.AuditLog log = com.cloudferm.model.AuditLog.builder()
                .userId(userId)
                .action(action)
                .entityName(entityName)
                .entityId(entityId)
                .timestamp(java.time.LocalDateTime.now())
                .details(details)
                .build();
        auditLogRepository.save(log);
    }

    private void createAssignmentHistory(Batch batch, User operator) {
        com.cloudferm.model.BatchAssignmentHistory history = com.cloudferm.model.BatchAssignmentHistory.builder()
                .batch(batch)
                .operator(operator)
                .assignedAt(java.time.LocalDateTime.now())
                .build();
        assignmentHistoryRepository.save(history);
    }
}
