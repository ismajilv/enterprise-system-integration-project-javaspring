package com.rentit.invoicing.application.dto;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceDTO extends ResourceSupport {

	Long _id;

	Long purchaseOrderId;

	LocalDate dueDate;

	BigDecimal payableAmount;

	String rejectionNote;

}
