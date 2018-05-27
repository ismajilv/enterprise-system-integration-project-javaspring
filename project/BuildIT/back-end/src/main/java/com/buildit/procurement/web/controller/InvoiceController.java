package com.buildit.procurement.web.controller;

import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.buildit.procurement.application.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;
import com.buildit.procurement.application.dto.InvoiceDTO;

import java.util.Collection;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static java.util.Objects.isNull;

@RestController
@CrossOrigin()
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    InvoiceService service;

    @GetMapping
    public ResponseEntity<Resources<InvoiceDTO>> readAll() {
        Collection<InvoiceDTO> invoices = service.readAll();

        invoices.forEach(inv -> fixLinks(inv));

        return new ResponseEntity<>(
                new Resources<>(invoices),
                new HttpHeaders(),
                HttpStatus.OK
        );
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
        if (!isNull(invoice.getPurchaseOrder())) {
            invoice.getPurchaseOrder().removeLinks();
        }
    }

}