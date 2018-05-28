package com.rentit.test;

import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.application.services.InventoryService;
import com.rentit.inventory.application.services.PlantInventoryEntryAssembler;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.repository.InventoryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryItemRepository;
import com.rentit.inventory.domain.repository.PlantReservationRepository;
import com.rentit.invoicing.application.dto.InvoiceDTO;
import com.rentit.invoicing.domain.repository.InvoiceRepository;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@RestController
public class TestHelperController {

    @Autowired
    PlantReservationRepository plantReservationRepository;

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;

    @Autowired
    PlantInventoryItemRepository plantInventoryItemRepository;

   // @Autowired
    //InventoryRepository inventoryRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    TestDataProvider testDataProvider;

    @Autowired
    EntityManager entityManager;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;

    @PostMapping("/test/initData")
    @Transactional
    public List<PlantInventoryEntry> initTestDataForCucumber(){

        System.out.println("INIT TEST DATA");
        invoiceRepository.deleteAll();
        plantReservationRepository.deleteAll();
        purchaseOrderRepository.deleteAll();

        List<PlantInventoryEntry> entries = inventoryRepository.findAvailablePlants("excavator type 2", LocalDate.now().plusDays(1), LocalDate.now().plusDays(5));
        List<PlantInventoryItem> items = inventoryRepository.findAvailableItems(entries.get(0), LocalDate.now().plusDays(1), LocalDate.now().plusDays(5));
        HttpHeaders headers = new HttpHeaders();

        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        PlantInventoryEntryDTO plantInventoryEntryDTO = new PlantInventoryEntryDTO();
        HttpEntity<PlantInventoryEntryDTO> entity = new HttpEntity<>(plantInventoryEntryDTO, headers);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.exchange("http://localhost:8080/test/initData?accepted=3&rejected=6",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<String>() {}
        );
        return null;
    }


}