package com.example.demo.inventory.domain.repository;

import com.example.demo.inventory.domain.model.PlantInventoryItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PlantInventoryItemRepositoryCustomQueries {

	List<PlantInventoryItem> findPlantsNotHiredForPeriod(LocalDate start, LocalDate end);

}
