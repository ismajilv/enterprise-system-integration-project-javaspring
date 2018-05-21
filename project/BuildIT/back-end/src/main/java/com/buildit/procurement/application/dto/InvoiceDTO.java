package com.buildit.procurement.application.dto;

import com.buildit.procurement.domain.model.PurchaseOrder;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class InvoiceDTO extends ResourceSupport {

	Long _id;

	PurchaseOrderDTO purchaseOrder;

	// TODO add other fields

}