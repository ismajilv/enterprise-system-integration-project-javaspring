package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.CommentDTO;
import com.buildit.procurement.domain.model.Comment;
import com.buildit.procurement.web.controller.PlantHireRequestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class CommentAssembler extends ResourceAssemblerSupport<Comment, CommentDTO> {

	public CommentAssembler() {
		super(PlantHireRequestController.class, CommentDTO.class);
	}

	@Override
	public CommentDTO toResource(Comment comment) {
		CommentDTO dto = createResourceWithId(comment.getId(), comment);

		dto.set_id(comment.getId());
		dto.setContents(comment.getContents());

		return dto;
	}

}
