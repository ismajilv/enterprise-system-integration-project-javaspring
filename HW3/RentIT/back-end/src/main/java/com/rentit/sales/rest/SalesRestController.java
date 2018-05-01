package com.rentit.sales.rest;

import com.rentit.common.application.exceptions.PlantNotFoundException;
import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.application.dto.PlantInventoryItemDTO;
import com.rentit.inventory.application.services.InventoryService;
import com.rentit.inventory.application.services.PlantInventoryEntryAssembler;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.sales.application.dto.POExtensionDTO;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.application.services.PurchaseOrderAssembler;
import com.rentit.sales.application.services.SalesService;
import com.rentit.sales.domain.model.POStatus;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.validator.PurchaseOrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.afford;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "http://localhost:6080")
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
                        methodOn(SalesRestController.class)
                                .readOnePlant(p.get_id()))
                        .withSelfRel()
        );
    }

    @GetMapping("/plants/{id}")
    public PlantInventoryEntryDTO readOnePlant(@PathVariable Long id) {
        PlantInventoryEntryDTO plant = plantInventoryEntryAssembler.toResource(inventoryService.findPlantInventoryEntry(id));

        fixLinks(plant);

        return plant;
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getPending() {
        List<PurchaseOrder> pendingOrders = salesService.findPendingOrders();

        List<PurchaseOrderDTO> ret = pendingOrders.stream().map(po -> purchaseOrderAssembler.toResource(po)).collect(Collectors.toList());

        return new ResponseEntity<>(new Resources<>(ret), HttpStatus.OK);
    }

    @PostMapping("/orders/{poId}/reject")
    public ResponseEntity<?> rejectPurchaseOrder(@PathVariable("poId") Long poId)  throws URISyntaxException {
        final PurchaseOrder po = salesService.rejectPurchaseOrder(poId);

        DataBinder binder = new DataBinder(po);

        binder.addValidators(new PurchaseOrderValidator());
        binder.validate();

        if (binder.getBindingResult().hasErrors()) {
            return new ResponseEntity<>(
                    new Resources<>(binder.getBindingResult().getAllErrors()),
                    HttpStatus.BAD_REQUEST);
        }

        PurchaseOrderDTO poDTO = purchaseOrderAssembler.toResource(po);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(poDTO.getRequiredLink("self").getHref()));
        return new ResponseEntity<>(
                new Resource<PurchaseOrderDTO>(poDTO),
                headers,
                HttpStatus.OK);
    }

    @PostMapping("/orders/{poId}/accept")
    public ResponseEntity<?> acceptPurchaseOrder(
            @RequestParam(name = "piiId") Long piiId,
            @PathVariable("poId") Long poId)  throws URISyntaxException, PlantNotFoundException {

        if (!inventoryService.isPlantInventoryItemExisting(piiId)) {
            throw new PlantNotFoundException(piiId);
        }
        final PurchaseOrder po = salesService.acceptPurchaseOrder(poId, piiId);

        DataBinder binder = new DataBinder(po);

        binder.addValidators(new PurchaseOrderValidator());
        binder.validate();

        if (binder.getBindingResult().hasErrors()) {
            return new ResponseEntity<>(
                    new Resources<>(binder.getBindingResult().getAllErrors()),
                    HttpStatus.BAD_REQUEST);
        }

        PurchaseOrderDTO poDTO = purchaseOrderAssembler.toResource(po);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(poDTO.getRequiredLink("self").getHref()));
        return new ResponseEntity<>(
                new Resource<PurchaseOrderDTO>(poDTO),
                headers,
                HttpStatus.OK);
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

        if(!inventoryService.isPlantInventoryEntryExisting(partialPODTO.getPlant().get_id())) {
            throw new PlantNotFoundException(partialPODTO.getPlant().get_id());
        }

        PurchaseOrder preparedForSavePO = salesService.preparePurchaseOrderForSave(partialPODTO.getPlant().get_id(), partialPODTO.getRentalPeriod().getStartDate(), partialPODTO.getRentalPeriod().getEndDate());

        DataBinder binder = new DataBinder(preparedForSavePO);

        binder.addValidators(new PurchaseOrderValidator());
        binder.validate();

        if (binder.getBindingResult().hasErrors()) {
            return new ResponseEntity<>(
                    new Resources<>(binder.getBindingResult().getAllErrors()),
                    HttpStatus.BAD_REQUEST);
        }

        // not ideal breaking save into two parts, but beats having web in service layer
        PurchaseOrder newlyCreatedPO = salesService.save(preparedForSavePO);

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
    public ResponseEntity<Object> handPlantNotFoundException(PlantNotFoundException ex) {
        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
