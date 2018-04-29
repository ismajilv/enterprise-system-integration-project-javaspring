package com.buildit.procurement.web.controller;

import com.buildit.common.application.dto.RentItPOStatusUpdateDTO;
import com.buildit.procurement.application.services.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/callbacks")
public class RentItCallbackController {

	@Autowired
	PurchaseOrderService service;

	@PostMapping("/orderStateChanged")
	public void notifyState(@RequestBody @Valid RentItPOStatusUpdateDTO statusDTO) {
		service.updateStatus(statusDTO.getHref(), statusDTO.getValue());
	}

}