package com.cloudferm.controller;

import com.cloudferm.dto.BatchResponse;
import com.cloudferm.dto.CreateBatchRequest;
import com.cloudferm.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batches")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;
    private final com.cloudferm.service.ReportService reportService;

    @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BatchResponse> createBatch(@RequestBody CreateBatchRequest request) {
        return ResponseEntity.ok(batchService.createBatch(request));
    }

    @GetMapping
    public ResponseEntity<List<BatchResponse>> getAllBatches(@RequestParam(required = false) String status) {
        if ("ACTIVE".equalsIgnoreCase(status)) {
            return ResponseEntity.ok(batchService.getActiveBatches());
        }
        return ResponseEntity.ok(batchService.getAllBatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchResponse> getBatchById(@PathVariable Long id) {
        return ResponseEntity.ok(batchService.getBatchById(id));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<BatchResponse> closeBatch(@PathVariable Long id) {
        return ResponseEntity.ok(batchService.closeBatch(id));
    }

    @GetMapping("/history")
    public ResponseEntity<List<BatchResponse>> getFinishedBatches(
            @RequestParam(required = false) Long operatorId,
            org.springframework.security.core.Authentication authentication) {
        
        // Get user details from authentication
        org.springframework.security.core.userdetails.UserDetails userDetails = 
                (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
        
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        
        if (isAdmin) {
            // Admin can see all finished batches or filter by operator
            if (operatorId != null) {
                return ResponseEntity.ok(batchService.getFinishedBatchesByOperator(operatorId));
            }
            return ResponseEntity.ok(batchService.getFinishedBatches());
        } else {
            // Operators can only see their own finished batches
            com.cloudferm.repository.UserRepository userRepo = batchService.getUserRepository();
            com.cloudferm.model.User currentUser = userRepo.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return ResponseEntity.ok(batchService.getFinishedBatchesByOperator(currentUser.getId()));
        }
    }

    @GetMapping("/{id}/report/excel")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> downloadExcelReport(@PathVariable Long id) {
        try {
            byte[] excelData = reportService.generateExcelReport(id);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=batch_" + id + "_report.xlsx")
                    .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .body(excelData);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
