package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.SupplierDTO;
import com.buildit.procurement.domain.model.Supplier;
import com.buildit.procurement.domain.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierService {

	@Autowired
	SupplierRepository repository;

	@Autowired
	SupplierAssembler assembler;

	public Supplier create(String name) {
		Supplier supplier = new Supplier();

		supplier.setName(name);

		repository.save(supplier);

		return supplier;
	}

	public Supplier getById(Long id) {
		Optional<Supplier> maybeSupplier = repository.findById(id);

		if (!maybeSupplier.isPresent()) {
			throw new IllegalArgumentException("Cannot find supplier by id: " + id);
		}

		return maybeSupplier.get();
	}

	public SupplierDTO read(Long id) {
		return assembler.toResource(getById(id));
	}

	public List<SupplierDTO> readAll() {
		List<Supplier> all = repository.findAll();

		return all.stream().map(s -> assembler.toResource(s)).collect(Collectors.toList());
	}

}