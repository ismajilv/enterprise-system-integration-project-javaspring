package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.ConstructionSiteDTO;
import org.springframework.http.ResponseEntity;
import com.buildit.procurement.application.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;
import com.buildit.procurement.application.dto.InvoiceDTO;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@CrossOrigin()
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    InvoiceService service;

    @GetMapping
    public List<InvoiceDTO> readAll() {
        List<InvoiceDTO> invoices = service.readAll();

        invoices.forEach(inv -> fixLinks(inv));

        return invoices;
    }

    @GetMapping("/{id}")
    public InvoiceDTO readOne(@PathVariable Long id) {
        InvoiceDTO invoiceDTO = service.readOne(id);

        fixLinks(invoiceDTO);

        return invoiceDTO;
    }

    @PostMapping("/{id}/accept")
    public void accept(@PathVariable Long id) {
        service.accept(id);
    }

    private void fixLinks(InvoiceDTO invoice) {
        invoice.removeLinks();
        invoice.getLinks().add(linkTo(
                methodOn(InvoiceController.class)
                        .readOne(invoice.get_id()))
                .withSelfRel());
    }

}