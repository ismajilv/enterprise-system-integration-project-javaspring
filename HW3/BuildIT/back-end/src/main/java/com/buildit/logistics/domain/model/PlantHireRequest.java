package com.buildit.logistics.domain.model;

import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.logistics.domain.enums.PHRStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class PlantHireRequest {

    @Id
    @GeneratedValue
    @Column(name = "request_id")
    Long id;

    String address;

    @Embedded
    @Column(name = "rental_period")
    BusinessPeriod rentalPeriod;

    @Enumerated(EnumType.STRING)
    PHRStatus status;

    @Column(name = "rental_cost", precision = 8, scale = 2)
    BigDecimal rentalCost;

    @Column(name = "creator_name")
    String creatorName;

    @JoinColumn(name = "plant_id")
    @OneToOne
    PlantInventoryEntry plant;

    @JoinColumn(name = "purchase_order_id")
    @OneToOne
    PurchaseOrder purchaseOrder;
}
