package com.buildit.procurement.application.services;

import com.buildit.common.application.service.BusinessPeriodAssembler;
import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.common.domain.model.Money;
import com.buildit.procurement.application.dto.ExternalPurchaseOrderDTO;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.application.dto.ExternalCreatePORequestDTO;
import com.buildit.procurement.domain.enums.PHRStatus;
import com.buildit.procurement.domain.enums.POStatus;
import com.buildit.procurement.domain.model.*;
import com.buildit.procurement.domain.repository.PlantHireRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlantHireRequestService {

	@Autowired
	PlantHireRequestRepository plantHireRequestRepository;

	@Autowired
	ConstructionSiteService constructionSiteService;

	@Autowired
	SupplierService supplierService;

	@Autowired
	PlantInventoryEntryService plantInventoryEntryService;

	@Autowired
	PlantHireRequestAssembler assembler;

	@Autowired
	BusinessPeriodAssembler businessPeriodAssembler;

	@Autowired
	ExternalIntegrationsService externalIntegrationsService;

	@Autowired
	PurchaseOrderService purchaseOrderService;

	public PlantHireRequestDTO addRequest(Long constructionSiteId, Long supplierId, String plantHref, BusinessPeriod rentalPeriod) {
		ConstructionSite constructionSite = constructionSiteService.readModel(constructionSiteId);
		Supplier supplier = supplierService.readModel(supplierId);
		PlantInventoryEntryDTO plantDTO = plantInventoryEntryService.fetchByHref(plantHref);
		BigDecimal rentalCost = plantDTO.getPricePerDay().getTotal().multiply(BigDecimal.valueOf(rentalPeriod.getNoOfDays()));
		PlantInventoryEntry plant = plantInventoryEntryService.readOrCreateModel(plantHref);

		PlantHireRequest plantHireRequest = new PlantHireRequest();

		plantHireRequest.setStatus(PHRStatus.PENDING);
		plantHireRequest.setConstructionSite(constructionSite);
		plantHireRequest.setComments(new ArrayList<>());
		plantHireRequest.setRentalPeriod(rentalPeriod);
		plantHireRequest.setSupplier(supplier);
		plantHireRequest.setPlant(plant);
		plantHireRequest.setRentalCost(Money.of(rentalCost));

		plantHireRequest = plantHireRequestRepository.save(plantHireRequest);

		return assembler.toResource(plantHireRequest);
	}

	public PlantHireRequestDTO getByIdAsDTO(Long id) {
		return assembler.toResource(getById(id));
	}

	public PlantHireRequest getById(Long id) {
		Optional<PlantHireRequest> maybePlantHireRequest = plantHireRequestRepository.findById(id);

		if (!maybePlantHireRequest.isPresent()) {
			throw new IllegalArgumentException("Cannot find plant hire request with id: " + id);
		}

		return maybePlantHireRequest.get();
	}

	public List<PlantHireRequestDTO> getAll() {
		List<PlantHireRequest> all = plantHireRequestRepository.findAll();

		return all.stream().map(phr -> assembler.toResource(phr)).collect(Collectors.toList());
	}

	public PlantHireRequestDTO accept(Long id) {
		PlantHireRequest request = getById(id);

		ExternalCreatePORequestDTO rentItPO = ExternalCreatePORequestDTO.of(
				request.getPlant().getHref(),
				businessPeriodAssembler.toResource(request.getRentalPeriod())
		);

		ExternalPurchaseOrderDTO createdPO = externalIntegrationsService.createPO(rentItPO);

		POStatus status = createdPO.getStatus().convertToLocal();
		
		PurchaseOrder purchaseOrder = purchaseOrderService.create(createdPO.getPoHref(), status);

		request.setPurchaseOrder(purchaseOrder);
		
		request.setStatus(PHRStatus.ACCEPTED);

		request = plantHireRequestRepository.save(request);

		return assembler.toResource(request);
	}

	public PlantHireRequestDTO reject(Long id) {
		PlantHireRequest request = getById(id);

		request.setStatus(PHRStatus.REJECTED);

		request = plantHireRequestRepository.save(request);

		return assembler.toResource(request);
	}

}
