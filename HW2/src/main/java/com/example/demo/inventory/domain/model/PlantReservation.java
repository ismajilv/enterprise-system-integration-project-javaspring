package com.example.demo.inventory.domain.model;

import com.example.demo.common.domain.model.BusinessPeriod;
import com.example.demo.sales.domain.model.PurchaseOrder;
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
