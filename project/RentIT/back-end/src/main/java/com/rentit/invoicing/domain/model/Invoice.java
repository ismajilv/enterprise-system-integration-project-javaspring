package com.rentit.invoicing.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;

	@Column(nullable = false)
	Long purchaseOrderId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	InvoiceStatus status;

	@Column(name = "due_date", nullable = false)
	LocalDate dueDate;

	@JoinColumn(name = "remittance_advice_id")
	@OneToOne
	RemittanceAdvice remittanceAdvice;

	@Column(nullable = false)
	BigDecimal payableAmount;

	@Column
	String rejectionNote;

}