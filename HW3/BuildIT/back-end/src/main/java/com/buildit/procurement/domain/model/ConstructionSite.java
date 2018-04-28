package com.buildit.procurement.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class ConstructionSite {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;

	String address;

}
