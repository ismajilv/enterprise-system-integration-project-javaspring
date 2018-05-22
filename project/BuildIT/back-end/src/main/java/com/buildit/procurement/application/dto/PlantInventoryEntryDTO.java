package com.buildit.procurement.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class PlantInventoryEntryDTO extends ResourceSupport {

	String href;

	Long externalId;

	String name;

	BigDecimal pricePerDay;

	SupplierDTO supplier;

}