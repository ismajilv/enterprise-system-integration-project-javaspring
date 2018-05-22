package com.buildit.procurement.application.services;

import com.buildit.common.application.service.BusinessPeriodAssembler;
import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.common.domain.model.Employee;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.application.dto.RentItPurchaseOrderDTO;
import com.buildit.procurement.application.services.assemblers.PlantHireRequestAssembler;
import com.buildit.procurement.domain.enums.PHRStatus;
import com.buildit.procurement.domain.enums.RentItPurchaseOrderStatus;
import com.buildit.procurement.domain.enums.Role;
import com.buildit.procurement.domain.model.*;
import com.buildit.procurement.domain.repository.PlantHireRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Service
public class PlantHireRequestService {

	@Autowired
	PlantHireRequestRepository repository;

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
	RentItService rentItService;

	@Autowired
	PurchaseOrderService purchaseOrderService;

	@Autowired
	EmployeeService employeeService;

	@Transactional
	public PlantHireRequestDTO updateRequest(Long id,
											 Long constructionSiteId,
											 Long supplierId,
											 String plantHref,
											 BusinessPeriod rentalPeriod) {
		PlantHireRequest request = readModel(id);

		if (!isNull(constructionSiteId)) {
			ConstructionSite constructionSite = constructionSiteService.readModel(constructionSiteId);

			request.setConstructionSite(constructionSite);
		}

		if (!isNull(supplierId)) {
			Supplier supplier = supplierService.readModel(supplierId);

			request.setSupplier(supplier);
		}

		if (!isNull(rentalPeriod)) {
			request.setRentalPeriod(rentalPeriod);
		}

		if (!isNull(plantHref)) {
			PlantInventoryEntry plant = plantInventoryEntryService.readOrCreateModel(plantHref);

			request.setPlant(plant);
		}

		BigDecimal cost = calculateCost(request.getPlant().getHref(), request.getRentalPeriod());

		request.setRentalCost(cost);

		request = repository.save(request);

		return assembler.toResource(request);
	}

	@Transactional
	public PlantHireRequestDTO addRequest(Long constructionSiteId,
										  Long supplierId,
										  String plantHref,
										  BusinessPeriod rentalPeriod) {
		PlantInventoryEntry plant = plantInventoryEntryService.readOrCreateModel(plantHref);

		PlantHireRequest plantHireRequest = new PlantHireRequest();

		plantHireRequest.setStatus(PHRStatus.PENDING_WORKS_ENGINEER_APPROVAL);

		ConstructionSite constructionSite = constructionSiteService.readModel(constructionSiteId);
		plantHireRequest.setConstructionSite(constructionSite);
		plantHireRequest.setComments(new ArrayList<>());
		plantHireRequest.setRentalPeriod(rentalPeriod);

		Supplier supplier = supplierService.readModel(supplierId);
		plantHireRequest.setSupplier(supplier);
		plantHireRequest.setPlant(plant);

		plantHireRequest.setRentalCost(calculateCost(plantHref, rentalPeriod));

		Employee requestingSiteEngineer = employeeService.getLoggedInEmployee(Role.SITE_ENGINEER);
		plantHireRequest.setRequestingSiteEngineer(requestingSiteEngineer);

		plantHireRequest = repository.save(plantHireRequest);

		return assembler.toResource(plantHireRequest);
	}

	private BigDecimal calculateCost(String plantHref, BusinessPeriod rentalPeriod) {
		PlantInventoryEntryDTO plantDTO = plantInventoryEntryService.fetchByHref(plantHref);

		BigDecimal rentalCost = plantDTO.getPricePerDay().multiply(BigDecimal.valueOf(rentalPeriod.getNoOfDays()));

		return rentalCost;
	}

	@Transactional(readOnly = true)
	public PlantHireRequestDTO readOne(Long id) {
		return assembler.toResource(readModel(id));
	}

	@Transactional(readOnly = true)
	public PlantHireRequest readModel(Long id) {
		Optional<PlantHireRequest> maybePlantHireRequest = repository.findById(id);

		if (!maybePlantHireRequest.isPresent()) {
			throw new IllegalArgumentException("Cannot find plant hire request with id: " + id);
		}

		return maybePlantHireRequest.get();
	}

	@Transactional(readOnly = true)
	public List<PlantHireRequestDTO> getAll() {
		List<PlantHireRequest> all = repository.findAll();

		return all.stream().map(phr -> assembler.toResource(phr)).collect(Collectors.toList());
	}

	@Transactional
	public PlantHireRequestDTO accept(Long id) {
		Employee approvingWorksEngineer = employeeService.getLoggedInEmployee(Role.WORKS_ENGINEER);

		PlantHireRequest request = readModel(id);
		String plantHref = request.getPlant().getHref();
		requireNonNull(plantHref);

		RentItPurchaseOrderDTO createdPO =
				rentItService.createPurchaseOrder(plantHref, businessPeriodAssembler.toResource(request.getRentalPeriod()));

		String href = createdPO.get_links().get("self").get("href");

		PHRStatus status = createdPO.getStatus().convertToPHRStatus();
		request.setStatus(status);

		PurchaseOrder purchaseOrder = purchaseOrderService.create(href, id);

		request.setPurchaseOrder(purchaseOrder);

		request.setStatus(PHRStatus.PENDING_RENTAL_PARTNER_APPROVAL);

		request.setApprovingWorksEngineer(approvingWorksEngineer);

		request = repository.save(request);

		return assembler.toResource(request);
	}

	@Transactional
	public PlantHireRequestDTO reject(Long id) {
		PlantHireRequest request = readModel(id);

		request.setStatus(PHRStatus.REJECTED);

		request = repository.save(request);

		return assembler.toResource(request);
	}


	@Transactional
	public void updateStatus(String href, RentItPurchaseOrderStatus newStatus) {
		requireNonNull(newStatus);

		PurchaseOrder purchaseOrder = purchaseOrderService.readModel(href);

		PlantHireRequest phr = purchaseOrder.getPlantHireRequest();

		phr.setStatus(newStatus.convertToPHRStatus());

		repository.save(phr);
	}

}
