package com.rentit.sales.rest;

import com.rentit.common.application.exceptions.PlantNotFoundException;
import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.application.services.InventoryService;
import com.rentit.inventory.application.services.PlantInventoryEntryAssembler;
import com.rentit.sales.application.dto.POExtensionDTO;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.application.services.PurchaseOrderAssembler;
import com.rentit.sales.application.services.SalesService;
import com.rentit.sales.domain.model.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.afford;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/sales")
public class SalesRestController {
    @Autowired
    InventoryService inventoryService;
    @Autowired
    SalesService salesService;
    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;
    @Autowired
    PurchaseOrderAssembler purchaseOrderAssembler;

    @GetMapping("/plants")
    public List<PlantInventoryEntryDTO> findAvailablePlants(
            @RequestParam(name = "name") String plantName,
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return inventoryService.findAvailable(plantName, startDate, endDate);
    }

    @GetMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> fetchPurchaseOrder(@PathVariable("id") Long id) {
        final PurchaseOrder foundPO = salesService.findPurchaseOrder(id);
        PurchaseOrderDTO dto = purchaseOrderAssembler.toResource(foundPO);
        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(new URI(dto.getRequiredLink("self").getHref()));

        return new ResponseEntity<>(
                new Resource<PurchaseOrderDTO>(dto),
                headers,
                HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody PurchaseOrderDTO partialPODTO) throws URISyntaxException, PlantNotFoundException {
        PurchaseOrder newlyCreatedPO = salesService.createPurchaseOrder(partialPODTO.getPlant().get_id(), partialPODTO.getRentalPeriod().getStartDate(), partialPODTO.getRentalPeriod().getEndDate());

        PurchaseOrderDTO dto = purchaseOrderAssembler.toResource(newlyCreatedPO);
        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(new URI(dto.getRequiredLink("self").getHref()));

        return new ResponseEntity<>(
                new Resource<PurchaseOrderDTO>(dto,
                        linkTo(methodOn(SalesRestController.class).
                                fetchPurchaseOrder(newlyCreatedPO.getId())).withSelfRel()),
                headers,
                HttpStatus.CREATED);
    }

    @GetMapping("/orders/{id}/extensions")
    public Resources<Resource<POExtensionDTO>> retrievePurchaseOrderExtensions(@PathVariable("id") Long id) {
        List<Resource<POExtensionDTO>> result = new ArrayList<>();
        POExtensionDTO extension = new POExtensionDTO();
        extension.setEndDate(LocalDate.now().plusWeeks(1));
        result.add(new Resource<>(extension));
        return new Resources<>(result, linkTo(methodOn(SalesRestController.class).retrievePurchaseOrderExtensions(id)).withSelfRel().andAffordance(afford(methodOn(SalesRestController.class).requestPurchaseOrderExtension(null, id))));
    }

    @PostMapping("/orders/{id}/extensions")
    public Resource<?> requestPurchaseOrderExtension(@RequestBody POExtensionDTO extension, @PathVariable("id") Long id) {
        return null;
    }

    @ExceptionHandler(PlantNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handPlantNotFoundException(PlantNotFoundException ex) {
        // Code To handle Exception
    }
}
