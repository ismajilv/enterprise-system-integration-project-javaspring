package com.buildit.procurement.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Supplier {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;

	String name;

}