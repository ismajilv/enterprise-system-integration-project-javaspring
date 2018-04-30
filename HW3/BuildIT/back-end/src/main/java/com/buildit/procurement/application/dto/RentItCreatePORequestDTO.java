package com.buildit.procurement.application.dto;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class RentItCreatePORequestDTO extends ResourceSupport {

	RentItPlantInventoryEntryDTO plant;

	BusinessPeriodDTO rentalPeriod;

}
