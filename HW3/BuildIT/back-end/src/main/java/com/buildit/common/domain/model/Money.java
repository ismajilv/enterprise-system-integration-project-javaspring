package com.buildit.common.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Value // only getters and no setters (cannot change attributes)
@NoArgsConstructor(force=true,access= AccessLevel.PRIVATE)
@AllArgsConstructor(staticName="of")
public class Money {

	// String currency; TBD later

	@Column(precision=8,scale=2, nullable = false)
	BigDecimal total;

}