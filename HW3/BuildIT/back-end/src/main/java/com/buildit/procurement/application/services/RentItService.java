package com.buildit.procurement.application.services;

import com.buildit.common.application.dto.MoneyDTO;
import com.buildit.common.domain.model.BusinessPeriod;
import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.application.dto.RentItCreatePORequestDTO;
import com.buildit.procurement.application.dto.RentItPurchaseOrderDTO;
import com.buildit.procurement.domain.enums.RentItPurchaseOrderStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

// TODO data should be queried externally from different RentIts
@Service
public class RentItService {

	// mock querying from RentIt for now
	private Map<String, PlantInventoryEntryDTO> href2Plant = new HashMap<>();

	{
		PlantInventoryEntryDTO plant1 = PlantInventoryEntryDTO.of(
				"http://ramirent.ee:9550/api/plants/2",
				"BOBCAT E19CAB",
				MoneyDTO.of(BigDecimal.valueOf(159.99))
		);

		PlantInventoryEntryDTO plant2 = PlantInventoryEntryDTO.of(
				"http://ramirent.ee:9550/api/plants/59",
				"KAESER M122",
				MoneyDTO.of(BigDecimal.valueOf(1800))
		);

		PlantInventoryEntryDTO plant3 = PlantInventoryEntryDTO.of(
				"http://www.cramo.ee/api/item-json/55",
				"WACKER-NEUSON DPU6555HE",
				MoneyDTO.of(BigDecimal.valueOf(620.50))
		);

		href2Plant.put(plant1.getHref(), plant1);

		href2Plant.put(plant2.getHref(), plant2);

		href2Plant.put(plant3.getHref(), plant3);
	}

	// TODO may need parameters
	public Collection<PlantInventoryEntryDTO> queryPlantCatalog() {
		return href2Plant.values();
	}

	public boolean isAvailableDuringPeriod(String href, BusinessPeriod period) {
		return true;
	}

	public PlantInventoryEntryDTO readOneExternal(String href) {
		PlantInventoryEntryDTO maybePlantInventoryEntry = href2Plant.get(href);

		if (isNull(maybePlantInventoryEntry)) {
			throw new IllegalArgumentException("Cannot fetch plant inventory entry by href: " + href);
		}

		return maybePlantInventoryEntry;
	}

	public RentItPurchaseOrderDTO createPurchaseOrder(RentItCreatePORequestDTO request) {
		return RentItPurchaseOrderDTO.of(
				"http://ramirent.ee:5999/api/orders-list/584",
				RentItPurchaseOrderStatus.PENDING_APPROVE
		);
	}

}
