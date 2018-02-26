package com.example.demo.repositories;

import com.example.demo.models.PlantInventoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlantInventoryEntryRepository extends JpaRepository<PlantInventoryEntry, Long>, PlantSearchRepository {

	@Query("select pie from PlantInventoryEntry pie where LOWER(pie.name) like lower(concat('%', ?1,'%'))")
	List<PlantInventoryEntry> findByNameContaining(String namePart);

}