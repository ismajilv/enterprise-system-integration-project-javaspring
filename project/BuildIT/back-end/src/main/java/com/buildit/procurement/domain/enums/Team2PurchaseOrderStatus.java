package com.buildit.procurement.domain.enums;

public enum Team2PurchaseOrderStatus {

	OPEN,

	PENDING_APPROVAL,

	CANCELLED,

	ACCEPTED,

	REJECTED,

	REJECTED_BY_CUSTOMER,

	PLANT_DISPATCHED,

	PLANT_DELIVERED,

	PLANT_RETURNED,

	PENDING_EXTENSION,

	INVOICED;

	public PHRStatus convertToPHRStatus() {
		switch (this) {
			case PENDING_APPROVAL:
			case OPEN:
				return PHRStatus.PENDING_RENTAL_PARTNER_APPROVAL;
			case CANCELLED:
				return PHRStatus.CANCELLED;
			case ACCEPTED:
			case PLANT_DISPATCHED:
				return PHRStatus.ACCEPTED_BY_RENTAL_PARTNER;
			case REJECTED:
				return PHRStatus.REJECTED;
			case REJECTED_BY_CUSTOMER:
				return PHRStatus.REJECTED;
			case PLANT_DELIVERED:
			case PLANT_RETURNED:
			case INVOICED:
				return PHRStatus.PLANT_DELIVERED;

			default:
				throw new IllegalArgumentException("Unknown PO status: " + this);
		}
	}

}