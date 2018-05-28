package com.buildit.procurement.application.services.integration;

import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.procurement.application.dto.Team1PlantInventoryEntryDTO;
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
import com.buildit.procurement.application.services.assemblers.Team1ToBuildItPlantInventoryEntryAssembler;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Component
public class Team1RentItService implements RentalPartnerService {

    @Autowired
    Team1ToBuildItPlantInventoryEntryAssembler team1ToBuildItPlantInventoryEntryAssembler;

    @Autowired
    ConstructionSiteService constructionSiteService;

    @Override
    public String getPartnerName() {
        return "Team 1 Rentit";
    }

    @Override
    public String getApiUrl() {
        return "https://rentitall.herokuapp.com";
    }



    public Collection<PlantInventoryEntryDTO> queryPlantCatalog(String name, LocalDate startDate, LocalDate endDate) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        final String url = getApiUrl() + "/api/inventory/plants";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("name", name);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Team1PlantInventoryEntryDTO>> response =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<List<Team1PlantInventoryEntryDTO>>() {
                        }
                );

        List<Team1PlantInventoryEntryDTO> entries = response.getBody();

        List<PlantInventoryEntryDTO> ret = team1ToBuildItPlantInventoryEntryAssembler.toResources(entries);

        return ret;
    }


	public boolean isAvailableDuringPeriod(String href, BusinessPeriod period) {
		return true;
	}

    @Override
    public boolean cancelPurchaseOrder(Long supplierId, Long purchaseOrderExternalId) {
        throw new NotImplementedException();
    }


    public PlantInventoryEntryDTO fetchPlantEntry(String href) {
       return new PlantInventoryEntryDTO();
    }

    public Pair<PurchaseOrderDTO, PHRStatus> createPurchaseOrder(String href, BusinessPeriodDTO businessPeriodDTO, Long constructionSiteId) {
        throw new NotImplementedException();
    }

    public void sendRemittanceAdvice(Long supplierId, RemittanceAdviceDTO remittanceAdvice) {
        throw new NotImplementedException();
    }

    public RentItExtensionRequestDTO sendExtensionRequest(Long supplierId, Long purchaseOrderExternalId, LocalDate newEndDate) {
        throw new NotImplementedException();
    }

}
