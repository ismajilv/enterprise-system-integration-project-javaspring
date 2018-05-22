package com.buildit.test;

import com.buildit.common.application.dto.EmployeeDTO;
import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.procurement.application.dto.ExtensionRequestDTO;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.application.services.*;
import com.buildit.procurement.domain.enums.Role;
import com.buildit.procurement.domain.model.ConstructionSite;
import com.buildit.procurement.domain.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

// TODO delete before going live!
@Service
public class TestDataProvider {

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
	ExtensionRequestService extensionRequestService;

	@Value("${rentItUrl}")
	String rentItUrl;

	@PostConstruct
	public void init() {
		System.out.println("== Adding test data ==");

		ConstructionSite constructionSite1 =
				constructionSiteService.create("Pepleri 1, Tartu");

		ConstructionSite constructionSite2 =
				constructionSiteService.create("Viru 1, Tallinn");

		ConstructionSite constructionSite3 =
				constructionSiteService.create("Kase 12, Narva");

		Supplier supplierRamirent = supplierService.create("Ramirent");

		Supplier supplierCramo = supplierService.create("Cramo");

		EmployeeDTO employee1 = employeeService.create(Role.SITE_ENGINEER, "James", "Dean");

		EmployeeDTO employee2 = employeeService.create(Role.WORKS_ENGINEER, "Tom", "Cruise");

		PlantHireRequestDTO pendingPlantHireRequest =

				plantHireRequestService.addRequest(
						constructionSite1.getId(),
						supplierRamirent.getId(),
						rentItUrl + "/api/sales/plants/3",
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
						supplierCramo.getId(),
						rentItUrl + "/api/sales/plants/6",
						BusinessPeriod.of(
								LocalDate.now().plusDays(1),
								LocalDate.now().plusDays(5)
						)
				);

		plantHireRequestService.reject(rejectedPlantHireRequest.get_id());

		PlantHireRequestDTO acceptedPlantHireRequest =

				plantHireRequestService.addRequest(
						constructionSite1.getId(),
						supplierRamirent.getId(),
						rentItUrl + "/api/sales/plants/3",
						BusinessPeriod.of(
								LocalDate.now().plusDays(10),
								LocalDate.now().plusDays(12)
						)
				);

		plantHireRequestService.accept(acceptedPlantHireRequest.get_id());

		ExtensionRequestDTO extension = extensionRequestService.create(acceptedPlantHireRequest.get_id(), acceptedPlantHireRequest.getRentalPeriod().getEndDate().plusDays(3));

		// TODO add some seed data with PurchaseOrder Invoice RemittanceAdvice
	}

}
