package com.buildit.procurement.application.dto;

import com.buildit.procurement.domain.enums.RentItPurchaseOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class RentItPurchaseOrderDTO extends ResourceSupport {

	String poHref;

	RentItPurchaseOrderStatus status;

}
