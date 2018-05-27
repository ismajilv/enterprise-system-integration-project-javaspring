package com.buildit.procurement.application.services.integration;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.procurement.application.dto.*;

import java.time.LocalDate;
import java.util.Collection;

interface RentalPartnerService {

	String getPartnerName();

	String getApiUrl();

	Collection<PlantInventoryEntryDTO> querySupplierPlantCatalog(String name, LocalDate startDate, LocalDate endDate);

	RentItPlantInventoryEntryDTO fetchPlantEntry(String href);

	RentItPurchaseOrderDTO createPurchaseOrder(String href, BusinessPeriodDTO businessPeriodDTO, Long constructionSiteId);

	void sendRemittanceAdvice(Long supplierId, RemittanceAdviceDTO remittanceAdvice);

	RentItExtensionRequestDTO sendExtensionRequest(Long supplierId, Long purchaseOrderExternalId, LocalDate newEndDate);

}
