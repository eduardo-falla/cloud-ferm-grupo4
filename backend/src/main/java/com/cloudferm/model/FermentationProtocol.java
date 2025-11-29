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
@Table(name = "fermentation_protocols")
public class FermentationProtocol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "variety_id", nullable = false)
    private CocoaVariety variety;

    private Integer dayNumber;

    private Double minTemp;
    private Double maxTemp;
    private Double targetPh;
}
