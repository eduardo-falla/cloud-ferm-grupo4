package com.cloudferm.repository;

import com.cloudferm.model.QualityAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface QualityAssessmentRepository extends JpaRepository<QualityAssessment, Long> {
    Optional<QualityAssessment> findByBatchId(Long batchId);
}
