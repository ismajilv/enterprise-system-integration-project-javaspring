package com.buildit.procurement.domain.model;

import com.buildit.procurement.domain.enums.InvoiceStatus;
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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	InvoiceStatus status;

	@Column(name = "due_date", nullable = false)
	LocalDate dueDate;

	@JoinColumn(name = "purchase_order_id", nullable = false)
	@OneToOne(optional = false)
	PurchaseOrder purchaseOrder;

	@Column(nullable = false)
	Boolean latePayment;

	@JoinColumn(name = "remittance_advice_id")
	@OneToOne
	RemittanceAdvice remittanceAdvice;

	@Column(nullable = false)
	BigDecimal payableAmount;

}