package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.SupplierDTO;
import com.buildit.procurement.application.services.assemblers.SupplierAssembler;
import com.buildit.procurement.domain.model.Supplier;
import com.buildit.procurement.domain.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

	@Autowired
	SupplierRepository repository;

	@Autowired
    SupplierAssembler assembler;

	@Transactional
	public Supplier create(String name) {
		Supplier supplier = new Supplier();

		supplier.setName(name);

		repository.save(supplier);

		return supplier;
	}

	@Transactional(readOnly = true)
	public Supplier readModel(Long id) {
		Optional<Supplier> maybeSupplier = repository.findById(id);

		if (!maybeSupplier.isPresent()) {
			throw new IllegalArgumentException("Cannot find supplier by id: " + id);
		}

		return maybeSupplier.get();
	}

	@Transactional(readOnly = true)
	public List<SupplierDTO> readAll() {
		return assembler.toResources(repository.findAll());
	}

	@Transactional(readOnly = true)
	public SupplierDTO readOne(Long id) {
		return assembler.toResource(readModel(id));
	}

	public Supplier getFirstAsModel() {
		return repository.findAll().iterator().next();
	}

}