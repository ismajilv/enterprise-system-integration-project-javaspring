package com.buildit.procurement.application.services.integration;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.procurement.application.dto.*;
import com.buildit.procurement.application.services.ConstructionSiteService;
import com.buildit.procurement.application.services.assemblers.RentItToBuildItPlantInventoryEntryAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Component
class LocalRentItService implements RentalPartnerService {

	@Autowired
	RentItToBuildItPlantInventoryEntryAssembler rent2buildEntryAssembler;

	@Autowired
	ConstructionSiteService constructionSiteService;

	@Override
	public String getPartnerName() {
		return "LocalRentIt";
	}

	@Override
	public String getApiUrl() {
		return "http://localhost:8090";
	}

	public Collection<PlantInventoryEntryDTO> queryPlantCatalog(String name, LocalDate startDate, LocalDate endDate) {
		HttpHeaders headers = new HttpHeaders();

		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final String url = getApiUrl() + "/api/plants";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("name", name)
				.queryParam("startDate", startDate)
				.queryParam("endDate", endDate);

		HttpEntity<List<RentItPlantInventoryEntryDTO>> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<RentItPlantInventoryEntryDTO>> response =
				restTemplate.exchange(
						builder.toUriString(),
						HttpMethod.GET,
						entity,
						new ParameterizedTypeReference<List<RentItPlantInventoryEntryDTO>>() {
						}
				);

		List<RentItPlantInventoryEntryDTO> entries = response.getBody();

		List<PlantInventoryEntryDTO> ret = rent2buildEntryAssembler.toResources(entries);

		return ret;
	}

//	public boolean isAvailableDuringPeriod(String href, BusinessPeriod period) {
//		return true;
//	}

	public RentItPlantInventoryEntryDTO fetchPlantEntry(String href) {
		HttpHeaders headers = new HttpHeaders();

		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<RentItPlantInventoryEntryDTO> response =
				new RestTemplate().exchange(
						href,
						HttpMethod.GET,
						entity,
						new ParameterizedTypeReference<RentItPlantInventoryEntryDTO>() {
						}
				);

		RentItPlantInventoryEntryDTO entry = response.getBody();

		return entry;
	}

	@Override
	public Collection<PlantInventoryEntryDTO> querySupplierPlantCatalog(String name, LocalDate startDate, LocalDate endDate) {
		return null;
	}

	public RentItPurchaseOrderDTO createPurchaseOrder(String href, BusinessPeriodDTO businessPeriodDTO, Long constructionSiteId) {
		RentItPlantInventoryEntryDTO rentItEntry = fetchPlantEntry(href);
		// String respondTo = getApiUrl() + "/callbacks/orderStateChanged";
		ConstructionSiteDTO site = constructionSiteService.readOne(constructionSiteId);
		RentItCreatePORequestDTO rentItPORequest =
				RentItCreatePORequestDTO.of(rentItEntry.get_id(), businessPeriodDTO, site.getAddress());

		return doCreatePurchaseOrder(rentItPORequest);
	}

	private RentItPurchaseOrderDTO doCreatePurchaseOrder(RentItCreatePORequestDTO request) {
		HttpHeaders headers = new HttpHeaders();

		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<RentItCreatePORequestDTO> entity = new HttpEntity<>(request, headers);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<RentItPurchaseOrderDTO> response =
				restTemplate.exchange(
						getApiUrl() + "/api/orders",
						HttpMethod.POST,
						entity,
						new ParameterizedTypeReference<RentItPurchaseOrderDTO>() {
						}
				);

		RentItPurchaseOrderDTO order = response.getBody();

		return order;
	}

	public void sendRemittanceAdvice(Long supplierId, RemittanceAdviceDTO remittanceAdvice) {
		throw new NotImplementedException();
	}

	public RentItExtensionRequestDTO sendExtensionRequest(Long supplierId, Long purchaseOrderExternalId, LocalDate newEndDate) {
		HttpHeaders headers = new HttpHeaders();

		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		ExtensionRequestDTO request = new ExtensionRequestDTO();

		request.setNewEndDate(newEndDate);

		HttpEntity<ExtensionRequestDTO> entity = new HttpEntity<>(request, headers);

		RestTemplate restTemplate = new RestTemplate();

		String url = getApiUrl() + "/api/orders/" + purchaseOrderExternalId + "/requestExtension";

		ResponseEntity<RentItExtensionRequestDTO> response =
				restTemplate.exchange(
						url,
						HttpMethod.POST,
						entity,
						new ParameterizedTypeReference<RentItExtensionRequestDTO>() {
						}
				);

		RentItExtensionRequestDTO rentItExtensionRequestDTO = response.getBody();

		return rentItExtensionRequestDTO;
	}

}
