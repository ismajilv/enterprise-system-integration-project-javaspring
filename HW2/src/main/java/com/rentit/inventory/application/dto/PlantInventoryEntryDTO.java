package com.rentit.inventory.application.dto;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
public class PlantInventoryEntryDTO extends ResourceSupport {
    Long _id;
    String name;
    String description;
    @Column(precision = 8, scale = 2)
    BigDecimal price;
}
