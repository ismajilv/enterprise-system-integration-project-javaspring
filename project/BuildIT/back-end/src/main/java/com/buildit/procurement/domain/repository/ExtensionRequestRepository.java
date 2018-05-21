package com.buildit.procurement.domain.repository;

import com.buildit.procurement.domain.model.Comment;
import com.buildit.procurement.domain.model.ExtensionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtensionRequestRepository extends JpaRepository<ExtensionRequest, Long> {
}
