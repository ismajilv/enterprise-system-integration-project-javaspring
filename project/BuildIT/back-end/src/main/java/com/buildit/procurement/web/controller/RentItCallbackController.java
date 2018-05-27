package com.buildit.procurement.web.controller;

import com.buildit.common.application.dto.RentItPOStatusUpdateDTO;
import com.buildit.procurement.application.dto.InvoiceDTO;
import com.buildit.procurement.application.dto.RentItInvoiceDTO;
import com.buildit.procurement.application.services.InvoiceService;
import com.buildit.procurement.application.services.PlantHireRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.Objects.isNull;

@RestController
@CrossOrigin()
@RequestMapping("/callbacks")
public class RentItCallbackController {

	@Autowired
	PlantHireRequestService service;

	@Autowired
	InvoiceService invoiceService;

	@PostMapping("/orderStateChanged")
	public void orderStateChanged(@RequestBody @Valid RentItPOStatusUpdateDTO statusDTO) {
		service.updateStatus(statusDTO.getHref(), statusDTO.getValue());
	}

	@PostMapping("/submitInvoice")
	public boolean submitInvoice(@RequestBody RentItInvoiceDTO invoice) {
		InvoiceDTO localInvoice = invoiceService.add(invoice);
		// TODO: return something as invoice accept or reject response
		return !isNull(localInvoice);
	}

}