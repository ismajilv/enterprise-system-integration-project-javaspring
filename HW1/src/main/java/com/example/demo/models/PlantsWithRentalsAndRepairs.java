package com.example.demo.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force=true,access= AccessLevel.PRIVATE)
@AllArgsConstructor
public class PlantsWithRentalsAndRepairs {

	PlantInventoryEntry entry;

	Long rentals;

	Long repairs;

}
