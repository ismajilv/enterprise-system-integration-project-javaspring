package com.example.demo.inventory.rest;

import com.example.demo.inventory.application.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/inventory")
public class InventoryEntryRestController {

    @Autowired
    InventoryService inventoryService;


}
