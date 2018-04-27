package com.buildit.procurement.web.controller;

import com.buildit.common.application.dto.StringDTO;
import com.buildit.procurement.application.dto.CommentDTO;
import com.buildit.procurement.application.dto.CreatePlantHireRequestDTO;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.application.services.CommentService;
import com.buildit.procurement.application.services.PlantHireRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class PlantHireRequestController {

    @Autowired
    PlantHireRequestService service;

    @Autowired
    CommentService commentService;

    @GetMapping
    public List<PlantHireRequestDTO> getAll() {
        return service.getAll();
    }

    @PostMapping
    public PlantHireRequestDTO create(@RequestBody @Valid CreatePlantHireRequestDTO createRequest) {
        return service.addRequest(
                createRequest.getConstructionSiteId(),
                createRequest.getSupplierId(),
                createRequest.getPlantHref(),
                createRequest.getRentalPeriod().toModel(),
                createRequest.getRentalCost().toModel()
        );
    }

    @PostMapping("/{id}/comments")
    public CommentDTO addComment(@PathVariable Long id, @RequestBody StringDTO textDTO) {
        return commentService.addComment(id, textDTO.getValue());
    }

    @PostMapping("/{id}/accept")
    public PlantHireRequestDTO accept(@PathVariable Long id) {
        return service.accept(id);
    }

    @PostMapping("/{id}/reject")
    public PlantHireRequestDTO reject(@PathVariable Long id) {
        return service.reject(id);
    }

}