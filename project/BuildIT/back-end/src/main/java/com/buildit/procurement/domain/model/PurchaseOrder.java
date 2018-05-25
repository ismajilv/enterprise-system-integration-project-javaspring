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

<<<<<<< HEAD
	@JoinColumn(name = "invoice_id", nullable = false)
	@OneToOne(optional = false)
=======
	@OneToOne(mappedBy = "purchaseOrder")
>>>>>>> db27d439948c39c9c06729e4dca5e33e7c9bb672
	Invoice invoice;

}