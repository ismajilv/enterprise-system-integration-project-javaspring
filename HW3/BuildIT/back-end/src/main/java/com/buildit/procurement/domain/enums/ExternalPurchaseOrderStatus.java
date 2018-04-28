package com.buildit.procurement.domain.enums;

public enum ExternalPurchaseOrderStatus {

	PENDING_APPROVE,

	APPROVED,

	REFUSED;

	public POStatus convertToLocal() {
		switch (this) {
			case PENDING_APPROVE:
				return POStatus.PENDING;
			case APPROVED:
				return POStatus.APPROVED;
			case REFUSED:
				return POStatus.REJECTED;
			default:
				throw new IllegalArgumentException("Unknown PO status: " + this);
		}
	}

}