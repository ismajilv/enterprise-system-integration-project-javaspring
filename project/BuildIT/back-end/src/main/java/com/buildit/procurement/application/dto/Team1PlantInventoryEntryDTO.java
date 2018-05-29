package com.buildit.procurement.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor(force = true)
public class Team1PlantInventoryEntryDTO {
    Long _id;
    String name;
    String description;
    @Column(precision = 8, scale = 2)
    BigDecimal price;
    Map<String, Map<String, String>> _links; // self->href->"http://..."
}