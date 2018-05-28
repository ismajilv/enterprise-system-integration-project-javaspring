package com.rentit.sales.rest;

import com.rentit.inventory.application.exceptions.PlantNotFoundException;
import com.rentit.inventory.application.services.InventoryService;
import com.rentit.inventory.application.services.PlantInventoryEntryAssembler;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.model.PlantReservation;
import com.rentit.sales.application.dto.CreatePORequestDTO;
import com.rentit.sales.application.dto.ExtensionRequestDTO;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.application.exceptions.POStatusException;
import com.rentit.sales.application.services.PurchaseOrderAssembler;
import com.rentit.sales.application.services.SalesService;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.validator.PurchaseOrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
@CrossOrigin
@RequestMapping("/api/orders")
public class PurchaseOrderController {

    @Autowired
    InventoryService inventoryService;
    @Autowired
    SalesService salesService;
    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;
    @Autowired
    PurchaseOrderAssembler purchaseOrderAssembler;


    @GetMapping()
    public ResponseEntity<?> getOrders(@RequestParam(required = false) String status) {

        List<PurchaseOrder> orders;
        if(status != null && status.equalsIgnoreCase("ALL")){
            orders = salesService.findAllOrders();
        } else {
            orders  = salesService.findPendingOrders();
        }

        List<PurchaseOrderDTO> ret = orders.stream().map(po -> purchaseOrderAssembler.toResource(po)).collect(Collectors.toList());

        return new ResponseEntity<>(new Resources<>(ret), HttpStatus.OK);
    }


    @PostMapping("/{poId}/dispatch")
    public ResponseEntity<?> dispatchPurchaseOrder(@PathVariable("poId") Long poId) throws URISyntaxException {

        final PurchaseOrder po = salesService.dispatchPurchaseOrder(poId);
        PurchaseOrderDTO poDTO = purchaseOrderAssembler.toResource(po);
        salesService.notifyCustomer(poDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(poDTO.getRequiredLink("self").getHref()));
        return new ResponseEntity<>(
                new Resource<PurchaseOrderDTO>(poDTO),
                headers,
                HttpStatus.OK);
    }

    @PostMapping("/{poId}/return")
    public ResponseEntity<?> markAsReturned(@PathVariable("poId") Long poId) throws URISyntaxException {

        final PurchaseOrder po = salesService.markAsReturned(poId);
        PurchaseOrderDTO poDTO = purchaseOrderAssembler.toResource(po);
        salesService.notifyCustomer(poDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(poDTO.getRequiredLink("self").getHref()));
        return new ResponseEntity<>(
                new Resource<PurchaseOrderDTO>(poDTO),
                headers, HttpStatus.OK);
    }

    @PostMapping("/{poId}/deliver")
    public ResponseEntity<?> deliverPurchaseOrder(@PathVariable("poId") Long poId) throws URISyntaxException {

        final PurchaseOrder po = salesService.deliverPurchaseOrder(poId);
        PurchaseOrderDTO poDTO = purchaseOrderAssembler.toResource(po);
        salesService.notifyCustomer(poDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(poDTO.getRequiredLink("self").getHref()));
        return new ResponseEntity<>(
                new Resource<PurchaseOrderDTO>(poDTO),
                headers, HttpStatus.OK);
    }


    @PostMapping("/{poId}/customer_reject")
    public ResponseEntity<?> rejectByCustomer(@PathVariable("poId") Long poId) throws URISyntaxException {

        final PurchaseOrder po = salesService.customerRejectPurchaseOrder(poId);
        PurchaseOrderDTO poDTO = purchaseOrderAssembler.toResource(po);
        salesService.notifyCustomer(poDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(poDTO.getRequiredLink("self").getHref()));
        return new ResponseEntity<>(
                new Resource<PurchaseOrderDTO>(poDTO),
                headers, HttpStatus.OK);
    }


    @PostMapping("/{poId}/cancel")
    public ResponseEntity<?> cancelPurchaseOrder(@PathVariable("poId") Long poId) throws POStatusException, URISyntaxException {

        final PurchaseOrder po = salesService.cancelPurchaseOrder(poId);
        PurchaseOrderDTO poDTO = purchaseOrderAssembler.toResource(po);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(poDTO.getRequiredLink("self").getHref()));

        return new ResponseEntity<>(
                new Resource<PurchaseOrderDTO>(poDTO),
                headers,
                HttpStatus.OK);
    }

    @PostMapping("/{poId}/reject")
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
        salesService.notifyCustomer(poDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(poDTO.getRequiredLink("self").getHref()));
        return new ResponseEntity<>(
                new Resource<PurchaseOrderDTO>(poDTO),
                headers,
                HttpStatus.OK);
    }

    @PostMapping("/{poId}/accept")
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
        salesService.notifyCustomer(poDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(poDTO.getRequiredLink("self").getHref()));
        return new ResponseEntity<>(
                new Resource<PurchaseOrderDTO>(poDTO),
                headers,
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
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

    @PostMapping()
    public ResponseEntity<?> createPurchaseOrder(@RequestBody CreatePORequestDTO partialPODTO) throws URISyntaxException, PlantNotFoundException {

        if(!inventoryService.isPlantInventoryEntryExisting(partialPODTO.getPlantId())) {
            throw new PlantNotFoundException(partialPODTO.getPlantId());
        }

        PurchaseOrder preparedForSavePO =
                salesService.preparePurchaseOrderForSave(partialPODTO.getPlantId(),
                        partialPODTO.getRentalPeriod().getStartDate(),
                        partialPODTO.getRentalPeriod().getEndDate(),
                        partialPODTO.getDeliveryAddress());

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
                        linkTo(methodOn(PurchaseOrderController.class).
                                fetchPurchaseOrder(newlyCreatedPO.getId())).withSelfRel()),
                headers,
                HttpStatus.CREATED);
    }

    // Add extension into purchase order dto instead?
//    @GetMapping("/{id}/extension")
//    public Resources<Resource<ExtensionRequestDTO>> retrievePurchaseOrderExtension(@PathVariable("id") Long id) {
//        ExtensionRequestDTO extension = new ExtensionRequestDTO();
//        extension.setNewEndDate(LocalDate.now().plusWeeks(1));
//        return new Resources<>(extension, linkTo(methodOn(PurchaseOrderController.class).retrievePurchaseOrderExtensions(id)).withSelfRel().andAffordance(afford(methodOn(PurchaseOrderController.class).requestPurchaseOrderExtension(null, id))));
//    }

    @PostMapping("/{id}/requestExtension")
    public ExtensionRequestDTO requestPurchaseOrderExtension(@RequestBody ExtensionRequestDTO request, @PathVariable("id") Long purchaseOrderId) {
        final PurchaseOrder oldPO= salesService.findPurchaseOrder(purchaseOrderId);
        final LocalDate from = oldPO.getRentalPeriod().getEndDate().plusDays(1);
        final LocalDate to = request.getNewEndDate();
        PlantInventoryItem extendableItem = null;
        final PlantReservation reservation = oldPO.getReservation();
        if(reservation != null && salesService.isPIIExtentable(reservation.getPlant(), from, to)){
            extendableItem = oldPO.getReservation().getPlant();
        } else {
            extendableItem = salesService.getAlternativeItem(oldPO.getPlant(), from, to);
        }
        final PurchaseOrder extendedPO = salesService.extendPo(oldPO.getId(), extendableItem, from, to);
        if(extendableItem == null){
            return ExtensionRequestDTO.of(oldPO.getRentalPeriod().getEndDate(), Boolean.FALSE, "Couldn't find any available items.", oldPO.getTotal());
        }

        return ExtensionRequestDTO.of(oldPO.getRentalPeriod().getEndDate(), Boolean.TRUE, null, extendedPO.getTotal());
    }

    @ExceptionHandler(PlantNotFoundException.class)
    public ResponseEntity<Object> handPlantNotFoundException(PlantNotFoundException ex) {
        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
