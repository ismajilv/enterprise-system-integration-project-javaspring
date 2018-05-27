package com.buildit.procurement.application.dto;

import com.buildit.procurement.domain.enums.InvoiceStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor(force = true)
public class RentItInvoiceDTO {

    Long _id;

    InvoiceStatus status;

    LocalDate dueDate;

    Boolean latePayment;

    RentItPurchaseOrderDTO purchaseOrderDTO;

    Map<String, Map<String, String>> _links; // self->href->"http://..."
}
