package com.example.demo.sales.web.dto;

import com.example.demo.common.application.dto.BusinessPeriodDTO;
import lombok.Data;

@Data
public class CatalogQueryDTO {
    String name;
    BusinessPeriodDTO rentalPeriod;
}
