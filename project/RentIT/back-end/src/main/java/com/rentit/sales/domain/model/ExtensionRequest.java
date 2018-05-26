package com.rentit.sales.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class ExtensionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(name = "new_end_date", nullable = false)
    LocalDate newEndDate;

    @JoinColumn(name = "purchase_order_id", nullable = false)
    @OneToOne(optional = false)
    PurchaseOrder purchaseOrder;

    @Column
    String comment; // Filled if this ER gets rejected

}
