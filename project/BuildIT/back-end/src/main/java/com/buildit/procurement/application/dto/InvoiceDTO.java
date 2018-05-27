package com.buildit.procurement.application.dto;

import com.buildit.procurement.domain.enums.InvoiceStatus;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDate;

@Data
public class InvoiceDTO extends ResourceSupport {

	Long _id;

	InvoiceStatus status;

	LocalDate dueDate;

	Boolean latePayment;

	PurchaseOrderDTO purchaseOrder;

}