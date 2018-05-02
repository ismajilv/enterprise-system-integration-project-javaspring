package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.CommentDTO;
import com.buildit.procurement.application.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:6080")
@RequestMapping("/api/comments")
/**
 * Just for testing use; can be deleted in production
 */
public class CommentController {

	@Autowired
	CommentService service;

	@GetMapping
	public Collection<CommentDTO> readAll() {
		List<CommentDTO> comments = service.readAll();

		comments.forEach(dto -> dto.removeLinks());

		return comments;
	}

}