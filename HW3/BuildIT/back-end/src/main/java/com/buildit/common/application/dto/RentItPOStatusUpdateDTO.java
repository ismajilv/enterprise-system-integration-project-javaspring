package com.buildit.common.application.dto;

import com.buildit.procurement.domain.enums.RentItPurchaseOrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(force = true)
public class RentItPOStatusUpdateDTO {

	@NotNull
	String href;

	@NotNull
	RentItPurchaseOrderStatus value;

}