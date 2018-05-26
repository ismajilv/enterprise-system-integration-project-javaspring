package com.buildit.procurement.domain.enums;

public enum RentItPurchaseOrderStatus {

	PENDING, REJECTED, OPEN, CLOSED, PENDING_EXTENSION, CANCELLED, DISPATCHED
	,RETURNED, REJECTED_BY_CUSTOMER, DELIVERED;


	public PHRStatus convertToPHRStatus() {
		switch (this) {
			case PENDING:
				return PHRStatus.PENDING_WORKS_ENGINEER_APPROVAL;
			case DISPATCHED:
			case DELIVERED:
				return PHRStatus.PLANT_DELIVERED;
			case REJECTED_BY_CUSTOMER:
				return PHRStatus.CANCELLED;
			case RETURNED:
				return PHRStatus.PAID;
			case CANCELLED:
				return PHRStatus.CANCELLED;
			case OPEN:
				return  PHRStatus.ACCEPTED_BY_RENTAL_PARTNER;
			case CLOSED:
			case PENDING_EXTENSION:
				return PHRStatus.PENDING_RENTAL_PARTNER_APPROVAL;
			case REJECTED:
				return PHRStatus.REJECTED;

			default:
				throw new IllegalArgumentException("Unknown PO status: " + this);
		}
	}

}