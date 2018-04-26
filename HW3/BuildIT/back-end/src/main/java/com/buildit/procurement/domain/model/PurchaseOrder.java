package com.buildit.procurement.domain.model;

import com.buildit.procurement.domain.enums.POStatus;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.net.URI;

// TODO - VALUE OBJECT?
@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class PurchaseOrder {

    @Id // It's the identifier
    @URL
    String href;

    POStatus status;

}