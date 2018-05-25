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

import static java.util.Objects.requireNonNull;

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

	public SupplierDTO findOrCreateByName(String name) {
		requireNonNull(name);
		if (name.length() < 1) throw new IllegalArgumentException("Name is an empty string");
		List<Supplier> suppliers = repository.findByName(name);
		Supplier supplier;
		if (suppliers.isEmpty()) {
			supplier = create(name);
		} else {
			supplier = suppliers.get(0);
		}
		return assembler.toResource(supplier);
	}

}