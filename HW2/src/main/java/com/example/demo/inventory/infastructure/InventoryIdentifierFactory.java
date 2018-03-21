package com.example.demo.inventory.infastructure;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InventoryIdentifierFactory {
    public String nextPurchaseOrderID() {
        return UUID.randomUUID().toString();
    }
}
