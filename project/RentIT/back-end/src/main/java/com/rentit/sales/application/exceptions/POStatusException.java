package com.rentit.sales.application.exceptions;


import com.rentit.sales.domain.model.POStatus;

public class POStatusException extends Exception{
    public POStatusException(String action, POStatus status) {
        super(String.format("Not allowed to %s purchase order with status %s!", action, status));
    }
}