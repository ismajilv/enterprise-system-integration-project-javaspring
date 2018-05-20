package com.rentit.inventory.application.services;

import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.inventory.application.dto.PlantReservationDTO;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.model.PlantReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantReservationAssembler {

    @Autowired
    PlantInventoryItemAssembler plantInventoryItemAssembler;

    public PlantReservationDTO toResource(PlantReservation reservation) {
        PlantReservationDTO dto = new PlantReservationDTO();
        dto.set_id(reservation.getId());
        dto.setSchedule(BusinessPeriodDTO.of(reservation.getSchedule().getStartDate(),reservation.getSchedule().getEndDate()));
        dto.setPlantInfo(plantInventoryItemAssembler.toResource(reservation.getPlant()));
        return dto;
    }
}