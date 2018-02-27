package com.example.demo.models;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Information that will be stored in the database

//We will use Java persistence API

@Entity
@Data
public class PlantInventoryEntry {

    @Id
    @GeneratedValue
    Long id;

    @Column
    String name;

    @Column
    String description;

    @Embedded
    Money price;

    @OneToMany(mappedBy = "plantInfo", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    List<PlantInventoryItem> items = new ArrayList<>();

    public boolean equals(Object o) {
        return (o instanceof PlantInventoryEntry) &&
                (!Objects.isNull(((PlantInventoryEntry)o).getId())) &&
                (((PlantInventoryEntry)o).getId().equals(this.getId()));
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
