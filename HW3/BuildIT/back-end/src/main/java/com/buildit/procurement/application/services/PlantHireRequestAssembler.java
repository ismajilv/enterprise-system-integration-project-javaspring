package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.CommentDTO;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.controller.rest.PlantHireRestController;
import com.buildit.procurement.domain.model.Comment;
import com.buildit.procurement.domain.model.PlantHireRequest;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class PlantHireRequestAssembler extends ResourceAssemblerSupport<PlantHireRequest, PlantHireRequestDTO> {

    public PlantHireRequestAssembler() {
        super(PlantHireRestController.class, PlantHireRequestDTO.class);

    }

    @Override
    public PlantHireRequestDTO toResource(PlantHireRequest plantHireRequest) {
        PlantHireRequestDTO dto = createResourceWithId(plantHireRequest.getId(), plantHireRequest);

        dto.set_id(plantHireRequest.getId());

        return dto;
    }

}
