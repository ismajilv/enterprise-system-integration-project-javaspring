package com.buildit.procurement.application.dto;

import com.buildit.procurement.domain.enums.POStatus;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class PurchaseOrderDTO extends ResourceSupport {

	String href;

	POStatus status;

}
