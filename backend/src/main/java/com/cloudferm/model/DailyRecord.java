package com.cloudferm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "daily_records")
public class DailyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @Column(nullable = false)
    private LocalDateTime recordedAt;

    @Column(nullable = false)
    private Double tempMass;

    @Column(nullable = false)
    private Double tempAmbient;

    @Column(nullable = false)
    private Double humidityMass;

    @Column(nullable = false)
    private Double humidityAmbient;

    @Column(nullable = false)
    private Double phLevel;

    @Column(nullable = false)
    private boolean isTurned;

    @Column(columnDefinition = "TEXT")
    private String operatorObservations;
}
