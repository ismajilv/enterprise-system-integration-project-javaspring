package com.buildit.procurement.application.services;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.common.domain.model.AddressDTO;
import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.application.dto.RentItCreatePORequestDTO;
import com.buildit.procurement.application.dto.RentItPlantInventoryEntryDTO;
import com.buildit.procurement.application.dto.RentItPurchaseOrderDTO;
import com.buildit.procurement.web.controller.RentItCallbackController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class RentItService {

	@Autowired
	RentItToBuildItPlantInventoryEntryAssembler rent2buildEntryAssembler;

	@Value("${rentItUrl}")
	String rentItUrl;

	public Collection<PlantInventoryEntryDTO> queryPlantCatalog(String name, LocalDate startDate, LocalDate endDate) {
		HttpHeaders headers = new HttpHeaders();

		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		checkRentItUrl();
		final String url = rentItUrl + "/api/sales/plants";
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

		return rent2buildEntryAssembler.toResources(entries);
	}

	private void checkRentItUrl() {
		requireNonNull(rentItUrl);
		if (rentItUrl.length() < 10) throw new IllegalArgumentException("Configure rentItUrl properly: " + rentItUrl);
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

	public RentItPurchaseOrderDTO createPurchaseOrder(String href, BusinessPeriodDTO businessPeriodDTO) {
		RentItPlantInventoryEntryDTO rentItEntry = fetchPlantEntryFromRentIt(href);
        String respondTo = rentItUrl + "/callbacks/orderStateChanged";
		RentItCreatePORequestDTO rentItPORequest =
				RentItCreatePORequestDTO.of(rentItEntry, businessPeriodDTO);

		return doCreatePurchaseOrder(rentItPORequest);
	}

	private RentItPurchaseOrderDTO doCreatePurchaseOrder(RentItCreatePORequestDTO request) {
		HttpHeaders headers = new HttpHeaders();

		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<RentItCreatePORequestDTO> entity = new HttpEntity<>(request, headers);

		RestTemplate restTemplate = new RestTemplate();

		checkRentItUrl();
		ResponseEntity<RentItPurchaseOrderDTO> response =
				restTemplate.exchange(
						rentItUrl + "/api/sales/orders",
						HttpMethod.POST,
						entity,
						new ParameterizedTypeReference<RentItPurchaseOrderDTO>() {
						}
				);

		RentItPurchaseOrderDTO order = response.getBody();

		return order;
	}

}
