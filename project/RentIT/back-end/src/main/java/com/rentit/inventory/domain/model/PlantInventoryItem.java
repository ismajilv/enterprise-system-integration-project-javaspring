package com.rentit.inventory.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class PlantInventoryItem {
    @Id @GeneratedValue
    Long id;

    String serialNumber;

    @ManyToOne
    PlantInventoryEntry plantInfo;
}
