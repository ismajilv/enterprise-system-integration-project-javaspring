package com.buildit.procurement.domain.repository;

import com.buildit.procurement.domain.model.Comment;
import com.buildit.procurement.domain.model.ConstructionSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstructionSiteRepository  extends JpaRepository<ConstructionSite, Long> {
}
