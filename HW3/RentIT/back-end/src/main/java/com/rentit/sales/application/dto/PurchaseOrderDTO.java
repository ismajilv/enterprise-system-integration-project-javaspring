package com.rentit.sales.application.dto;

import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.sales.domain.model.POStatus;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PurchaseOrderDTO extends ResourceSupport{
    Long _id;
    PlantInventoryEntryDTO plant;
    BusinessPeriodDTO rentalPeriod;

    @Column(precision = 8, scale = 2)
    BigDecimal total;

    POStatus status;
    AddressDTO address;
}
