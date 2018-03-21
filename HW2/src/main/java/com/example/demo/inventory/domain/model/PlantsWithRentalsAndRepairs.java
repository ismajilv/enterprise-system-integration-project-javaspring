package com.example.demo.inventory.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force=true,access= AccessLevel.PRIVATE)
@AllArgsConstructor(staticName="of")
public class PlantsWithRentalsAndRepairs {

	PlantInventoryEntry entry;

	int year;

	Long rentals;

	Long repairs;

}
