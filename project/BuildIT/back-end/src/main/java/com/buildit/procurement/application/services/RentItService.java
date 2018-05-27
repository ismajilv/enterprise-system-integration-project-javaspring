package com.buildit.procurement.application.services;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.procurement.application.dto.*;
import com.buildit.procurement.application.services.assemblers.RentItToBuildItPlantInventoryEntryAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

@Service
public class RentItService {

	public static final String RENTAL_PARTNER_NAME_1 = "Own RentIt";

	@Autowired
    RentItToBuildItPlantInventoryEntryAssembler rent2buildEntryAssembler;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	ConstructionSiteService constructionSiteService;

	private Map<SupplierDTO, String> supplier2Url = new HashMap<>();

	@PostConstruct
	public void init() {
		SupplierDTO supplier = supplierService.findOrCreateByName(RENTAL_PARTNER_NAME_1);
		supplier2Url.put(supplier, "http://localhost:8090");
	}

	public Collection<PlantInventoryEntryDTO> queryPlantCatalog(String name, LocalDate startDate, LocalDate endDate) {
		List<PlantInventoryEntryDTO> foundPlants = new ArrayList<>();

		for (Map.Entry<SupplierDTO, String> entry : supplier2Url.entrySet()) {
			HttpHeaders headers = new HttpHeaders();

			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

			final String url = entry.getValue() + "/api/plants";
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

			ret.forEach(
					pie -> pie.setSupplier(entry.getKey())
			);

			foundPlants.addAll(ret);
		}

		return foundPlants;
	}

//	public boolean isAvailableDuringPeriod(String href, BusinessPeriod period) {
//		return true;
//	}

	public RentItPlantInventoryEntryDTO fetchPlantEntryFromRentIt(String href) {
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

	public RentItPurchaseOrderDTO createPurchaseOrder(String href, BusinessPeriodDTO businessPeriodDTO, Long constructionSiteId) {
		RentItPlantInventoryEntryDTO rentItEntry = fetchPlantEntryFromRentIt(href);
		String respondTo = supplier2Url.entrySet().iterator().next().getValue() + "/callbacks/orderStateChanged";
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
						supplier2Url.entrySet().iterator().next().getValue() + "/api/orders",
						HttpMethod.POST,
						entity,
						new ParameterizedTypeReference<RentItPurchaseOrderDTO>() {
						}
				);

		RentItPurchaseOrderDTO order = response.getBody();

		return order;
	}

	public void sendRemittanceAdvice(Long supplierId, RemittanceAdviceDTO remittanceAdvice) {
		// TODO
	}

	public String getRentItUrl() {
		return supplier2Url.entrySet().iterator().next().getValue();
	}

	public RentItExtensionRequestDTO sendExtensionRequest(Long supplierId, Long purchaseOrderExternalId, LocalDate newEndDate) {
		HttpHeaders headers = new HttpHeaders();

		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		ExtensionRequestDTO request = new ExtensionRequestDTO();

		request.setNewEndDate(newEndDate);

		HttpEntity<ExtensionRequestDTO> entity = new HttpEntity<>(request, headers);

		RestTemplate restTemplate = new RestTemplate();

		String url = supplier2Url.entrySet().iterator().next().getValue() + "/api/orders/" + purchaseOrderExternalId + "/requestExtension";

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
