package com.buildit.procurement.domain.repository;

import com.buildit.procurement.domain.model.PurchaseOrder;
import com.buildit.procurement.domain.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, String> {
	List<PurchaseOrder> findByExternalId(Long externalId);
}
