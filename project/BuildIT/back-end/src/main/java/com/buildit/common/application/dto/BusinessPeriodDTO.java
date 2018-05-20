package com.buildit.common.application.dto;

import com.buildit.common.domain.model.BusinessPeriod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
// @Value(staticConstructor = "of")
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class BusinessPeriodDTO extends ResourceSupport {

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	LocalDate startDate;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	LocalDate endDate;

	public BusinessPeriod toModel() {
		return BusinessPeriod.of(startDate, endDate);
	}

}