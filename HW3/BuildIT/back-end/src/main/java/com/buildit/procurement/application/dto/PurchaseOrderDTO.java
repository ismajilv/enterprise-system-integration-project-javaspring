package com.buildit.procurement.application.dto;

import com.buildit.procurement.domain.enums.POStatus;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class PurchaseOrderDTO extends ResourceSupport {

	String href;

	POStatus status;

}
