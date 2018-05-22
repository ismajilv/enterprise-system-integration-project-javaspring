package com.buildit.procurement.domain.enums;

public enum RentItPurchaseOrderStatus {

	PENDING, REJECTED, OPEN, CLOSED, PENDING_EXTENSION;

	public PHRStatus convertToPHRStatus() {
		switch (this) {
			case PENDING:
				return PHRStatus.PENDING_WORKS_ENGINEER_APPROVAL;
			case OPEN:
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