package com.rentit.invoicing.rest;

import com.rentit.invoicing.application.dto.InvoiceDTO;
import com.rentit.invoicing.application.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
@RequestMapping("/api/invoices")
public class InvoiceController {

	@Autowired
	InvoiceService service;

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {
		InvoiceDTO dto = service.readOne(id);

		return new ResponseEntity<>(
				new Resource<InvoiceDTO>(dto),
				new HttpHeaders(),
				HttpStatus.OK);
	}

}
