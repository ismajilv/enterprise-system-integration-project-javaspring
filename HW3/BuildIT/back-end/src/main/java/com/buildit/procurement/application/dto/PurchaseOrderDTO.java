package com.buildit.procurement.application.dto;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class PurchaseOrderDTO extends ResourceSupport {

	String href;

	// TODO add other fields & to assembler

}
