package com.example.demo.controllers;
import com.example.demo.repositories.MaintenancePlanRepository;
import com.example.demo.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

@RestController
public class PlantInventoryEntryController {
    @Autowired
    MaintenancePlanRepository maintenancePlanRepository;

    @GetMapping("/")
    public List<Pair<Integer, Long>> findCorrectiveRepairsByYearForPeriod() {
        return maintenancePlanRepository.findCorrectiveRepairsByYearForPeriod((2014),(2018));
    }



}
