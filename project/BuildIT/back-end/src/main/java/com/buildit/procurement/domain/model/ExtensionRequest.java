package com.buildit.procurement.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class ExtensionRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;

	@Column(name = "new_end_date", nullable = false)
	LocalDate newEndDate;

	@JoinColumn(name = "plant_hire_request_id", nullable = false)
	@OneToOne(optional = false)
	PlantHireRequest plantHireRequest;

	@Column
	String rejectionComment; // Sent & filled from RentIt if this ER gets rejected

}