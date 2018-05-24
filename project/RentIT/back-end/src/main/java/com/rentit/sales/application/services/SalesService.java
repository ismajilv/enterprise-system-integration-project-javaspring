package com.rentit.sales.application.services;

import com.rentit.common.application.exceptions.PlantNotFoundException;
import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.model.PlantReservation;
import com.rentit.inventory.domain.repository.InventoryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryItemRepository;
import com.rentit.inventory.domain.repository.PlantReservationRepository;
import com.rentit.sales.application.dto.POCallbackDTO;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.application.exceptions.POStatusException;
import com.rentit.sales.domain.model.Address;
import com.rentit.sales.domain.model.POStatus;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class SalesService {

    @Value("${buildItUrl}")
    String buildItUrl;

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;
    @Autowired
    PlantInventoryItemRepository plantInventoryItemRepository;
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    PlantReservationRepository plantReservationRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrder findPurchaseOrder(Long id) {
        return purchaseOrderRepository.getOne(id);
    }

    public PurchaseOrder rejectPurchaseOrder(Long id){
        PurchaseOrder po = findPurchaseOrder(id);
        po.reject();
        return save(po);
    }

    public PurchaseOrder deliverPurchaseOrder(Long id){
        PurchaseOrder po = findPurchaseOrder(id);
        po.deliver();
        return save(po);
    }

    public PurchaseOrder acceptPurchaseOrder(Long poId, Long piiId){
        PurchaseOrder po = findPurchaseOrder(poId);
        PlantInventoryItem pii = plantInventoryItemRepository.getOne(piiId);
        PlantReservation reservation = new PlantReservation();
        reservation.setPlant(pii);
        reservation.setSchedule(po.getRentalPeriod());
        reservation = plantReservationRepository.save(reservation);
        po.registerFirstAllocation(reservation);
        return save(po);
    }

    public PurchaseOrder cancelPurchaseOrder(Long id) throws POStatusException {
        PurchaseOrder po = findPurchaseOrder(id);
        if(!(po.getStatus().equals(POStatus.OPEN)||po.getStatus().equals(POStatus.PENDING))){
            throw new POStatusException("cancel", po.getStatus());
        }
        po.cancel();
        return save(po);
    }

    public PurchaseOrder dispatchPurchaseOrder(Long id){
        PurchaseOrder po = findPurchaseOrder(id);
        po.dispatch();
        return save(po);
    }

    public PurchaseOrder customerRejectPurchaseOrder(Long id){
        PurchaseOrder po = findPurchaseOrder(id);
        po.customerReject();
        return save(po);
    }

    public PurchaseOrder markAsReturned(Long id){
        PurchaseOrder po = findPurchaseOrder(id);
        po.markAsReturned();
        return save(po);
    }

    public PurchaseOrder preparePurchaseOrderForSave(Long plantId, LocalDate startDate, LocalDate endDate) throws PlantNotFoundException {
        PlantInventoryEntry plant = plantInventoryEntryRepository.getOne(plantId);
        PurchaseOrder po = PurchaseOrder.of(
                plant,
                BusinessPeriod.of(startDate, endDate));


// batch allocation ->
//        List<PlantInventoryItem> items = inventoryRepository.findAvailableItems(plant, startDate, endDate);
//
//        if (!items.isEmpty()) {
//            PlantReservation reservation = new PlantReservation();
//            reservation.setPlant(items.get(0));
//            reservation.setSchedule(BusinessPeriod.of(startDate, endDate));
//            plantReservationRepository.save(reservation);
//
//            po.registerFirstAllocation(reservation); // single responsibility vs ddd business intention trade-off
//
//            // validate PO
//            purchaseOrderRepository.save(po);
//        } else {
//            po.reject();
//            purchaseOrderRepository.save(po);
//        }


        return po; // not dto
    }

    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public List<PurchaseOrder> findPendingOrders() {
        return purchaseOrderRepository.findPendingPurchaseOrders();
    }

    public void notifyCustomer(PurchaseOrderDTO po) {
        POCallbackDTO callback = POCallbackDTO.of("http://localhost:8090/api/sales/orders/" + po.get_id(), po.getStatus());

        HttpHeaders headers = new HttpHeaders();

        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<POCallbackDTO> entity = new HttpEntity<>(callback, headers);

        RestTemplate restTemplate = new RestTemplate();

        checkBuildItUrl();

        restTemplate.exchange(buildItUrl + "/callbacks/orderStateChanged",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<String>() {}
        );
    }

    private void checkBuildItUrl() {
        requireNonNull(buildItUrl);
        if (buildItUrl.length() < 10) throw new IllegalArgumentException("Configure buildItUrl properly: " + buildItUrl);
    }

}
