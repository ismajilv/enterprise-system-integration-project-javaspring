package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.ConstructionSiteDTO;
import com.buildit.procurement.domain.model.ConstructionSite;
import com.buildit.procurement.domain.repository.ConstructionSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConstructionSiteService {

	@Autowired
	ConstructionSiteRepository constructionSiteRepository;

	@Autowired
	ConstructionSiteAssembler constructionSiteAssembler;

	@Transactional
	public ConstructionSite create(String address) {
		ConstructionSite constructionSite = new ConstructionSite();

		constructionSite.setAddress(address);

		constructionSiteRepository.save(constructionSite);

		return constructionSite;
	}

	@Transactional(readOnly = true)
	public ConstructionSiteDTO readOne(Long id) {
		return constructionSiteAssembler.toResource(readModel(id));
	}

	@Transactional(readOnly = true)
	public List<ConstructionSiteDTO> readAll() {
		List<ConstructionSite> all = constructionSiteRepository.findAll();

		return all.stream().map(cs -> constructionSiteAssembler.toResource(cs)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ConstructionSite readModel(Long id) {
		Optional<ConstructionSite> maybeConstructionSite = constructionSiteRepository.findById(id);

		if (!maybeConstructionSite.isPresent()) {
			throw new IllegalArgumentException("Cannot find construction site by id: " + id);
		}

		return maybeConstructionSite.get();
	}

}
