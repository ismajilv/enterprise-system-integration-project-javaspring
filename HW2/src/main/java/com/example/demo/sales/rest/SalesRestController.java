package com.example.demo.sales.rest;

import com.example.demo.common.application.exceptions.PlantNotFoundException;
import com.example.demo.inventory.application.services.InventoryService;
import com.example.demo.inventory.application.services.PlantInventoryEntryAssembler;
import com.example.demo.sales.application.dto.PurchaseOrderDTO;
import com.example.demo.sales.application.services.SalesService;
import com.example.demo.inventory.application.dto.PlantInventoryEntryDTO;
import com.example.demo.sales.application.dto.PurchaseOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
public class SalesRestController {
    @Autowired
    InventoryService inventoryService;


    @Autowired //Something we expect the runtime to provide
            SalesService salesService;

    @Autowired
    PlantInventoryEntryAssembler assembler;

    @GetMapping("/plants")
    public List<PlantInventoryEntryDTO> findAvailablePlants(
            @RequestParam(name = "name") String plantName,
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return inventoryService.findAvailable(plantName, startDate, endDate);
    }
//            @RequestParam(name = "name", required = false) Optional<String> plantName,
//            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
//            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate
//            /*@RequestParam(name = "name") String plantName,

// {
//        //System.out.println(salesService.queryPlantCatalog(null,null));
//        // TODO: Complete this part
//      //  return Collections.emptyList();
//    }

    @GetMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseOrderDTO fetchPurchaseOrder(@PathVariable("id") Long id) {
        return salesService.findPurchaseOrder(id);
    }

    @PostMapping("/orders")
    public ResponseEntity<PurchaseOrderDTO> createPurchaseOrder(@RequestBody PurchaseOrderDTO partialPODTO) throws URISyntaxException, PlantNotFoundException {
        PurchaseOrderDTO newlyCreatePODTO = salesService.createPurchaseOrder(partialPODTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(newlyCreatePODTO.getId().getHref()));

        return new ResponseEntity<>(newlyCreatePODTO, headers, HttpStatus.CREATED);
    }

    @ExceptionHandler(PlantNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handPlantNotFoundException(PlantNotFoundException ex) {
        // Code To handle Exception
    }
}