package com.buildit.procurement.domain.model;

import com.buildit.procurement.domain.enums.POStatus;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
public class PurchaseOrder {

	@Id // It's the identifier
	@URL
	String href;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	POStatus status;

	@JoinColumn(name = "plant_hire_request_id", nullable = false)
	@OneToOne(optional = false)
	PlantHireRequest plantHireRequest;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	Collection<Invoice> invoices = new ArrayList<>();

}