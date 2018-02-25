package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class PurchaseOrder {
    @Id @GeneratedValue

    Long id;

    @Column
    LocalDate issueDate;

    @Column
    LocalDate paymentSchedule;

    @Embedded
    Money total;

    @Enumerated(EnumType.STRING)
    POStatus status;

    @OneToMany
    List<PlantReservation> reservations;

    @ManyToOne
    PlantInventoryEntry plant;

    @Embedded
    BusinessPeriod rentalPeriod;

}
