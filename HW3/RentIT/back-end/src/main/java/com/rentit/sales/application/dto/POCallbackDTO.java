package com.rentit.sales.application.dto;

import com.rentit.sales.domain.model.POStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class POCallbackDTO {
    String href;
    POStatus status;
}
