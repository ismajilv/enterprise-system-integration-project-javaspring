package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.application.dto.RentItPurchaseOrderDTO;
import com.buildit.procurement.application.services.ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class PlantHireRestController {

    @Autowired
    ProcurementService plantHireServices;

    @GetMapping("/plants")
    public ResponseEntity<?> findAvailablePlants(
            @RequestParam(name = "name") String plantName,
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
//        TODO: query available plants from RentIt
        return null;
    }

    @PatchMapping("/orders/{id}")
    public void updatePurchaseOrder(@PathVariable(name = "id") Long poId,
                                    @RequestBody RentItPurchaseOrderDTO po){
//        TODO: update purchase order
    }

    @PostMapping("/requests")
    public ResponseEntity<?> createHireRequest(@RequestBody PlantHireRequestDTO request){
//        TODO: create new plant hire request
        return null;
    }

    @GetMapping("/requests")
    public ResponseEntity<?> findAllRequests(){
//        TODO: return all plant hire requests
        return null;
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<?> findRequest(@PathVariable(name = "id") Long requestId){
//        called repeatedly to get the PO status
//        TODO: return plant hire requests with given id
        return null;
    }

    @PatchMapping("/request-management/requests/{id}")
    public ResponseEntity<?> approveHireRequest(@PathVariable(name = "id") Long id,
                                          @RequestBody PlantHireRequestDTO updatedRequest){
//        TODO: update the state of the plant hire request to ACCEPTED, generate and send new PO to RentIT
        return null;
    }

    @DeleteMapping("/request-management/requests/{id}")
    public ResponseEntity<?> rejectHireRequest(@PathVariable(name = "id") Long id){
//        TODO: update the state of the plant hire request to REJECTED
        return null;
    }
}
