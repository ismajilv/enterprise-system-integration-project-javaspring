package com.buildit.procurement.application.services.integration;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.procurement.application.dto.*;
import com.buildit.procurement.domain.enums.PHRStatus;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.Collection;

interface RentalPartnerService {

	String getPartnerName();

	String getApiUrl();

	Collection<PlantInventoryEntryDTO> queryPlantCatalog(String name, LocalDate startDate, LocalDate endDate);

	PlantInventoryEntryDTO fetchPlantEntry(String href);

	Pair<PurchaseOrderDTO, PHRStatus> createPurchaseOrder(String href, BusinessPeriodDTO businessPeriodDTO, Long constructionSiteId);

	void sendRemittanceAdvice(Long supplierId, RemittanceAdviceDTO remittanceAdvice);

	RentItExtensionRequestDTO sendExtensionRequest(Long supplierId, Long purchaseOrderExternalId, LocalDate newEndDate);

}
