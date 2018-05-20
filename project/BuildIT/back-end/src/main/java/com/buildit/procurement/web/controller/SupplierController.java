package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.SupplierDTO;
import com.buildit.procurement.application.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@CrossOrigin()
@RequestMapping("/api/suppliers")
public class SupplierController {

	@Autowired
	SupplierService service;

	@GetMapping
	public List<SupplierDTO> readAll() {
		List<SupplierDTO> suppliers = service.readAll();

		suppliers.forEach(s -> fixLinks(s));

		return suppliers;
	}

	@GetMapping("/{id}")
	public SupplierDTO readOne(@PathVariable Long id) {
		SupplierDTO supplier = service.readOne(id);

		fixLinks(supplier);

		return supplier;
	}

	private void fixLinks(SupplierDTO supplier) {
		supplier.removeLinks();

		supplier.getLinks().add(linkTo(
				methodOn(SupplierController.class)
						.readOne(supplier.get_id()))
				.withSelfRel());
	}

}