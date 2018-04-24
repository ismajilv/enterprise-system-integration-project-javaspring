package com.rentit.inventory.domain.model;

import com.rentit.common.domain.model.BusinessPeriod;
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
}
