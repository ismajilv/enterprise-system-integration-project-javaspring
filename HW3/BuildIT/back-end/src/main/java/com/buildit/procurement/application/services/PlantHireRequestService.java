package com.buildit.procurement.application.services;

import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.common.domain.model.Money;
import com.buildit.procurement.domain.enums.PHRStatus;
import com.buildit.procurement.domain.model.ConstructionSite;
import com.buildit.procurement.domain.model.PlantHireRequest;
import com.buildit.procurement.domain.model.PlantInventoryEntry;
import com.buildit.procurement.domain.model.Supplier;
import com.buildit.procurement.domain.repository.PlantHireRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

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

	public PlantHireRequest addRequest(Long constructionSiteId, Long supplierId, String plantHref, LocalDate start, LocalDate end, Money rentalCost) {
		ConstructionSite constructionSite = constructionSiteService.getById(constructionSiteId);
		Supplier supplier = supplierService.getById(supplierId);
		PlantInventoryEntry plant = plantInventoryEntryService.getByHref(plantHref);

		PlantHireRequest plantHireRequest = new PlantHireRequest();

		plantHireRequest.setStatus(PHRStatus.PENDING);
		plantHireRequest.setConstructionSite(constructionSite);
		plantHireRequest.setComments(new ArrayList<>());
		plantHireRequest.setRentalPeriod(BusinessPeriod.of(start, end));
		plantHireRequest.setSupplier(supplier);
		plantHireRequest.setPlant(plant);
		plantHireRequest.setRentalCost(rentalCost);

		plantHireRequestRepository.save(plantHireRequest);

		return plantHireRequest;
	}

	public PlantHireRequest getById(Long id) {
		Optional<PlantHireRequest> maybePlantHireRequest = plantHireRequestRepository.findById(id);

		if (!maybePlantHireRequest.isPresent()) {
			throw new IllegalArgumentException("Cannot find plant hire request with id: " + id);
		}

		return maybePlantHireRequest.get();
	}

}
