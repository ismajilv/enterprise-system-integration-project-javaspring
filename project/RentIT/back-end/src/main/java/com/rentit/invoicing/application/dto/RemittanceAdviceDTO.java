package com.rentit.invoicing.application.dto;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class RemittanceAdviceDTO extends ResourceSupport {
    Long _id;
    String note;
}
