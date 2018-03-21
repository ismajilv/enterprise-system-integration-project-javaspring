package com.example.demo.inventory.domain.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@ToString(exclude = {"maintenancePlans"})
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

    @OneToMany(mappedBy = "plant", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    List<MaintenancePlan> maintenancePlans = new ArrayList<>();

    public boolean equals(Object o) {
        return (o instanceof PlantInventoryItem) &&
                (!Objects.isNull(((PlantInventoryItem)o).getId())) &&
                (((PlantInventoryItem)o).getId().equals(this.getId()));
    }

}
