package com.rentit.sales.domain.validator;

import com.rentit.common.domain.validator.BusinessPeriodValidator;
import com.rentit.sales.domain.model.PurchaseOrder;
import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;

import static java.util.Objects.isNull;

public class PurchaseOrderValidator implements Validator {

	private BusinessPeriodValidator businessPeriodValidator = new BusinessPeriodValidator();

	@Override
	public boolean supports(Class clazz) {
		return PurchaseOrder.class.equals(clazz);
	}

	@Override
	public void validate(@Nullable Object obj, Errors errors) {
		if (isNull(obj)) {
			errors.reject("null", "Purchase order is null");
		}

		PurchaseOrder po = (PurchaseOrder) obj;

		if (!isNull(po.getId()) && po.getId() < 1) {
			errors.rejectValue("id", "non.positive", "Purchase order has non-positive Id");
		}

		if (isNull(po.getPlant())) {
			errors.rejectValue("plant", "null", "Purchase order has null for plant");
		} else if (po.getPlant().getId() < 1) {
			errors.rejectValue("plant", "missing.id", "Purchase order has plant with null Id");
		}

		if (isNull(po.getStatus())) {
			errors.rejectValue("status", "null", "Purchase order has null for status");
		}

		switch (po.getStatus()) {
			case PENDING:
				if (!isNull(po.getRentalPeriod())
						&& !isNull(po.getRentalPeriod().getStartDate())
						&& po.getRentalPeriod().getStartDate().isBefore(LocalDate.now())) {
					errors.rejectValue("rentalPeriod", "past", "Purchase order must be for future");
				}
				break;
			case OPEN:
			case CLOSED:
				if (isNull(po.getTotal())) {
					errors.rejectValue("total", "null.for.open.closed", "Purchase order is in state " + po.getStatus() + " but has null for total");
				}
				// falls through
			default:
				// anything but PENDING should already have an Id
				if (isNull(po.getId())) {
					errors.rejectValue("id", "null", "Purchase order has null for Id");
				}
		}

		if (!isNull(po.getTotal()) && po.getTotal().signum() < 1) {
			errors.rejectValue("total", "non.positive", "Purchase order total is not a positive number");
		}

		try {
			errors.pushNestedPath("rentalPeriod");
			ValidationUtils.invokeValidator(this.businessPeriodValidator, po.getRentalPeriod(), errors);
		} finally {
			errors.popNestedPath();
		}

	}

}
