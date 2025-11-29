package com.cloudferm.repository;

import com.cloudferm.model.BatchAssignmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BatchAssignmentHistoryRepository extends JpaRepository<BatchAssignmentHistory, Long> {
    List<BatchAssignmentHistory> findByBatchId(Long batchId);
}
