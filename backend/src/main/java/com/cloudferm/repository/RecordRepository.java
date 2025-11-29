package com.cloudferm.repository;

import com.cloudferm.model.DailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecordRepository extends JpaRepository<DailyRecord, Long> {
    List<DailyRecord> findByBatchIdOrderByRecordedAtAsc(Long batchId);
}
