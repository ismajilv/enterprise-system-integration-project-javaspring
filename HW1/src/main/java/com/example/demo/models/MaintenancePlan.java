package com.example.demo.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(exclude = {"tasks"})
public class MaintenancePlan {

    @Id
    @GeneratedValue
    Long id;

    @Column
    Integer yearOfAction;

    @OneToMany(mappedBy = "maintenancePlan", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    List<MaintenanceTask> tasks = new ArrayList<>();


    @ManyToOne
    PlantInventoryItem plant;

}
