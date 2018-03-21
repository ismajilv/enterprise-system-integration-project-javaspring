package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class PlantInventoryItem {

    @Id
    @GeneratedValue //They both come from JPA
    Long id;

    @Column
    String serialNumber;

    @Enumerated(EnumType.STRING)
    EquipmentCondition equipmentCondition;

    @ManyToOne
    PlantInventoryEntry plantInfo;

}
