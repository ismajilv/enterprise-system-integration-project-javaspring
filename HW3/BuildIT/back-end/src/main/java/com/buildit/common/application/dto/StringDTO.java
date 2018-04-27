package com.buildit.common.application.dto;

import com.buildit.common.domain.model.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
@NoArgsConstructor(force = true)
public class StringDTO {

	String value;

}