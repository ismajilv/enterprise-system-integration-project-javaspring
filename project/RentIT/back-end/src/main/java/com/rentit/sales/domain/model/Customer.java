package com.rentit.sales.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;

	@Column
	String companyName;

}