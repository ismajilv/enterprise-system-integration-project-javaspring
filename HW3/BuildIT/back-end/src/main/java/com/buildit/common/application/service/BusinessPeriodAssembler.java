package com.buildit.common.application.service;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.procurement.web.controller.PlantHireRequestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class BusinessPeriodAssembler extends ResourceAssemblerSupport<BusinessPeriod, BusinessPeriodDTO> {

	public BusinessPeriodAssembler() {
		super(PlantHireRequestController.class, BusinessPeriodDTO.class);
	}

	@Override
	public BusinessPeriodDTO toResource(BusinessPeriod period) {
		BusinessPeriodDTO dto = BusinessPeriodDTO.of(period.getStartDate(), period.getEndDate());

		return dto;
	}

}
