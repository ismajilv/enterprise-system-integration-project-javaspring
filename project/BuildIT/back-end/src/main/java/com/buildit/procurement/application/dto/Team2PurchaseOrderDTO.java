package com.buildit.procurement.application.dto;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.procurement.domain.enums.RentItPurchaseOrderStatus;
import com.buildit.procurement.domain.enums.Team2PurchaseOrderStatus;
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
public class Team2PurchaseOrderDTO extends ResourceSupport {
	Long _id;
	BusinessPeriodDTO rentalPeriod;
	Team2CustomerWrapper customer;
	Team2PlantInventoryEntryDTO plant;
	@Column(precision = 8, scale = 2)
	BigDecimal total;
	Team2PurchaseOrderStatus status;
	Object notes;
	Map<String, Map<String, String>> _links; // self->href->"http://..."
}
