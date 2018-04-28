package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.EmployeeDTO;
import com.buildit.procurement.domain.model.PlantHireRequest;
import com.buildit.procurement.web.controller.PlantHireRequestController;
import com.buildit.procurement.domain.model.Employee;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class EmployeeAssembler extends ResourceAssemblerSupport<Employee, EmployeeDTO> {

    public EmployeeAssembler() {
        super(PlantHireRequestController.class, EmployeeDTO.class);

    }

    @Override
    public EmployeeDTO toResource(Employee employee) {
        EmployeeDTO dto = createResourceWithId(employee.getId(), employee);

        dto.set_id(employee.getId());

        return dto;
    }

}
