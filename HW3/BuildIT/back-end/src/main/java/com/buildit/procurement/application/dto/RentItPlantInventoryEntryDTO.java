package com.buildit.procurement.application.dto;

import com.buildit.common.application.dto.MoneyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor(force = true)
public class RentItPlantInventoryEntryDTO {

	Long _id;
	String name;
	String description;
	@Column(precision = 8, scale = 2)
	BigDecimal price;
	Map<String, Map<String, String>> _links; // self->href->"http://..."

}