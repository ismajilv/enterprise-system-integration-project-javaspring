package com.example.rentit.sales.rest.dtos;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class PlantInventoryEntry {
    String id;
    String name;
    String description;
    String price;
}
