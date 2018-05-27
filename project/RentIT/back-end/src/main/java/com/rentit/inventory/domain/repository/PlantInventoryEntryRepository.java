package com.rentit.inventory.domain.repository;

import com.rentit.inventory.domain.model.PlantInventoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantInventoryEntryRepository extends JpaRepository<PlantInventoryEntry, Long> {

    @Query("select p from PlantInventoryEntry p where p.id = ?1")
    PlantInventoryEntry findOneById(long id);

    List<PlantInventoryEntry> findByNameContaining(String str);

    @Query("select p from PlantInventoryEntry p where p.name like %?1%")
    List<PlantInventoryEntry> finderMethod(String mini);

    @Query(value="select * from plant_inventory_entry where LOWER(name) like %?1%", nativeQuery=true)
    List<PlantInventoryEntry> finderMethodV2(String name);
}
