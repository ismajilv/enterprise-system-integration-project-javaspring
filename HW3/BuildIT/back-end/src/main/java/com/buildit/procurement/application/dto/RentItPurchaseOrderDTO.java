package com.buildit.procurement.application.dto;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.procurement.domain.enums.RentItPurchaseOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class RentItPurchaseOrderDTO extends ResourceSupport {
	Long _id;
	RentItPlantInventoryEntryDTO plant;
	BusinessPeriodDTO rentalPeriod;
	@Column(precision = 8, scale = 2)
	BigDecimal total;
	RentItPurchaseOrderStatus status;
	Map<String, Map<String, String>> _links; // self->href->"http://..."

}
