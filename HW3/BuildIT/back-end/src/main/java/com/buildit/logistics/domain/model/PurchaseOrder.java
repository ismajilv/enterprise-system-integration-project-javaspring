package com.buildit.logistics.domain.model;

import com.buildit.logistics.domain.enums.POStatus;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.net.URI;

@Entity
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class PurchaseOrder {
    @Id
    @Column(name = "purchase_order_id")
    Long id;

    @Column(name = "hyperlink")
    URI href;

    @Column(name = "status")
    POStatus status;
}
