package com.cloudferm.controller;

import com.cloudferm.model.CocoaVariety;
import com.cloudferm.model.Farm;
import com.cloudferm.repository.CocoaVarietyRepository;
import com.cloudferm.repository.FarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.cloudferm.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CocoaVarietyRepository varietyRepository;
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;

    @GetMapping("/varieties")
    public ResponseEntity<List<CocoaVariety>> getAllVarieties() {
        return ResponseEntity.ok(varietyRepository.findAll());
    }

    @PostMapping("/varieties")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CocoaVariety> createVariety(@RequestBody CocoaVariety variety) {
        variety.setCreatedAt(java.time.LocalDateTime.now());
        
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        com.cloudferm.model.User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        variety.setCreatedBy(user);
        return ResponseEntity.ok(varietyRepository.save(variety));
    }

    @GetMapping("/farms")
    public ResponseEntity<List<Farm>> getAllFarms() {
        return ResponseEntity.ok(farmRepository.findAll());
    }

    @PostMapping("/farms")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Farm> createFarm(@RequestBody Farm farm) {
        farm.setCreatedAt(java.time.LocalDateTime.now());
        
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        com.cloudferm.model.User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        farm.setCreatedBy(user);
        return ResponseEntity.ok(farmRepository.save(farm));
    }
}
