package com.buildit.procurement.domain.model;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

// TODO - VALUE OBJECT?
@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class PlantInventoryEntry {

    @Id // It's the identifier
    @URL
    String href;

    String name;

}