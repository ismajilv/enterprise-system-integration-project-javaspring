package com.buildit.procurement.application.services.integration;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.procurement.application.dto.*;
import com.buildit.procurement.application.services.ConstructionSiteService;
import com.buildit.procurement.application.services.assemblers.Team2ToBuildItPlantInventoryEntryAssembler;
import com.buildit.procurement.domain.enums.PHRStatus;
import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Component
class Team2RentItService implements RentalPartnerService {

	static class Team2PlantCatalogInner2DTO implements Serializable {
		List<Team2PlantInventoryEntryDTO> plants;
	}

	static class Team2PlantCatalogInner1DTO implements Serializable {
		Team2PlantCatalogInner2DTO _embedded;
	}

	@Autowired
	Team2ToBuildItPlantInventoryEntryAssembler team2ToBuildItPlantInventoryEntryAssembler;

	@Autowired
	ConstructionSiteService constructionSiteService;

	@Override
	public String getPartnerName() {
		return "Team 2 Rentit";
	}

	@Override
	public String getApiUrl() {
		return "https://team-2-rentit.herokuapp.com";
	}

	public Collection<PlantInventoryEntryDTO> queryPlantCatalog(String name, LocalDate startDate, LocalDate endDate) {
		HttpHeaders headers = new HttpHeaders();

		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwYW5kYSIsImV4cCI6MTUyODA0MDIwMywiaWF0IjoxNTI3NDM1NDAzfQ.TK5D4DY9q5q3Q-5x5exX53jV-jnnlscjcEYovAzFIEbc6eZG8NQbePjWVhU0aKTVySUkYvSomn8jFJWkYqM5qQ");

		final String url = getApiUrl() + "/api/sales/plants";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("name", name)
				.queryParam("startDate", startDate)
				.queryParam("endDate", endDate);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response =
				restTemplate.exchange(
						builder.toUriString(),
						HttpMethod.GET,
						entity,
						new ParameterizedTypeReference<String>() {
						}
				);

		String dto = response.getBody();

		Gson gson = new Gson();

		Team2PlantCatalogInner1DTO wrappedCatalog = gson.fromJson(dto, Team2PlantCatalogInner1DTO.class);

		requireNonNull(wrappedCatalog);
		requireNonNull(wrappedCatalog._embedded);
		requireNonNull(wrappedCatalog._embedded.plants);

		List<Team2PlantInventoryEntryDTO> entries = wrappedCatalog._embedded.plants;

		List<PlantInventoryEntryDTO> ret = team2ToBuildItPlantInventoryEntryAssembler.toResources(entries);

		return ret;
	}

//	public boolean isAvailableDuringPeriod(String href, BusinessPeriod period) {
//		return true;
//	}

	public PlantInventoryEntryDTO fetchPlantEntry(String href) {
		HttpHeaders headers = new HttpHeaders();

		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwYW5kYSIsImV4cCI6MTUyODA0MDIwMywiaWF0IjoxNTI3NDM1NDAzfQ.TK5D4DY9q5q3Q-5x5exX53jV-jnnlscjcEYovAzFIEbc6eZG8NQbePjWVhU0aKTVySUkYvSomn8jFJWkYqM5qQ");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<Team2PlantInventoryEntryDTO> response =
				new RestTemplate().exchange(
						href,
						HttpMethod.GET,
						entity,
						new ParameterizedTypeReference<Team2PlantInventoryEntryDTO>() {
						}
				);

		Team2PlantInventoryEntryDTO entry = response.getBody();

		return team2ToBuildItPlantInventoryEntryAssembler.toResource(entry);
	}

	public Pair<PurchaseOrderDTO, PHRStatus> createPurchaseOrder(String href, BusinessPeriodDTO businessPeriodDTO, Long constructionSiteId) {
		Team2CreatePORequestDTO createPORequest = new Team2CreatePORequestDTO();
		Team2CustomerWrapper customer = new Team2CustomerWrapper();
		ConstructionSiteDTO site = constructionSiteService.readOne(constructionSiteId);
		customer.setSiteAddress(site.getAddress());
		customer.setContactPerson("James Dean");
		customer.setEmail("veskimae.k@gmail.com");
		customer.setContactPerson("Kristjan Veskimae");
		createPORequest.setCustomer(customer);
		Team2IdWrapper plant = new Team2IdWrapper();
		PlantInventoryEntryDTO plantEntry = fetchPlantEntry(href);
		plant.set_id(plantEntry.getExternalId());
		createPORequest.setPlant(plant);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwYW5kYSIsImV4cCI6MTUyODA0MDIwMywiaWF0IjoxNTI3NDM1NDAzfQ.TK5D4DY9q5q3Q-5x5exX53jV-jnnlscjcEYovAzFIEbc6eZG8NQbePjWVhU0aKTVySUkYvSomn8jFJWkYqM5qQ");

		HttpEntity<Team2CreatePORequestDTO> entity = new HttpEntity<>(createPORequest, headers);
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Team2PurchaseOrderDTO> response =
				restTemplate.exchange(
						getApiUrl() + "/api/sales/orders",
						HttpMethod.POST,
						entity,
						new ParameterizedTypeReference<Team2PurchaseOrderDTO>() {
						}
				);

		Team2PurchaseOrderDTO createdRemotePO = response.getBody();

		PHRStatus newPHRStatus = createdRemotePO.getStatus().convertToPHRStatus();

		PurchaseOrderDTO po = new PurchaseOrderDTO();

		po.setExternalId(createdRemotePO.get_id());
		po.setHref(createdRemotePO.get_links().get("self").get("href"));

		Pair<PurchaseOrderDTO,PHRStatus> ret = Pair.of(po, newPHRStatus);

		return ret;
	}

	public void sendRemittanceAdvice(Long supplierId, RemittanceAdviceDTO remittanceAdvice) {
		throw new NotImplementedException();
	}

	public RentItExtensionRequestDTO sendExtensionRequest(Long supplierId, Long purchaseOrderExternalId, LocalDate newEndDate) {
		throw new NotImplementedException();
	}

	@Override
	public boolean cancelPurchaseOrder(Long supplierId, Long purchaseOrderExternalId) {
		throw new NotImplementedException();
	}

}
