package com.buildit.test;

import com.buildit.procurement.domain.model.ConstructionSite;
import com.buildit.procurement.domain.model.Supplier;
import com.buildit.procurement.domain.repository.ConstructionSiteRepository;
import com.buildit.procurement.domain.repository.PlantHireRequestRepository;
import com.buildit.procurement.domain.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestHelperController {

	@Autowired
	ConstructionSiteRepository constructionSiteRepository;

	@Autowired
	SupplierRepository supplierRepository;

	@Autowired
	PlantHireRequestRepository plantHireRequestRepository;

	@PostMapping("/api/suppliers")
	public List<Supplier> setupSuppliers(@RequestBody List<Supplier> suppliers) {
		plantHireRequestRepository.deleteAll();
		constructionSiteRepository.deleteAll();
		supplierRepository.deleteAll();
		return supplierRepository.saveAll(suppliers);
	}

	@PostMapping("/api/constructionsites")
	public List<ConstructionSite> setupItems(@RequestBody List<ConstructionSite> constructionsites) {
		constructionSiteRepository.deleteAll();
		return constructionSiteRepository.saveAll(constructionsites);
	}

	@GetMapping("/api/plantihirerequest/count")
	public Integer countPlantHireRequests() {
		return plantHireRequestRepository.findAll().size();
	}
}