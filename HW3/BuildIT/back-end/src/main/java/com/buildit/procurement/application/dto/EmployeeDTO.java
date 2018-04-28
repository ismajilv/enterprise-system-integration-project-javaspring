package com.buildit.procurement.application.dto;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class EmployeeDTO extends ResourceSupport {

	Long _id;

	// TODO add other fields & to assembler

}