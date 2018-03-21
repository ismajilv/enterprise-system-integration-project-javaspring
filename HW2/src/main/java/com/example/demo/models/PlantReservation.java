package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PlantReservation {
    @Id @GeneratedValue

    Long id;

    @Embedded
    BusinessPeriod schedule;

    @ManyToOne
    PlantInventoryItem plant;

    @OneToOne
    MaintenancePlan maintenancePlan; // optional

    @OneToOne
    PurchaseOrder rental; // optional

}
