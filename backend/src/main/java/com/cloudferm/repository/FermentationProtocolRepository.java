package com.cloudferm.repository;

import com.cloudferm.model.FermentationProtocol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FermentationProtocolRepository extends JpaRepository<FermentationProtocol, Long> {
    List<FermentationProtocol> findByVarietyIdOrderByDayNumberAsc(Long varietyId);
}
