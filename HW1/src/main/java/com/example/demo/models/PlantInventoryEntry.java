package com.example.demo.models;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

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

}
