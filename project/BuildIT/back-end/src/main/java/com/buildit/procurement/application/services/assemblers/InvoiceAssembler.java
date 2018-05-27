package com.buildit.procurement.application.services.assemblers;

import com.buildit.procurement.web.controller.InvoiceController;
import org.springframework.stereotype.Service;
import com.buildit.procurement.application.dto.InvoiceDTO;
import com.buildit.procurement.application.dto.RemittanceAdviceDTO;
import com.buildit.procurement.domain.model.Invoice;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

@Service
public class InvoiceAssembler extends ResourceAssemblerSupport<Invoice, InvoiceDTO>{
    public InvoiceAssembler() { super(InvoiceController.class, InvoiceDTO.class);}

    @Override
    public InvoiceDTO toResource(Invoice invoice) {
        InvoiceDTO dto = createResourceWithId(invoice.getId(), invoice);

        dto.set_id(invoice.getId());
        dto.setDueDate(invoice.getDueDate());
        dto.setLatePayment(invoice.getLatePayment());
        dto.setStatus(invoice.getStatus());

        PurchaseOrderAssembler poAssembler = new PurchaseOrderAssembler();
        dto.setPurchaseOrder(poAssembler.toResource(invoice.getPurchaseOrder()));

        return dto;
    }
}


