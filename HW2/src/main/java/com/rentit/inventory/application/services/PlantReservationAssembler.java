package com.rentit.inventory.application.services;

import com.rentit.inventory.application.dto.PlantReservationDTO;
import com.rentit.inventory.domain.model.PlantReservation;
import org.springframework.stereotype.Service;

@Service
public class PlantReservationAssembler {

    public PlantReservationDTO toResource(PlantReservation reservation) {
        PlantReservationDTO dto = new PlantReservationDTO();
        dto.set_id(reservation.getId());
        return dto;
    }
}