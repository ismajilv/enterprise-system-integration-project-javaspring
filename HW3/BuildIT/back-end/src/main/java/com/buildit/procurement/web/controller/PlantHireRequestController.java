package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.CommentDTO;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.application.services.CommentAssembler;
import com.buildit.procurement.application.services.PlantHireRequestAssembler;
import com.buildit.procurement.domain.model.Comment;
import com.buildit.procurement.domain.model.PlantHireRequest;
import com.buildit.procurement.domain.repository.CommentRepository;
import com.buildit.procurement.domain.repository.PlantHireRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hire")
public class PlantHireRequestController {

    @Autowired
    PlantHireRequestRepository plantHireRequestRepository;

    @Autowired
    PlantHireRequestAssembler plantHireRequestAssembler;

    @GetMapping
    public List<PlantHireRequestDTO> getAll() {
        List<PlantHireRequest> all = plantHireRequestRepository.findAll();

        return all.stream().map(phr -> plantHireRequestAssembler.toResource(phr)).collect(Collectors.toList());
    }

}