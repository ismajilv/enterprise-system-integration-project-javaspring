package com.rentit.sales.domain.repository;
import com.rentit.sales.domain.model.PurchaseOrder;
import java.time.LocalDate;
import java.util.List;

public interface CustomPurchaseOrderRepository {
    List<PurchaseOrder> findPendingPurchaseOrders();
}

