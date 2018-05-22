package com.buildit.procurement.web.controller;

import com.buildit.common.application.dto.RentItPOStatusUpdateDTO;
import com.buildit.procurement.application.services.PlantHireRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin()
@RequestMapping("/callbacks")
public class RentItCallbackController {

	@Autowired
	PlantHireRequestService service;

	@PostMapping("/orderStateChanged")
	public void orderStateChanged(@RequestBody @Valid RentItPOStatusUpdateDTO statusDTO) {
		service.updateStatus(statusDTO.getHref(), statusDTO.getValue());
	}

}