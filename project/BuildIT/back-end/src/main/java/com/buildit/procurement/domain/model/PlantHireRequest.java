package com.buildit.procurement.domain.model;

import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.common.domain.model.Employee;
import com.buildit.procurement.domain.enums.PHRStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
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

	@Column(nullable = false)
	BigDecimal rentalCost;

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
	@JsonIgnore
	@JoinColumn(name = "purchase_order_id")
	@OneToOne
	PurchaseOrder purchaseOrder;
	@JoinColumn(name = "requesting_site_engineer_id", nullable = false)
	@ManyToOne(optional = false)
	Employee requestingSiteEngineer;
	@JoinColumn(name = "approving_works_engineer_id")
	@ManyToOne
	Employee approvingWorksEngineer;
	@OneToOne(mappedBy = "plantHireRequest")
	ExtensionRequest extensionRequest;
	//</editor-fold>

	public void setStatus(PHRStatus newStatus) {
		if (!isNull(status)) {
			switch (status) {
				case PENDING_WORKS_ENGINEER_APPROVAL:
					this.status = newStatus;
					break;
				default:
					this.status = newStatus;
					//until new rentit statuses are handled don't throw exception
					//throw new IllegalStateException(String.format("Illegal status change, cannot go from %s to %s", status, newStatus));
			}
		} else {
			this.status = newStatus;
		}
	}

}
