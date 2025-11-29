package com.cloudferm.repository;

import com.cloudferm.model.Batch;
import com.cloudferm.model.BatchStatus;
import com.cloudferm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, Long> {
    List<Batch> findByStatus(BatchStatus status);
    List<Batch> findByStatusAndOperator(BatchStatus status, User operator);
}
