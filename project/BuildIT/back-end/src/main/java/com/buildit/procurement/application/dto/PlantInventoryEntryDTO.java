package com.buildit.procurement.application.dto;

import com.buildit.common.application.dto.MoneyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class PlantInventoryEntryDTO extends ResourceSupport {

	String href;

	String name;

	MoneyDTO pricePerDay;

}