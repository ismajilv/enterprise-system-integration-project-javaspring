package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.InvoiceDTO;
import com.buildit.procurement.application.services.assemblers.InvoiceAssembler;
import com.buildit.procurement.domain.enums.InvoiceStatus;
import com.buildit.procurement.domain.model.Invoice;
import com.buildit.procurement.domain.model.PurchaseOrder;
import com.buildit.procurement.domain.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

	@Autowired
	InvoiceRepository repository;

	@Autowired
	RemittanceAdviceService remittanceAdviceService;

	@Autowired
	PurchaseOrderService purchaseOrderService;

	@Autowired
	InvoiceAssembler assembler;

	@Transactional
	public InvoiceDTO add(String purchaseOrderHref, LocalDate dueDate) {
		PurchaseOrder plantHireRequest = purchaseOrderService.readModel(purchaseOrderHref);

		Invoice invoice = new Invoice();

		invoice.setPurchaseOrder(plantHireRequest);

		invoice.setDueDate(dueDate);

		invoice.setLatePayment(false);

		invoice.setStatus(InvoiceStatus.PENDING);

		invoice = repository.save(invoice);

		return null; // TODO assemblers.toResource(invoice);
	}

	@Transactional
	public void accept(Long invoiceId) {
		Optional<Invoice> maybeInvoice = repository.findById(invoiceId);

		if (!maybeInvoice.isPresent()) {
			throw new IllegalArgumentException("No invoice found by ID: " + maybeInvoice);
		}

		Invoice invoice = maybeInvoice.get();

		if (invoice.getStatus().isTransitionAllowed(InvoiceStatus.ACCEPTED)) {
			remittanceAdviceService.create(invoiceId, "Paid to bank account");
			invoice.setStatus(InvoiceStatus.ACCEPTED);
			invoice = repository.save(invoice);
		} else {
			throw new IllegalStateException("Cannot shift invoice from state " + invoice.getStatus() + " to state " + InvoiceStatus.ACCEPTED);
		}
	}

	@Transactional(readOnly = true)
	public Collection<InvoiceDTO> readAll() {
		Collection<Invoice> all;

		all = repository.findAll();

		return all.stream().map(inv -> assembler.toResource(inv)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public InvoiceDTO readOne(Long invoiceId) { return assembler.toResource(readModel(invoiceId));}

	@Transactional(readOnly = true)
	public Invoice readModel(Long id) {
		Optional<Invoice> maybeInvoice = repository.findById(id);

		if (!maybeInvoice.isPresent()) {
			throw new IllegalArgumentException("Cannot invoice with id: " + id);
		}

		return maybeInvoice.get();
	}

}