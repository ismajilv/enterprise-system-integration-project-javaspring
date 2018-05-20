package com.buildit.procurement.domain.enums;

public enum RentItPurchaseOrderStatus {

	PENDING, REJECTED, OPEN, CLOSED, PENDING_EXTENSION;

	public POStatus convertToLocal() {
		switch (this) {
			case PENDING:
				return POStatus.PENDING;
			case OPEN:
			case CLOSED:
			case PENDING_EXTENSION:
				return POStatus.APPROVED;
			case REJECTED:
				return POStatus.REJECTED;
			default:
				throw new IllegalArgumentException("Unknown PO status: " + this);
		}
	}

}