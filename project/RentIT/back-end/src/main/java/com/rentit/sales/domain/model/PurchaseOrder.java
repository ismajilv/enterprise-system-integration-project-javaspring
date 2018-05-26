package com.rentit.sales.domain.model;

import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantReservation;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

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

    @OneToOne(mappedBy = "purchaseOrder")
    ExtensionRequest extensionRequest;

    @Column
    String deliveryAddress;

    public static PurchaseOrder of(PlantInventoryEntry plant,
                                   BusinessPeriod rentalPeriod,
                                   String deliveryAddress) {
        PurchaseOrder po = new PurchaseOrder();
        po.plant = plant;
        po.rentalPeriod = rentalPeriod;
        po.status = POStatus.PENDING_APPROVAL;
        po.deliveryAddress = deliveryAddress;
        return po;
    }

    public void setStatus(POStatus status) {
        if (!this.status.isTransitionAllowed(status)) {
            throw new IllegalArgumentException("Purchase order transition not allowed: " + this.status + "=>" + status);
        }
        this.status = status;
    }

    public void requestExtension(ExtensionRequest extension) {
        this.extensionRequest = extension;
        setStatus(POStatus.PENDING_EXTENSION);
    }

    public LocalDate pendingExtensionEndDate() {
        if (!isNull(extensionRequest)) {
            return extensionRequest.newEndDate;
        }
        return null;
    }

    public void acceptExtension(PlantReservation reservation) {
        reservations.add(reservation);
        setStatus(POStatus.ACCEPTED);
        rentalPeriod = BusinessPeriod.of(rentalPeriod.getStartDate(), reservation.getSchedule().getEndDate());
        Long nrOfDaysExtendedFor = ChronoUnit.DAYS.between(reservation.getSchedule().getStartDate(), reservation.getSchedule().getEndDate());
        total = total.add(new BigDecimal(nrOfDaysExtendedFor));
    }

    public void registerFirstAllocation(PlantReservation reservation) {
        reservations.add(reservation);
        setStatus(POStatus.ACCEPTED);
        rentalPeriod = BusinessPeriod.of(reservation.getSchedule().getStartDate(), reservation.getSchedule().getEndDate());
        Long nrOfDaysRented = ChronoUnit.DAYS.between(rentalPeriod.getStartDate(), rentalPeriod.getEndDate());
        total = plant.getPrice().multiply(new BigDecimal(nrOfDaysRented));
    }

    public void reject() {
        setStatus(POStatus.REJECTED);
    }

    public void cancel() {
        setStatus(POStatus.CANCELLED);
    }

    public void dispatch() {
        setStatus(POStatus.PLANT_DISPATCHED);
    }

    public void deliver() {
        setStatus(POStatus.PLANT_DELIVERED);
    }

    public void customerReject() {
        setStatus(POStatus.REJECTED_BY_CUSTOMER);
    }

    public void markAsReturned() {
        setStatus(POStatus.PLANT_RETURNED);
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "id=" + id +
                ", reservations=" + reservations +
                ", plant=" + plant +
                ", issueDate=" + issueDate +
                ", paymentSchedule=" + paymentSchedule +
                ", total=" + total +
                ", status=" + status +
                ", rentalPeriod=" + rentalPeriod +
                ", extension=" + extensionRequest +
                '}';
    }
}
