package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.CommentDTO;
import com.buildit.procurement.domain.model.Comment;
import com.buildit.procurement.domain.model.PlantHireRequest;
import com.buildit.procurement.domain.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

	@Autowired
	CommentRepository repository;

	@Autowired
	CommentAssembler assembler;

	@Autowired
	PlantHireRequestService plantHireRequestService;

	@Transactional
	public CommentDTO create(Long plantHireRequestId, String text) {
		PlantHireRequest plantHireRequest = plantHireRequestService.getById(plantHireRequestId);

		Comment comment = new Comment();

		comment.setPlantHireRequest(plantHireRequest);

		comment.setContents(text);

		plantHireRequest.getComments().add(comment);

		comment = repository.save(comment);

		return assembler.toResource(comment);
	}

	@Transactional(readOnly = true)
	public List<CommentDTO> readAll() {
		List<Comment> all = repository.findAll();

		return all.stream().map(c -> assembler.toResource(c)).collect(Collectors.toList());
	}

}