package com.buildit.procurement.domain.repository;

import com.buildit.common.domain.model.Employee;
import com.buildit.procurement.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByRole(Role role);

}
