package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.ConstructionSiteDTO;
import com.buildit.procurement.application.services.ConstructionSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/sites")
public class ConstructionSiteController {

	@Autowired
	ConstructionSiteService service;

	@GetMapping
	public List<ConstructionSiteDTO> readAll() {
		List<ConstructionSiteDTO> sites = service.readAll();

		sites.forEach(site -> fixLinks(site));

		return sites;
	}

	@GetMapping("/{id}")
	public ConstructionSiteDTO readOne(@PathVariable Long id) {
		ConstructionSiteDTO site = service.readOne(id);

		fixLinks(site);

		return site;
	}

	private void fixLinks(ConstructionSiteDTO site) {
		site.removeLinks();
		site.getLinks().add(linkTo(
				methodOn(ConstructionSiteController.class)
						.readOne(site.get_id()))
				.withSelfRel());
	}

}