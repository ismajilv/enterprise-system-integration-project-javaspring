package com.rentit.invoicing.application.services;

import com.rentit.invoicing.application.dto.InvoiceDTO;
import com.rentit.invoicing.domain.model.Invoice;
import com.rentit.invoicing.domain.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceService {

	@Autowired
	InvoiceRepository repository;

	@Autowired
	InvoiceAssembler assembler;

	public InvoiceDTO readOne(Long id) {
		Optional<Invoice> maybeInvoice = repository.findById(id);

		if (!maybeInvoice.isPresent()) {
			throw new IllegalArgumentException("No invoice found by ID: " + id);
		}

		return assembler.toResource(maybeInvoice.get());
	}
	

}
