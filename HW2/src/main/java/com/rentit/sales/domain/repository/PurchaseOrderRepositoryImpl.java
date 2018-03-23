package com.rentit.sales.domain.repository;

import com.rentit.sales.domain.model.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class PurchaseOrderRepositoryImpl implements CustomPurchaseOrderRepository {
    @Autowired
    EntityManager em;

    @Override
    public List<PurchaseOrder> findPendingPurchaseOrders() {
        return em.createQuery("select po from PurchaseOrder po where po.status = com.example.demo.models.POStatus.PENDING"
                , PurchaseOrder.class)
                .getResultList();
    }

}
