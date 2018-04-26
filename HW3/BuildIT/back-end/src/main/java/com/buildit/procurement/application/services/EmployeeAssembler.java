package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.EmployeeDTO;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.controller.rest.PlantHireRestController;
import com.buildit.procurement.domain.model.Employee;
import com.buildit.procurement.domain.model.PlantHireRequest;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class EmployeeAssembler extends ResourceAssemblerSupport<Employee, EmployeeDTO> {

    public EmployeeAssembler() {
        super(PlantHireRestController.class, EmployeeDTO.class);

    }

    @Override
    public EmployeeDTO toResource(Employee employee) {
        EmployeeDTO dto = createResourceWithId(employee.getId(), employee);

        dto.set_id(employee.getId());

        return dto;
    }

}
