package com.buildit.procurement.application.services;

import com.buildit.procurement.domain.model.ConstructionSite;
import com.buildit.procurement.domain.repository.ConstructionSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConstructionSiteService {

	@Autowired
	ConstructionSiteRepository constructionSiteRepository;

	public ConstructionSite create(String address) {
		ConstructionSite constructionSite = new ConstructionSite();

		constructionSite.setAddress(address);

		constructionSiteRepository.save(constructionSite);

		return constructionSite;
	}

	public ConstructionSite getById(Long id) {
		Optional<ConstructionSite> maybeConstructionSite = constructionSiteRepository.findById(id);

		if (!maybeConstructionSite.isPresent()) {
			throw new IllegalArgumentException("Cannot find construction site by id: " + id);
		}

		return maybeConstructionSite.get();
	}

}
