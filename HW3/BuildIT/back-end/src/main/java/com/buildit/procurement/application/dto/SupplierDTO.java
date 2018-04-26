package com.buildit.procurement.application.dto;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;

@Data
public class SupplierDTO extends ResourceSupport {

	Long _id;

	// TODO add other fields & to assembler

}