package com.buildit.procurement.domain.model;

import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.procurement.domain.enums.PHRStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class PlantHireRequest {

    @Id
    @GeneratedValue
    Long id;

    @Embedded
    BusinessPeriod rentalPeriod;

    @Enumerated(EnumType.STRING)
    PHRStatus status;

    @Column(precision = 8, scale = 2)
    BigDecimal rentalCost;

    @JoinColumn(name = "construction_site_id")
    @OneToOne
    ConstructionSite constructionSite;

    @JoinColumn(name = "requesting_site_engineer_id")
    @OneToOne
    Employee requestingSiteEngineer;

    @JoinColumn(name = "plant_id")
    @OneToOne
    PlantInventoryEntry plant;

    @JoinColumn(name = "supplier_id")
    @OneToOne
    Supplier supplier;

    @JoinColumn(name = "purchase_order_id")
    @OneToOne
    PurchaseOrder purchaseOrder;

    @OneToMany
    List<Comment> comments;

}
