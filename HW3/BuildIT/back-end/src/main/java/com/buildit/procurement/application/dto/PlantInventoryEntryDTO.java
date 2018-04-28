package com.buildit.procurement.application.dto;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class PlantInventoryEntryDTO extends ResourceSupport {

	String href;

	String name;

}