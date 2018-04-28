package com.buildit.procurement.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @JoinColumn(name = "plant_hire_request_id", nullable = false)
    @ManyToOne(optional = false)
    PlantHireRequest plantHireRequest;

    @Column(nullable = false)
    String contents;

}