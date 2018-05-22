package com.buildit.procurement.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
// TODO bind to PHR or PO?
public class ExtensionRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;

	@Column(name = "new_end_date", nullable = false)
	LocalDate newEndDate;

	@JoinColumn(name = "plant_hire_request_id", nullable = false)
	@ManyToOne(optional = false)
	PlantHireRequest plantHireRequest;

	@Column
	String comment; // Sent & filled from RentIt if this ER gets rejected

}