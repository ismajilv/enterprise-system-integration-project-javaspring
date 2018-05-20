package com.buildit.procurement.application.dto;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class CommentDTO extends ResourceSupport {

	Long _id;

	String contents;

	@Override
	public boolean hasLinks() {
		return false;
	}

	@Override
	public boolean hasLink(String rel) {
		return false;
	}

}