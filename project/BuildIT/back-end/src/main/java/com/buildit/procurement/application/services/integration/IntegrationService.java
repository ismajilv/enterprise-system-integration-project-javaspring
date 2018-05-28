package com.buildit.procurement.application.services.integration;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.procurement.application.dto.*;
import com.buildit.procurement.application.services.SupplierService;
import com.buildit.procurement.domain.enums.PHRStatus;
import org.apache.commons.lang3.tuple.Pair;
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
			supplierId2Service.put(supplierDTO.get_id(), partnerService);
		}
	}

	public Collection<PlantInventoryEntryDTO> queryPlantCatalog(String name, LocalDate startDate, LocalDate endDate) {
		Collection<PlantInventoryEntryDTO> ret = new ArrayList<>();
		for (Long supplierId : supplierId2Service.keySet()) {
			//try {
				Collection<PlantInventoryEntryDTO> result = querySupplierPlantCatalog(supplierId, name, startDate, endDate);
				ret.addAll(result);
			//} catch (Exception e) {
			// System.out.println("Requesting plant catalog failed from supplier: " + getServiceBySupplierId(supplierId).getPartnerName());
			//}
		}
		return ret;
	}

	public Collection<PlantInventoryEntryDTO> querySupplierPlantCatalog(Long supplierId, String name, LocalDate startDate, LocalDate endDate) {
		Collection<PlantInventoryEntryDTO> result = getServiceBySupplierId(supplierId).queryPlantCatalog(name, startDate, endDate);
		SupplierDTO supplierDTO = supplierService.readOne(supplierId);
		result.forEach(pie -> pie.setSupplier(supplierDTO));
		return result;
	}

	public PlantInventoryEntryDTO fetchPlantEntry(Long supplierId, String href) {
		PlantInventoryEntryDTO ret = getServiceBySupplierId(supplierId).fetchPlantEntry(href);
		SupplierDTO supplierDTO = supplierService.readOne(supplierId);
		ret.setSupplier(supplierDTO);
		requireNonNull(ret, "No plant found at: " + href);
		return ret;
	}

	public Pair<PurchaseOrderDTO, PHRStatus> createPurchaseOrder(Long supplierId, String href, BusinessPeriodDTO businessPeriodDTO, Long constructionSiteId) {
		return getServiceBySupplierId(supplierId).createPurchaseOrder(href, businessPeriodDTO, constructionSiteId);
	}

	public void sendRemittanceAdvice(Long supplierId, RemittanceAdviceDTO remittanceAdvice) {
		getServiceBySupplierId(supplierId).sendRemittanceAdvice(supplierId, remittanceAdvice);
	}

	public RentItExtensionRequestDTO sendExtensionRequest(Long supplierId, Long purchaseOrderExternalId, LocalDate newEndDate) {
		RentItExtensionRequestDTO ret = getServiceBySupplierId(supplierId).sendExtensionRequest(supplierId, purchaseOrderExternalId, newEndDate);
		requireNonNull(ret, "Received null from remote for extension request reply");
		return ret;
	}
	public boolean cancelPurchaseOrder(Long supplierId, Long purchaseOrderExternalId) {
		return getServiceBySupplierId(supplierId).cancelPurchaseOrder(supplierId, purchaseOrderExternalId);
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

