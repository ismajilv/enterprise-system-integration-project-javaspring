package com.example.demo.inventory.domain.model;

import lombok.Data;

import javax.persistence.*;


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
