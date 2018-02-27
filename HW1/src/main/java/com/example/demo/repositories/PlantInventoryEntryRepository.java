package com.example.demo.repositories;

import com.example.demo.models.PlantInventoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantInventoryEntryRepository extends JpaRepository<PlantInventoryEntry, Long>, PlantInventoryEntryRepositoryCustomQueries {

	@Query("select pie from PlantInventoryEntry pie where LOWER(pie.name) like lower(concat('%', ?1,'%'))")
	List<PlantInventoryEntry> findByNameContaining(String namePart);

}