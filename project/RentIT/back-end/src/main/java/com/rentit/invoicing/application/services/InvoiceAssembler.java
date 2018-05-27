package com.rentit.invoicing.application.services;

import com.rentit.invoicing.application.dto.InvoiceDTO;
import com.rentit.invoicing.domain.model.Invoice;
import com.rentit.invoicing.rest.InvoiceController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class InvoiceAssembler extends ResourceAssemblerSupport<Invoice, InvoiceDTO> {

	public InvoiceAssembler() {
		super(InvoiceController.class, InvoiceDTO.class);
	}

	public InvoiceDTO toResource(Invoice invoice) {
		InvoiceDTO dto = createResourceWithId(invoice.getId(), invoice);

		dto.set_id(invoice.getId());
		dto.setDueDate(invoice.getDueDate());
		dto.setPayableAmount(invoice.getPayableAmount());
		dto.setPurchaseOrderId(invoice.getPurchaseOrderId());
		dto.setRejectionNote(invoice.getRejectionNote());

		return dto;
	}
}