package com.buildit.common.application.service;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.common.application.dto.MoneyDTO;
import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.common.domain.model.Money;
import com.buildit.procurement.web.controller.PlantHireRequestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class MoneyAssembler extends ResourceAssemblerSupport<Money, MoneyDTO> {

    public MoneyAssembler() {
        super(PlantHireRequestController.class, MoneyDTO.class);
    }

    @Override
    public MoneyDTO toResource(Money period) {
        MoneyDTO dto = MoneyDTO.of(period.getTotal());

        return dto;
    }

}
