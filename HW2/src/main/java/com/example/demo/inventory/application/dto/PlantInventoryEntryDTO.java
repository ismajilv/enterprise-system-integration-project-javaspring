package com.example.demo.inventory.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import sun.misc.Resource;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
@NoArgsConstructor(force=true)
public class PlantInventoryEntryDTO extends ResourceSupport {
    Long _id;
    String name;
    String description;
    @Column(precision = 8, scale = 2) //A way to preserve the number from big decimal.
    BigDecimal price;
}
