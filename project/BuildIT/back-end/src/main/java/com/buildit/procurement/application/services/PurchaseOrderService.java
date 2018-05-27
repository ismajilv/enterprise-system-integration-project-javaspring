package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.PurchaseOrderDTO;
import com.buildit.procurement.application.services.assemblers.PurchaseOrderAssembler;
import com.buildit.procurement.domain.model.PlantHireRequest;
import com.buildit.procurement.domain.model.PurchaseOrder;
import com.buildit.procurement.domain.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class PurchaseOrderService {

	@Autowired
	PurchaseOrderRepository repository;

	@Autowired
    PurchaseOrderAssembler assembler;

	@Autowired
	PlantHireRequestService plantHireRequestService;

	@Transactional
	public PurchaseOrder create(String href, Long plantHireRequestId, Long externalId) {
		requireNonNull(href);
		if (href.length() < 10) throw new IllegalArgumentException("Illegal href: " + href);

		PurchaseOrder purchaseOrder = new PurchaseOrder();

		PlantHireRequest plantHireRequest = plantHireRequestService.readModel(plantHireRequestId);

		purchaseOrder.setHref(href);
		purchaseOrder.setPlantHireRequest(plantHireRequest);
		purchaseOrder.setExternalId(externalId);

		purchaseOrder = repository.save(purchaseOrder);

		return purchaseOrder;
	}

	@Transactional(readOnly = true)
	public List<PurchaseOrderDTO> readAll() {
		return assembler.toResources(repository.findAll());
	}

	public PurchaseOrder readModel(String id) throws IllegalArgumentException {
		Optional<PurchaseOrder> maybePurchaseOrder = repository.findById(id);

		if (!maybePurchaseOrder.isPresent()) {
			throw new IllegalArgumentException("Cannot find purchase order by id: " + id);
		}

		return maybePurchaseOrder.get();
	}

	public PurchaseOrder findByExternalId(Long purchaseOrderExternalId) {
		List<PurchaseOrder> found = repository.findByExternalId(purchaseOrderExternalId);

		if (found.isEmpty()) {
			throw new IllegalArgumentException("No purchase order found with external ID: " + purchaseOrderExternalId);
		} else if (found.size() > 1) {
			throw new IllegalStateException("More than one purchase order found with external ID: " + purchaseOrderExternalId);
		}

		return found.get(0);
	}

}
