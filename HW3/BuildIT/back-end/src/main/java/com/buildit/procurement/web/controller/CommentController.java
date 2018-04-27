package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.CommentDTO;
import com.buildit.procurement.application.services.CommentAssembler;
import com.buildit.procurement.domain.model.Comment;
import com.buildit.procurement.domain.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentAssembler commentAssembler;

    @GetMapping
    public List<CommentDTO> getAll() {
        List<Comment> all = commentRepository.findAll();

        return all.stream().map(c -> commentAssembler.toResource(c)).collect(Collectors.toList());
    }

}