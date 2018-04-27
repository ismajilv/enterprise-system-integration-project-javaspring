package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.CommentDTO;
import com.buildit.procurement.application.dto.ConstructionSiteDTO;
import com.buildit.procurement.application.services.CommentAssembler;
import com.buildit.procurement.application.services.ConstructionSiteAssembler;
import com.buildit.procurement.domain.model.Comment;
import com.buildit.procurement.domain.model.ConstructionSite;
import com.buildit.procurement.domain.repository.CommentRepository;
import com.buildit.procurement.domain.repository.ConstructionSiteRepository;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/site")
public class ConstructionSiteController {

    @Autowired
    ConstructionSiteRepository constructionSiteRepository;

    @Autowired
    ConstructionSiteAssembler constructionSiteAssembler;

    @GetMapping
    public List<ConstructionSiteDTO> getAll() {
        List<ConstructionSite> all = constructionSiteRepository.findAll();

        return all.stream().map(cs -> constructionSiteAssembler.toResource(cs)).collect(Collectors.toList());
    }

}