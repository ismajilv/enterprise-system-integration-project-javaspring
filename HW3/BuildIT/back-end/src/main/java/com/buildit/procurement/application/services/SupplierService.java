package com.buildit.procurement.application.services;

import com.buildit.procurement.domain.model.Comment;
import com.buildit.procurement.domain.model.ConstructionSite;
import com.buildit.procurement.domain.model.PlantHireRequest;
import com.buildit.procurement.domain.model.Supplier;
import com.buildit.procurement.domain.repository.CommentRepository;
import com.buildit.procurement.domain.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplierService {

	@Autowired
	SupplierRepository supplierRepository;

	public Supplier create(String name) {
		Supplier supplier = new Supplier();

		supplier.setName(name);

		supplierRepository.save(supplier);

		return supplier;
	}

	public Supplier getById(Long id) {
		Optional<Supplier> maybeSupplier = supplierRepository.findById(id);

		if (!maybeSupplier.isPresent()) {
			throw new IllegalArgumentException("Cannot find supplier by id: " + id);
		}

		return maybeSupplier.get();
	}

}