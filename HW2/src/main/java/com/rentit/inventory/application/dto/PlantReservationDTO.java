package com.rentit.inventory.application.dto;

import com.rentit.common.application.dto.BusinessPeriodDTO;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class PlantReservationDTO {
    Long _id;
    PlantInventoryItemDTO plantInfo;
    BusinessPeriodDTO schedule;
}
