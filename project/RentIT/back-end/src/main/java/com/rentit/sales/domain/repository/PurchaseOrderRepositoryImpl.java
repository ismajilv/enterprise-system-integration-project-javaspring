package com.rentit.sales.domain.repository;

import com.rentit.sales.domain.model.POStatus;
import com.rentit.sales.domain.model.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import java.util.List;

public class PurchaseOrderRepositoryImpl implements CustomPurchaseOrderRepository {

    @Autowired
    EntityManager em;

    @Override
    public List<PurchaseOrder> findPendingPurchaseOrders() {
        return em.createQuery("select po from PurchaseOrder po where po.status = ?1"
                , PurchaseOrder.class)
                .setParameter(1, POStatus.PENDING_APPROVAL)
                .getResultList();
    }

}
