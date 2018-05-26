package com.rentit.sales.application.dto;

import com.rentit.common.application.dto.BusinessPeriodDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class CreatePORequestDTO {

	Long plantId;

	BusinessPeriodDTO rentalPeriod;

	String deliveryAddress;

}
