package com.buildit.procurement.application.dto;

import com.buildit.common.application.dto.BusinessPeriodDTO;
import com.buildit.common.application.dto.MoneyDTO;
import com.buildit.procurement.domain.model.ConstructionSite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Data
public class PlantHireRequestDTO  extends ResourceSupport {

	Long _id;

	List<CommentDTO> comments;

	ConstructionSiteDTO site;

	BusinessPeriodDTO rentalPeriod;

	SupplierDTO supplier;

	PlantInventoryEntryDTO plant;

	MoneyDTO rentalCost;

	// TODO add other fields & to assembler

}
