package com.buildit.procurement.application.dto;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreatePlantHireRequestDTO extends ResourceSupport {

	@Min(1)
	long constructionSiteId;

	@Min(1)
	long supplierId;

	@Size(min = 10)
	String plantHref;

	@NotNull
	BusinessPeriodDTO rentalPeriod;
}