package com.buildit.common.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDate;

@Value(staticConstructor = "of")
// @NoArgsConstructor(force = true)
// @AllArgsConstructor(staticName = "of")
public class BusinessPeriodDTO extends ResourceSupport {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate endDate;

}