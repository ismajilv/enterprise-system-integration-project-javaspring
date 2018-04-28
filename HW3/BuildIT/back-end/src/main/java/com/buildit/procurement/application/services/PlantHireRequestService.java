package com.buildit.procurement.application.services;

import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.common.domain.model.Money;
import com.buildit.procurement.application.dto.CommentDTO;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
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

	public PlantHireRequestDTO addRequest(Long constructionSiteId, Long supplierId, String plantHref, BusinessPeriod rentalPeriod, Money rentalCost) {
		ConstructionSite constructionSite = constructionSiteService.getById(constructionSiteId);
		Supplier supplier = supplierService.getById(supplierId);
		PlantInventoryEntry plant = plantInventoryEntryService.getByHref(plantHref);

		PlantHireRequest plantHireRequest = new PlantHireRequest();

		plantHireRequest.setStatus(PHRStatus.PENDING);
		plantHireRequest.setConstructionSite(constructionSite);
		plantHireRequest.setComments(new ArrayList<>());
		plantHireRequest.setRentalPeriod(rentalPeriod);
		plantHireRequest.setSupplier(supplier);
		plantHireRequest.setPlant(plant);
		plantHireRequest.setRentalCost(rentalCost);

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

		// TODO generate and send a new PO to RentIT

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
