package com.buildit.procurement.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor(force = true)
public class Team2PlantInventoryEntryDTO {
	Long _id;
	String name;
	String description;
	@Column(precision = 8, scale = 2)
	BigDecimal price;
	String category;
	String supplier;
	Map<String, Map<String, String>> _links; // self->href->"http://..."
}