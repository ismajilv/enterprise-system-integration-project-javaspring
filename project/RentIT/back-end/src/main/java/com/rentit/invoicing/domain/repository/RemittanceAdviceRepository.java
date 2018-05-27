package com.rentit.invoicing.domain.repository;
import com.rentit.invoicing.domain.model.RemittanceAdvice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemittanceAdviceRepository extends JpaRepository<RemittanceAdvice, Long> {
}
