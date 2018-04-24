package com.buildit.logistics.controller.rest;

import com.buildit.logistics.application.dto.PlantHireRequestDTO;
import com.buildit.logistics.application.services.PlantHireServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PlantHireRestController {

    @Autowired
    PlantHireServices plantHireServices;

    @PostMapping("/requests")
    public Resource<?> createHireRequest(@RequestBody PlantHireRequestDTO request){
//        TODO: create new plant hire request
        return null;
    }

    @GetMapping("/requests")
    public Resources<?> getAllRequests(){
//        TODO: return all plant hire requests
        return null;
    }

    @PatchMapping("/request-management/requests/{id}")
    public Resource<?> approveHireRequest(@PathVariable(name = "id") Long id){
//        TODO: update the state of the plant hire request to ACCEPTED
        return null;
    }

    @DeleteMapping("/request-management/requests/{id}")
    public Resource<?> rejectHireRequest(@PathVariable(name = "id") Long id){
//        TODO: update the state of the plant hire request to REJECTED
        return null;
    }
}
