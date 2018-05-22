package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.InvoiceDTO;
import com.buildit.procurement.domain.enums.InvoiceStatus;
import com.buildit.procurement.domain.model.Invoice;
import com.buildit.procurement.domain.model.PurchaseOrder;
import com.buildit.procurement.domain.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class InvoiceService {

	@Autowired
	InvoiceRepository repository;

	@Autowired
	RemittanceAdviceService remittanceAdviceService;

	@Autowired
	PurchaseOrderService purchaseOrderService;

	@Transactional
	public InvoiceDTO add(String purchaseOrderHref, LocalDate dueDate) {
		PurchaseOrder plantHireRequest = purchaseOrderService.readModel(purchaseOrderHref);

		Invoice invoice = new Invoice();

		invoice.setPurchaseOrder(plantHireRequest);

		invoice.setDueDate(dueDate);

		invoice.setLatePayment(false);

		invoice.setStatus(InvoiceStatus.PENDING);

		invoice = repository.save(invoice);

		return null; // TODO assembler.toResource(invoice);
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

	public InvoiceDTO readOne(Long invoiceId) {
		return null; // TODO read from repository
	}

}