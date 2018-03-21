package com.example.demo.common.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable //This means that it is persistent but depended on another class and it will be embedded in the class
@Value

@NoArgsConstructor(force=true,access= AccessLevel.PRIVATE)
@AllArgsConstructor(staticName="of")

public class BusinessPeriod {
    LocalDate startDate;
    LocalDate endDate;
}
