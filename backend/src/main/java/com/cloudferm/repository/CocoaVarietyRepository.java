package com.cloudferm.repository;

import com.cloudferm.model.CocoaVariety;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CocoaVarietyRepository extends JpaRepository<CocoaVariety, Long> {
    Optional<CocoaVariety> findByName(String name);
}
