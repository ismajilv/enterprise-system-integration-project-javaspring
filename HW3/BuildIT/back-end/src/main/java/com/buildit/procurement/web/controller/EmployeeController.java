package com.buildit.procurement.web.controller;

import com.buildit.common.application.dto.EmployeeDTO;
import com.buildit.procurement.application.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
	EmployeeService service;

	@GetMapping("/{id}")
	public EmployeeDTO readOne(@PathVariable Long id) {
		EmployeeDTO employee = service.readOne(id);

		fixLinks(employee);

		return employee;
	}

	private void fixLinks(EmployeeDTO dto) {
		dto.removeLinks();

		dto.getLinks().add(linkTo(
				methodOn(EmployeeController.class)
						.readOne(dto.get_id()))
				.withSelfRel());
	}

}