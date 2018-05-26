package com.rentit.sales.domain.model;

import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantReservation;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;


@Embeddable
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class CustomerContactPerson {

	@Column(nullable = false)
	String firstName;

	@Column(nullable = false)
	String lastName;

	@Column(nullable = false)
	String telephoneNumber;

}
