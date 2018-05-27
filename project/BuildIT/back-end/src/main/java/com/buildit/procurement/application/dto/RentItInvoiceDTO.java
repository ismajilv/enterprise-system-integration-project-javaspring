package com.buildit.procurement.application.dto;

import com.buildit.procurement.domain.enums.InvoiceStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor(force = true)
public class RentItInvoiceDTO {

    Long _id;

    Long purchaseOrderId;

    LocalDate dueDate;

    BigDecimal payableAmount;

    Map<String, Map<String, String>> _links; // self->href->"http://..."

}
