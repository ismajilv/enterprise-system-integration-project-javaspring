package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.SupplierDTO;
import com.buildit.procurement.application.services.SupplierAssembler;
import com.buildit.procurement.domain.model.Supplier;
import com.buildit.procurement.domain.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    SupplierRepository repository;

    @Autowired
    SupplierAssembler assembler;

    @GetMapping
    public List<SupplierDTO> getAll() {
        List<Supplier> all = repository.findAll();

        return all.stream().map(s -> assembler.toResource(s)).collect(Collectors.toList());
    }

}