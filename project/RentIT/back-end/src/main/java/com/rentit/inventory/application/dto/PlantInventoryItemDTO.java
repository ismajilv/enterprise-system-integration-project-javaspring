package com.rentit.inventory.application.dto;

import com.rentit.inventory.domain.model.EquipmentCondition;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class PlantInventoryItemDTO extends ResourceSupport {
    Long _id;
    String serialNumber;
    EquipmentCondition equipmentCondition;
    PlantInventoryEntryDTO plantInfo;
}
