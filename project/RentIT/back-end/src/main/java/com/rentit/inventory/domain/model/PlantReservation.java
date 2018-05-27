package com.rentit.inventory.domain.model;

import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.sales.domain.model.PurchaseOrder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PlantReservation {
    @Id @GeneratedValue
    Long id;

    @ManyToOne
    PlantInventoryItem plant;

    @Embedded
    BusinessPeriod schedule;

    @JoinColumn(name = "purchase_order_id")
    @OneToOne
    PurchaseOrder purchaseOrder; // nullable - in future, maintenance tasks can reserve a plant as well

}
