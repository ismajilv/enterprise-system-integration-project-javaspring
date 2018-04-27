package com.buildit.procurement.domain.repository;

import com.buildit.procurement.domain.model.Comment;
import com.buildit.procurement.domain.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}