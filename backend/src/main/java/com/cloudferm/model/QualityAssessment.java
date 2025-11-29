package com.cloudferm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quality_assessments")
public class QualityAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "batch_id", nullable = false, unique = true)
    private Batch batch;

    private Double fermentationPercentage; // % well fermented
    private Double slatePercentage; // % pizarroso
    private Double moldPercentage; // % moho
    private Double violetPercentage; // % violeta

    private Double sensoryScore; // 0-10

    @Column(columnDefinition = "TEXT")
    private String notes;
}
