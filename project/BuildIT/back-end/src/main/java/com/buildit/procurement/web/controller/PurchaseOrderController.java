package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.PurchaseOrderDTO;
import com.buildit.procurement.application.services.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin()
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