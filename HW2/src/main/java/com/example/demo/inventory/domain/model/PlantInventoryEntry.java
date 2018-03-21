package com.example.demo.inventory.domain.model;


import com.example.demo.sales.domain.model.PurchaseOrder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Information that will be stored in the database

//We will use Java persistence API

@Entity
@Data
@ToString(exclude = {"items", "purchaseOrders"})
public class PlantInventoryEntry {

    @Id
    @GeneratedValue
    Long id;

    @Column
    String name;

    @Column
    String description;

    @Column(precision=8, scale=2)
    BigDecimal price;



    @OneToMany(mappedBy = "plantInfo", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    List<PlantInventoryItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "plant", cascade=CascadeType.ALL)// , fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    List<PurchaseOrder> purchaseOrders = new ArrayList<>();

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
