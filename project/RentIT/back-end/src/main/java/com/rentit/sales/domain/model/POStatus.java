package com.rentit.sales.domain.model;

public enum POStatus {

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

	public boolean isTransitionAllowed(POStatus newStatus) {
		if (this.equals(newStatus)) {
			// allow transitioning to itself
			return true;
		}

		switch (this) {
			case PENDING_APPROVAL:
				switch (newStatus) {
					case CANCELLED:
					case ACCEPTED:
					case REJECTED:
						return true;
					default:
						return false;

				}
			case ACCEPTED:
				switch (newStatus) {
					case CANCELLED:
					case PLANT_DISPATCHED:
						return true;
					default:
						return false;

				}

			case PLANT_DISPATCHED:
				switch (newStatus) {
					case REJECTED_BY_CUSTOMER:
					case PLANT_DELIVERED:
						return true;
					default:
						return false;

				}

			case PLANT_DELIVERED:
				switch (newStatus) {
					case PENDING_EXTENSION:
					case PLANT_RETURNED:
						return true;
					default:
						return false;

				}
			case PENDING_EXTENSION:
				switch (newStatus) {
					case PLANT_DELIVERED:
						return true;
					default:
						return false;

				}
			case PLANT_RETURNED:
				switch (newStatus) {
					case INVOICED:
						return true;
					default:
						return false;

				}

			case CANCELLED:
			case REJECTED:
			case REJECTED_BY_CUSTOMER:
			case INVOICED:
				// final states
				return false;

			default:
				throw new IllegalStateException("Unknown PO status: " + this);
		}
	}

}
