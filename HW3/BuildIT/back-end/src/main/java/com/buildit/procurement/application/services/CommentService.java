package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.CommentDTO;
import com.buildit.procurement.domain.model.Comment;
import com.buildit.procurement.domain.model.PlantHireRequest;
import com.buildit.procurement.domain.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	PlantHireRequestService plantHireRequestService;

	@Autowired
	CommentAssembler assembler;

	public CommentDTO addComment(Long plantHireRequestId, String text) {
		PlantHireRequest plantHireRequest = plantHireRequestService.getById(plantHireRequestId);

		Comment comment = new Comment();

		comment.setPlantHireRequest(plantHireRequest);

		comment.setContents(text);

		plantHireRequest.getComments().add(comment);

		comment = commentRepository.save(comment);

		return assembler.toResource(comment);
	}

}
