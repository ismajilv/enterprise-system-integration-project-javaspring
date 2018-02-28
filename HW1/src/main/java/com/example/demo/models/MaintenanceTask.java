package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Data
public class MaintenanceTask {

    @Id
    @GeneratedValue
    Long id;

    @Column
    String description;

    @Enumerated(EnumType.STRING)
    TypeOfWork typeOfWork;

    @Embedded
    Money price;

    @OneToOne
    PlantReservation reservation;

    @ManyToOne
    MaintenancePlan maintenancePlan;

}
