package com.example.rentit.sales.rest.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class PlantHireRequest {
    String id;
    String status;
}

