package com.buildit.procurement.application.dto;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class Team2CreatePORequestDTO extends ResourceSupport {

	Team2CustomerWrapper customer;

	Team2IdWrapper plant;

	BusinessPeriodDTO rentalPeriod;

}
