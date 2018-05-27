package com.rentit.invoicing.application.exceptions;


public class InvoiceNotFoundException extends Exception{
    public InvoiceNotFoundException(Long id) {
            super(String.format("Invoice not found! (Invoice id: %d)", id));
        }
}


