package com.rentit.inventory.application.dto;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class PlantInventoryItemDTO extends ResourceSupport {
    Long _id;
}
