package com.buildit.procurement.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ConstructionSite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String address;

}
