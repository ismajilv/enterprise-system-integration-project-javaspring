package com.buildit.test;

import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.common.domain.model.Money;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.application.services.*;
import com.buildit.procurement.domain.model.ConstructionSite;
import com.buildit.procurement.domain.model.PlantInventoryEntry;
import com.buildit.procurement.domain.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
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

		PlantInventoryEntry plant1 =
				plantInventoryEntryService.create(
						"http://ramirent.ee:9550/api/plants/2",
						"BOBCAT E19CAB"
				);

		PlantInventoryEntry plant2 =
				plantInventoryEntryService.create(
						"http://ramirent.ee:9550/api/plants/59",
						"KAESER M122"
				);

		PlantInventoryEntry plant3 =
				plantInventoryEntryService.create(
						"http://www.cramo.ee/api/item-json/55",
						"WACKER-NEUSON DPU6555HE"
				);

		PlantHireRequestDTO pendingPlantHireRequest =

				plantHireRequestService.addRequest(
						constructionSite1.getId(),
						supplierRamirent.getId(),
						plant1.getId(),
						BusinessPeriod.of(
								LocalDate.now().plusDays(1),
								LocalDate.now().plusDays(5)
						),
						Money.of(BigDecimal.TEN));

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
						plant3.getId(),
						BusinessPeriod.of(
								LocalDate.now().plusDays(1),
								LocalDate.now().plusDays(5)
						),
						Money.of(BigDecimal.valueOf(120.00)));

		plantHireRequestService.reject(rejectedPlantHireRequest.get_id());
	}

}
