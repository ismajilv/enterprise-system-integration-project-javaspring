package com.buildit.common.application.dto;

import com.buildit.procurement.domain.enums.Role;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class EmployeeDTO extends ResourceSupport {

	Long _id;

	Role role;

	String firstName;

	String lastName;

}
