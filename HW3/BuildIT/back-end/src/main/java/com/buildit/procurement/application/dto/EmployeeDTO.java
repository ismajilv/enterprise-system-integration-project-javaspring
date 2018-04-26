package com.buildit.procurement.application.dto;

import com.buildit.procurement.domain.enums.Role;
import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;

@Data
public class EmployeeDTO extends ResourceSupport {

    Long _id;

    // TODO add other fields & to assembler

}