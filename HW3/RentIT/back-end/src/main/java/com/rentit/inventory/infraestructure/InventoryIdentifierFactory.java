package com.rentit.inventory.infraestructure;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InventoryIdentifierFactory {
    public String nextIdentifier() {return UUID.randomUUID().toString();}
}
