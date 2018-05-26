package com.buildit.test;

import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.application.services.ConstructionSiteService;
import com.buildit.procurement.application.services.PlantHireRequestService;
import com.buildit.procurement.application.services.PlantInventoryEntryService;
import com.buildit.procurement.application.services.SupplierService;
import com.buildit.procurement.domain.model.ConstructionSite;
import com.buildit.procurement.domain.model.PlantHireRequest;
import com.buildit.procurement.domain.model.PlantInventoryEntry;
import com.buildit.procurement.domain.model.Supplier;
import com.buildit.procurement.domain.repository.ConstructionSiteRepository;
import com.buildit.procurement.domain.repository.PlantHireRequestRepository;
import com.buildit.procurement.domain.repository.PlantInventoryEntryRepository;
import com.buildit.procurement.domain.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    PlantHireRequestService plantHireRequestService;

    @Autowired
    PlantInventoryEntryService plantInventoryEntryService;

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;

    @Value("${rentItUrl}")
    String rentItUrl;

    @PostMapping("/api/phr")
    public List<PlantHireRequest> setupPhrs(@RequestBody int howMany) {
        plantHireRequestRepository.deleteAll();
        List<PlantHireRequest> phrs = new ArrayList<>();
        List<Supplier> suppliers = supplierRepository.findAll();
        List<ConstructionSite> constructionSites = constructionSiteRepository.findAll();

        ArrayList<PlantInventoryEntryDTO> pies = (ArrayList<PlantInventoryEntryDTO>)plantInventoryEntryService.findAvailable("",LocalDate.of(1900,3,20),LocalDate.of(2100,3,20));

        for(int i=0; i < pies.size(); i++){
            PlantHireRequestDTO pendingPlantHireRequest =
                    plantHireRequestService.addRequest(
                            constructionSites.get(0).getId(),
                            suppliers.get(0).getId(),
                            pies.get(i).getHref(),
                            BusinessPeriod.of(
                                    LocalDate.now().plusDays(1),
                                    LocalDate.now().plusDays(5)
                            )
                    );
            plantHireRequestService.accept(pendingPlantHireRequest.get_id());
        }
        List<PlantHireRequest> plantHireRequests = plantHireRequestRepository.findAll();
        return plantHireRequests;
    }

    @PostMapping("/api/suppliers")
    public List<Supplier> setupSuppliers(@RequestBody List<Supplier> suppliers) {
        plantHireRequestRepository.deleteAll();
        constructionSiteRepository.deleteAll();
        plantInventoryEntryRepository.deleteAll();
        supplierRepository.deleteAll();
        return supplierRepository.saveAll(suppliers);
    }

    @PostMapping("/api/constructionsites")
    public List<ConstructionSite> setupItems(@RequestBody List<ConstructionSite> constructionsites) {
        constructionSiteRepository.deleteAll();
        return  constructionSiteRepository.saveAll(constructionsites);
    }

    @GetMapping("/api/plantihirerequest/count")
    public Integer countPlantHireRequests(){
        return plantHireRequestRepository.findAll().size();
    }
}