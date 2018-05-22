package com.buildit.procurement.domain.model;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;

@Entity
@Data
public class PurchaseOrder {

	@Id // It's the identifier
	@URL
	String href;

	@Column
	Long externalId;

	@JoinColumn(name = "plant_hire_request_id", nullable = false)
	@OneToOne(optional = false)
	PlantHireRequest plantHireRequest;

	@OneToOne(mappedBy = "purchaseOrder")
	Invoice invoice;

}