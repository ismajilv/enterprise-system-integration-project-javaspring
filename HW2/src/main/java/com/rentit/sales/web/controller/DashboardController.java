package com.rentit.sales.web.controller;

import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.repository.InventoryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.web.dto.CatalogQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class	DashboardController	{
    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepo;

    @Autowired
    InventoryRepository inventoryRepository;

    @GetMapping("/catalog/form")
    public String getQueryForm(Model model)	{
        model.addAttribute("catalogQuery", new CatalogQueryDTO());
        return	"dashboard/catalog/query-form";
    }

    @PostMapping("catalog/query")
    public String executeQuery(CatalogQueryDTO query, Model model) {
        List<PlantInventoryEntry> result = inventoryRepository.findAvailablePlants(query.getName().toLowerCase(), query.getRentalPeriod().getStartDate(), query.getRentalPeriod().getEndDate());
        model.addAttribute("plants", result);

        PurchaseOrderDTO po = new PurchaseOrderDTO();
        po.setRentalPeriod(query.getRentalPeriod());
        model.addAttribute("po", po);
        return "dashboard/catalog/query-result";
    }

}