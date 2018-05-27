package com.buildit.procurement.application.dto;

import com.buildit.procurement.domain.enums.InvoiceStatus;

import java.time.LocalDate;

public class RentItInvoiceDTO {

    Long _id;

    InvoiceStatus status;

    LocalDate dueDate;

    Boolean latePayment;

    RentItPurchaseOrderDTO purchaseOrderDTO;
}
