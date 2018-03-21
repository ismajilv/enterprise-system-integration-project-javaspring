package com.example.demo.sales.application.dto;

import com.example.demo.common.application.dto.BusinessPeriodDTO;
import com.example.demo.inventory.application.dto.PlantInventoryEntryDTO;
import com.example.demo.sales.domain.model.POStatus;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data//Update class such as it extends Spring hateoas Resource support
public class PurchaseOrderDTO  extends ResourceSupport {
    Long _id;
    PlantInventoryEntryDTO plant;

    BusinessPeriodDTO rentalPeriod;

    @Column(precision = 8, scale = 2)
    BigDecimal total;
    POStatus status;

}