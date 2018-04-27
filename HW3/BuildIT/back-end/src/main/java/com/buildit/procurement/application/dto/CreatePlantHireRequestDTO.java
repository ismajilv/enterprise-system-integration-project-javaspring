package com.buildit.procurement.application.dto;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.common.application.dto.MoneyDTO;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class CreatePlantHireRequestDTO extends ResourceSupport {

	// TODO add validations
	Long constructionSiteId;

	Long supplierId;

	String plantHref;

	BusinessPeriodDTO rentalPeriod;

	MoneyDTO rentalCost; // TODO query from RentIt in service?

}