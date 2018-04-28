package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.ConstructionSiteDTO;
import com.buildit.procurement.application.dto.SupplierDTO;
import com.buildit.procurement.application.services.SupplierAssembler;
import com.buildit.procurement.application.services.SupplierService;
import com.buildit.procurement.domain.model.Supplier;
import com.buildit.procurement.domain.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    SupplierService service;

    @GetMapping
    public List<SupplierDTO> readAll() {
        List<SupplierDTO> suppliers = service.readAll();

        suppliers.forEach(s -> fixLinks(s));

        return suppliers;
    }

    @GetMapping("/{id}")
    public SupplierDTO readOne(@PathVariable Long id) {
        SupplierDTO supplier = service.read(id);

        fixLinks(supplier);

        return supplier;
    }

    private void fixLinks(SupplierDTO supplier) {
        supplier.removeLinks();

        supplier.getLinks().add(linkTo(
                methodOn(SupplierController.class)
                        .readOne(supplier.get_id()))
                .withSelfRel());
    }

}