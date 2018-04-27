package com.buildit.procurement.application.dto;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class CommentDTO extends ResourceSupport  {

	Long _id;

	String contents;

}