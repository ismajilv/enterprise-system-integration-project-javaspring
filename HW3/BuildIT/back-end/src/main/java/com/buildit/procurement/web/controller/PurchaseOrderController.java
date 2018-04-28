package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.PurchaseOrderDTO;
import com.buildit.procurement.application.dto.SupplierDTO;
import com.buildit.procurement.application.services.PurchaseOrderService;
import com.buildit.procurement.application.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/orders")
public class PurchaseOrderController {

	@Autowired
	PurchaseOrderService service;

	@GetMapping
	public List<PurchaseOrderDTO> readAll() {
		List<PurchaseOrderDTO> suppliers = service.readAll();

		suppliers.forEach(s -> fixLinks(s));

		return suppliers;
	}

	private void fixLinks(PurchaseOrderDTO order) {
		order.removeLinks();
	}

}