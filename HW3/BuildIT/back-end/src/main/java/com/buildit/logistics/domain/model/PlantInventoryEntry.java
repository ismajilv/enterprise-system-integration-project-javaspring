package com.buildit.logistics.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.net.URI;

@Entity
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class PlantInventoryEntry {
    @Id
    @Column(name = "plant_id")
    Long id;

    @Column(name = "hyperlink")
    @URL
    String href;

    @Column(name = "plant_name")
    String name;
}
