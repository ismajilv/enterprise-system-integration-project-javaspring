package com.buildit.procurement.application.services.integration;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.procurement.application.dto.*;
import com.buildit.procurement.application.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

import static java.util.Objects.requireNonNull;

@Service
public class IntegrationService {

	@Autowired
	private List<RentalPartnerService> partnerServices;

	@Autowired
	private SupplierService supplierService;

	private Map<Long, RentalPartnerService> supplierId2Service = new HashMap<>();

	@PostConstruct
	public void init() {
		if (partnerServices.isEmpty()) throw new IllegalStateException("No integration services found!");
		for (RentalPartnerService partnerService : partnerServices) {
			SupplierDTO supplierDTO = supplierService.findOrCreateByName(partnerService.getPartnerName());
			System.out.println("%%%%%%%%%%% " + supplierDTO.get_id() + supplierDTO.getName() + partnerService);
			supplierId2Service.put(supplierDTO.get_id(), partnerService);
		}
	}

	public Collection<PlantInventoryEntryDTO> queryPlantCatalog(String name, LocalDate startDate, LocalDate endDate) {
		Collection<PlantInventoryEntryDTO> ret = new ArrayList<>();
		for (Map.Entry<Long, RentalPartnerService> entry : supplierId2Service.entrySet()) {
			RentalPartnerService partnerService = entry.getValue();
			Collection<PlantInventoryEntryDTO> result = partnerService.querySupplierPlantCatalog(name, startDate, endDate);
			Long supplierId = entry.getKey();
			SupplierDTO supplierDTO = supplierService.readOne(supplierId);
			result.forEach(pie -> pie.setSupplier(supplierDTO));
		}
		return ret;
	}

	public Collection<PlantInventoryEntryDTO> querySupplierPlantCatalog(Long supplierId, String name, LocalDate startDate, LocalDate endDate) {
		return getServiceBySupplierId(supplierId).querySupplierPlantCatalog(name, startDate, endDate);
	}

	public RentItPlantInventoryEntryDTO fetchPlantEntry(Long supplierId, String href) {
		RentItPlantInventoryEntryDTO ret = getServiceBySupplierId(supplierId).fetchPlantEntry(href);
		requireNonNull(ret, "No plant found at: " + href);
		return ret;
	}

	public RentItPurchaseOrderDTO createPurchaseOrder(Long supplierId, String href, BusinessPeriodDTO businessPeriodDTO, Long constructionSiteId) {
		return getServiceBySupplierId(supplierId).createPurchaseOrder(href, businessPeriodDTO, constructionSiteId);
	}

	public void sendRemittanceAdvice(Long supplierId, RemittanceAdviceDTO remittanceAdvice) {
		getServiceBySupplierId(supplierId).sendRemittanceAdvice(supplierId, remittanceAdvice);
	}

	public RentItExtensionRequestDTO sendExtensionRequest(Long supplierId, Long purchaseOrderExternalId, LocalDate newEndDate) {
		return getServiceBySupplierId(supplierId).sendExtensionRequest(supplierId, purchaseOrderExternalId, newEndDate);
	}

	private RentalPartnerService getServiceBySupplierId(Long supplierId) {
		requireNonNull(supplierId);
		if (!supplierId2Service.containsKey(supplierId)) {
			throw new IllegalArgumentException("No integration service defined for supplier id: " + supplierId);
		}
		RentalPartnerService service = supplierId2Service.get(supplierId);
		return service;
	}

}
