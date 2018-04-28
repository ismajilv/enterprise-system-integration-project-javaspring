package com.buildit.procurement.web.controller;

import com.buildit.common.application.dto.EmployeeDTO;
import com.buildit.procurement.application.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
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