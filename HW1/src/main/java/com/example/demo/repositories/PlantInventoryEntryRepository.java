package com.example.demo.repositories;

import com.example.demo.models.PlantInventoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantInventoryEntryRepository extends JpaRepository<PlantInventoryEntry, Long>, PlantSearchRepository {

	List<PlantInventoryEntry> findByNameContaining(String namePart);
}