package com.buildit.procurement.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Data
public class PlantInventoryEntryDTO  extends ResourceSupport {

	String href;

	String name;

}