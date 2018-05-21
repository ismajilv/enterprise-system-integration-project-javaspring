package com.buildit.procurement.domain.enums;

public enum InvoiceStatus {

	PENDING,

	ACCEPTED,

	REJECTED;

	public boolean isTransitionAllowed(InvoiceStatus newState) {
		if (InvoiceStatus.ACCEPTED.equals(newState)) {
			switch (this) {
				case PENDING:
					return true;
				case ACCEPTED:
					return false;
				case REJECTED:
					return false;
				default:
					throw new IllegalStateException("Unsupported invoice state: " + this);
			}
		}
		return true;
	}

}
