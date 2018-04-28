package com.buildit.procurement.domain.model;

import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.common.domain.model.Money;
import com.buildit.procurement.domain.enums.PHRStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.isNull;

@Entity
@Data
public class PlantHireRequest {

	@Id
	@GeneratedValue
	Long id;

	//<editor-fold desc="Constants">
	@Embedded
	@Column(nullable = false)
	BusinessPeriod rentalPeriod;

	@Embedded
	@Column(nullable = false)
	Money rentalCost;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	PHRStatus status;
	//<editor-fold desc="Externals">
	@JoinColumn(name = "plant_href", nullable = false)
	@ManyToOne(optional = false)
	PlantInventoryEntry plant;
	//</editor-fold>
	@JoinColumn(name = "supplier_id", nullable = false)
	@ManyToOne(optional = false)
	Supplier supplier;
	//<editor-fold desc="Internals">
	@JoinColumn(name = "construction_site_id", nullable = false)
	@ManyToOne(optional = false)
	ConstructionSite constructionSite;
	//</editor-fold>
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "plantHireRequest", cascade = CascadeType.ALL)
	Collection<Comment> comments = new ArrayList<>();

	// not really needed
	// @JoinColumn(name = "requesting_site_engineer_id")
	// @OneToOne
	// Employee requestingSiteEngineer;
	@JoinColumn(name = "purchase_order_id")
	@OneToOne
	PurchaseOrder purchaseOrder;
	//</editor-fold>

	public void setStatus(PHRStatus newStatus) {
		if (!isNull(status)) {
			switch (status) {
				case PENDING:
					this.status = newStatus;
					break;
				default:
					throw new IllegalStateException(String.format("Illegal status change, cannot go from %s to %s", status, newStatus));
			}
		} else {
			this.status = newStatus;
		}
	}

}
