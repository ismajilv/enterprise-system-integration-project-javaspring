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

    @Column(nullable = false)
    Boolean accepted;

    @Column
    String rejectionComment; // Filled with possible replacement info if this ER gets rejected

}
