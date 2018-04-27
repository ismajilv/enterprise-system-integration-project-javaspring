package com.buildit.test;

import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.common.domain.model.Money;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.application.services.*;
import com.buildit.procurement.domain.model.ConstructionSite;
import com.buildit.procurement.domain.model.PlantHireRequest;
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

		ConstructionSite constructionSite =
				constructionSiteService.create("Pepleri 1, Tartu");

		Supplier supplier = supplierService.create("Ramirent");

		PlantInventoryEntry plant =
				plantInventoryEntryService.create(
						"http://ramirent.ee:9550/api/plants/2",
						"BOBCAT E19CAB"
				);

		PlantHireRequestDTO plantHireRequest =

				plantHireRequestService.addRequest(
						constructionSite.getId(),
						supplier.getId(),
						plant.getId(),
						BusinessPeriod.of(
								LocalDate.now().plusDays(1),
								LocalDate.now().plusDays(5)
						),
						Money.of(BigDecimal.TEN));

		commentService.addComment(
				plantHireRequest.get_id(),
				"This excavator is too expensive, take a smaller one"
		);
	}

}
