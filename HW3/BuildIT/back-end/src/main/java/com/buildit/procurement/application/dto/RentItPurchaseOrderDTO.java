package com.buildit.procurement.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Data
public class RentItPurchaseOrderDTO  extends ResourceSupport {

	String href;

	// TODO add other fields & to assembler

}
