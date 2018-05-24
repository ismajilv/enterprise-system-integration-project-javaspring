package com.buildit.procurement.domain.enums;

public enum RentItPurchaseOrderStatus {

	PENDING, REJECTED, OPEN, CLOSED, PENDING_EXTENSION, CANCELLED;

	public POStatus convertToLocal() {
		switch (this) {
			case PENDING:
				return POStatus.PENDING;
			case OPEN:
			case CLOSED:
			case PENDING_EXTENSION:
				return POStatus.APPROVED;
			case REJECTED:
			case CANCELLED:
				return POStatus.REJECTED;
			default:
				throw new IllegalArgumentException("Unknown PO status: " + this);
		}
	}

}