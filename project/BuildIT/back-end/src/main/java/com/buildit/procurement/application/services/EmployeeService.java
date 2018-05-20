package com.buildit.procurement.application.services;

import com.buildit.common.application.dto.EmployeeDTO;
import com.buildit.common.application.service.EmployeeAssembler;
import com.buildit.common.domain.model.Employee;
import com.buildit.procurement.domain.enums.Role;
import com.buildit.procurement.domain.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository repository;

	@Autowired
	EmployeeAssembler assembler;

	@Transactional
	public EmployeeDTO create(Role role, String firstName, String lastName) {
		Employee employee = new Employee();

		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setRole(role);

		employee = repository.save(employee);

		return assembler.toResource(employee);
	}

	@Transactional(readOnly = true)
	public EmployeeDTO findOneOfRole(Role role) {
		return assembler.toResource(findOneModelOfRole(role));
	}

	private Employee findOneModelOfRole(Role role) {
		List<Employee> found = repository.findByRole(role);

		if (found.isEmpty()) {
			throw new IllegalArgumentException("No employees found with role: " + role);
		}

		return found.get(0);
	}

	// TODO implement with Spring security & drop role parameter
	@Transactional(readOnly = true)
	public Employee getLoggedInEmployee(Role role) {
		return findOneModelOfRole(role);
	}

	@Transactional(readOnly = true)
	public EmployeeDTO readOne(Long id) {
		return assembler.toResource(repository.getOne(id));
	}

}
