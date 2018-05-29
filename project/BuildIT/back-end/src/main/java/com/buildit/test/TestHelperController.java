package com.buildit.test;

import com.buildit.common.application.dto.EmployeeDTO;
import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.procurement.application.dto.ExtensionRequestDTO;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.application.dto.SupplierDTO;
import com.buildit.procurement.application.services.*;
import com.buildit.procurement.domain.enums.Role;
import com.buildit.procurement.domain.model.ConstructionSite;
import com.buildit.procurement.domain.model.PlantHireRequest;
import com.buildit.procurement.domain.model.PlantInventoryEntry;
import com.buildit.procurement.domain.model.Supplier;
import com.buildit.procurement.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestHelperController {

    @Autowired
    ConstructionSiteRepository constructionSiteRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    PlantHireRequestRepository plantHireRequestRepository;

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;


    @Autowired
    PlantHireRequestService plantHireRequestService;

    @Autowired
    CommentService commentService;

    @Autowired
    ConstructionSiteService constructionSiteService;

    @Autowired
    SupplierService supplierService;

    @Autowired
    PlantInventoryEntryService plantInventoryEntryService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ExtensionRequestRepository extensionRequestRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @PostMapping("test/initData")
    public void initData(@RequestParam Long accepted, @RequestParam Long rejected){
        
        invoiceRepository.deleteAll();
        purchaseOrderRepository.deleteAll();
        extensionRequestRepository.deleteAll();
        plantHireRequestRepository.deleteAll();
        plantInventoryEntryRepository.deleteAll();
        employeeRepository.deleteAll();
        constructionSiteRepository.deleteAll();

        init(accepted, rejected);
    }

    public void init(Long id1, Long id2) {


        System.out.println("== Adding test data ==");

        ConstructionSite constructionSite1 =
                constructionSiteService.create("Pepleri 1, Tartu");

        ConstructionSite constructionSite2 =
                constructionSiteService.create("Viru 1, Tallinn");

        ConstructionSite constructionSite3 =
                constructionSiteService.create("Kase 12, Narva");

        SupplierDTO supplier = supplierService.findOrCreateByName("LocalRentIt");

        // Supplier supplier = supplierService.readModel(supplierAsDTO.get_id());

        // Supplier supplierRamirent = supplierService.create("Ramirent");

        // Supplier supplierCramo = supplierService.create("Cramo");

        EmployeeDTO employee1 = employeeService.create(Role.SITE_ENGINEER, "James", "Dean");

        EmployeeDTO employee2 = employeeService.create(Role.WORKS_ENGINEER, "Tom", "Cruise");


        PlantHireRequestDTO pendingPlantHireRequest =

                plantHireRequestService.addRequest(
                        constructionSite1.getId(),
                        supplier.get_id(),
                        "http://localhost:8090/api/plants/"+id1,
                        BusinessPeriod.of(
                                LocalDate.now().plusDays(1),
                                LocalDate.now().plusDays(5)
                        )
                );

        commentService.create(
                pendingPlantHireRequest.get_id(),
                "This excavator is too expensive, take a smaller one - Regards, Tom"
        );

        commentService.create(
                pendingPlantHireRequest.get_id(),
                "Close that request down than, will create a new one - James"
        );

        PlantHireRequestDTO rejectedPlantHireRequest =

                plantHireRequestService.addRequest(
                        constructionSite2.getId(),
                        supplier.get_id(),
                        "http://localhost:8090/api/plants/"+id2,
                        BusinessPeriod.of(
                                LocalDate.now().plusDays(1),
                                LocalDate.now().plusDays(5)
                        )
                );

        plantHireRequestService.reject(rejectedPlantHireRequest.get_id());

        PlantHireRequestDTO acceptedPlantHireRequest =

                plantHireRequestService.addRequest(
                        constructionSite1.getId(),
                        supplier.get_id(),
                        "http://localhost:8090/api/plants/"+id1,
                        BusinessPeriod.of(
                                LocalDate.now().plusDays(10),
                                LocalDate.now().plusDays(12)
                        )
                );

        PlantHireRequestDTO accepted = plantHireRequestService.accept(acceptedPlantHireRequest.get_id());

        PlantHireRequestDTO extended = plantHireRequestService.extend(
                acceptedPlantHireRequest.get_id(),
                new ExtensionRequestDTO(
                        accepted.getPurchaseOrder().getExternalId(),
                        acceptedPlantHireRequest.getRentalPeriod().getEndDate().plusDays(3),
                        null
                )
        );
    }

    @GetMapping("/api/plantihirerequest/count")
    public Integer countPlantHireRequests(){
        return plantHireRequestRepository.findAll().size();
    }
}