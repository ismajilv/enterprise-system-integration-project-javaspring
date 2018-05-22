package com.buildit.procurement.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ExtensionRequestDTO extends ResourceSupport {

	Long _id;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	LocalDate newEndDate;

	String comment;

	@Override
	public boolean hasLinks() {
		return false;
	}

	@Override
	public boolean hasLink(String rel) {
		return false;
	}

}