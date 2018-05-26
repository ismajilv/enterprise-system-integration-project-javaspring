package com.rentit.inventory.application.exceptions;

public class PlantNotFoundException extends Exception{
    public PlantNotFoundException(Long id) {
        super(String.format("Plant not found! (Plant id: %d)", id));
    }
}
