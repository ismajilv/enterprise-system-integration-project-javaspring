package com.rentit.inventory.rest;

import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.application.dto.PlantInventoryItemDTO;
import com.rentit.inventory.application.exceptions.PlantNotFoundException;
import com.rentit.inventory.application.services.InventoryService;
import com.rentit.inventory.application.services.PlantInventoryEntryAssembler;
import com.rentit.inventory.application.services.PlantInventoryItemAssembler;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.repository.InventoryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/plants")
public class PlantInventoryEntryRestController {

    @Autowired
    InventoryService inventoryService;

    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;


    @GetMapping()
    public List<PlantInventoryEntryDTO> findAvailablePlants(
            @RequestParam(name = "name") String plantName,
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<PlantInventoryEntryDTO> found = inventoryService.findAvailable(plantName, startDate, endDate);

        fixLinks(found);

        return found;
    }

    private void fixLinks(List<PlantInventoryEntryDTO> found) {
        found.forEach(p -> fixLinks(p));
    }

    private void fixLinks(PlantInventoryEntryDTO p) {
        p.removeLinks();
        p.getLinks().add(
                linkTo(
                        methodOn(PlantInventoryEntryRestController.class)
                                .readOnePlant(p.get_id()))
                        .withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public PlantInventoryEntryDTO readOnePlant(@PathVariable Long id) {
        PlantInventoryEntryDTO plant = plantInventoryEntryAssembler.toResource(inventoryService.findPlantInventoryEntry(id));

        fixLinks(plant);

        return plant;
    }

    @ExceptionHandler(PlantNotFoundException.class)
    public ResponseEntity<Object> handPlantNotFoundException(PlantNotFoundException ex) {
        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
