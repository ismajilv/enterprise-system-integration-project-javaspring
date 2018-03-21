package com.rentit.sales.domain.model;

import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantReservation;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class PurchaseOrder {
    @Id @GeneratedValue
    Long id;

    @OneToMany
    List<PlantReservation> reservations = new ArrayList<>();
    @ManyToOne
    PlantInventoryEntry plant;

    LocalDate issueDate;
    LocalDate paymentSchedule;

    @Column(precision = 8, scale = 2)
    BigDecimal total;

    @Enumerated(EnumType.STRING)
    POStatus status;

    @Embedded
    BusinessPeriod rentalPeriod;

    @ElementCollection
    List<POExtension> extensions = new ArrayList<>();

    public static PurchaseOrder of(PlantInventoryEntry plant, BusinessPeriod rentalPeriod) {
        PurchaseOrder po = new PurchaseOrder();
        po.plant = plant;
        po.rentalPeriod = rentalPeriod;
        po.status = POStatus.PENDING;
        return po;
    }

    public void requestExtension(POExtension extension) {
        extensions.add(extension);
        status = POStatus.PENDING_EXTENSION;
    }

    public LocalDate pendingExtensionEndDate() {
        if (extensions.size() > 0) {
            POExtension openExtension = extensions.get(extensions.size() - 1);
            return openExtension.getEndDate();
        }
        return null;
    }

    public void acceptExtension(PlantReservation reservation) {
        reservations.add(reservation);
        status = POStatus.OPEN;
        rentalPeriod = BusinessPeriod.of(rentalPeriod.getStartDate(), reservation.getSchedule().getEndDate());
        // UPDATE PO total!!
    }

    public void registerFirstAllocation(PlantReservation reservation) {
        reservations.add(reservation);
        status = POStatus.OPEN;
        rentalPeriod = BusinessPeriod.of(reservation.getSchedule().getStartDate(), reservation.getSchedule().getEndDate());
        // UPDATE PO total!!
    }

    public void reject() {
        status = POStatus.REJECTED;
    }

}
