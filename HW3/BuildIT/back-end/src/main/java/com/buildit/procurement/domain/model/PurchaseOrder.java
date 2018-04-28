package com.buildit.procurement.domain.model;

import com.buildit.procurement.domain.enums.POStatus;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;

@Entity
@Data
public class PurchaseOrder {

	@Id // It's the identifier
	@URL
	String href;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	POStatus status;

}