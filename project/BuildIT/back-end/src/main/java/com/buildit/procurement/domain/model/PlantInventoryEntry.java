package com.buildit.procurement.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class PlantInventoryEntry {

	@Id // href is the identifier
	@URL
	String href;

	@Column
	Long externalId;

	@Column(nullable = false)
	String name;

	@JoinColumn(name = "supplier_id", nullable = false)
	@ManyToOne(optional = false)
	Supplier supplier;

}