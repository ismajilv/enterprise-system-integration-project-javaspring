package com.rentit.invoicing.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class RemittanceAdvice {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;

	@Column
	String note;

	@JoinColumn(name = "invoice_id", nullable = false)
	@OneToOne(optional = false)
	Invoice invoice;

}