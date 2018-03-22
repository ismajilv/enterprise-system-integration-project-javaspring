package com.rentit.common.domain.validator;

import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.sales.domain.model.PurchaseOrder;
import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;

public class BusinessPeriodValidator  implements Validator {

	@Override
	public boolean supports(Class clazz) {
		return BusinessPeriod.class.equals(clazz);
	}

	@Override
	public void validate(@Nullable Object obj, Errors errors) {
		if (isNull(obj)) {
			errors.reject("null", "Business period is null");
		}

		BusinessPeriod bp = (BusinessPeriod) obj;

		if (isNull(bp.getStartDate())) {
			errors.rejectValue("startDate", "null", "Period is missing start date");
		} else if (isNull(bp.getEndDate())) {
			errors.rejectValue("endDate", "null", "Period is missing end date");
		} else if (bp.getEndDate().isBefore(bp.getStartDate())) {
			errors.rejectValue("endDate", "endDate.before.startDate", "Period has end date before start date");
		}
	}
}
