package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class MaintenancePlan {

    @Id
    @GeneratedValue
    Long id;

    @Column
    Integer yearOfAction;

    @OneToMany(cascade={CascadeType.ALL})
    List<MaintenanceTask> tasks = new ArrayList<>();

    @ManyToOne
    PlantInventoryItem plant;

}
