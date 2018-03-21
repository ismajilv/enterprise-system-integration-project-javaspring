package com.example.demo.sales.domain.model;

import com.example.demo.common.domain.model.BusinessPeriod;
import com.example.demo.inventory.domain.model.PlantInventoryEntry;
import com.example.demo.inventory.domain.model.PlantReservation;
import com.example.demo.sales.domain.model.POStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class PurchaseOrder {
    @Id @GeneratedValue

    Long id;

    public static PurchaseOrder of(PlantInventoryEntry plant, BusinessPeriod rentalPeriod) {
        PurchaseOrder po = new PurchaseOrder();
        po.plant = plant;
        po.rentalPeriod = rentalPeriod;
        po.status = POStatus.OPEN;
        return po;
    }
    @Column
    LocalDate issueDate;

    @Column
    LocalDate paymentSchedule;

//    @Embedded
//    Money total;

    @Column(precision = 8, scale = 2)
    BigDecimal total;

    @Enumerated(EnumType.STRING)
    POStatus status;

    @OneToMany
    List<PlantReservation> reservations = new ArrayList<>();

    @ManyToOne
    PlantInventoryEntry plant;

    @Embedded
    BusinessPeriod rentalPeriod;

}
