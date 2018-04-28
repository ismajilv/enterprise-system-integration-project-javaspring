package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.CommentDTO;
import com.buildit.procurement.application.services.CommentAssembler;
import com.buildit.procurement.application.services.CommentService;
import com.buildit.procurement.domain.model.Comment;
import com.buildit.procurement.domain.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
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