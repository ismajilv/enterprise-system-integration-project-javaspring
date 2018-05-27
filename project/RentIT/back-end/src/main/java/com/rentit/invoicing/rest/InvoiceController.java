package com.rentit.invoicing.rest;

import com.rentit.invoicing.application.dto.InvoiceDTO;
import com.rentit.invoicing.application.dto.RemittanceAdviceDTO;
import com.rentit.invoicing.application.exceptions.InvoiceNotFoundException;
import com.rentit.invoicing.application.services.InvoiceAssembler;
import com.rentit.invoicing.application.services.InvoiceService;
import com.rentit.invoicing.domain.model.Invoice;
import com.rentit.invoicing.domain.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@CrossOrigin()
@RequestMapping("/api/invoices")
public class InvoiceController {

	@Autowired
	InvoiceService service;

	@Autowired
	InvoiceRepository invoiceRepository;

	@Autowired
	InvoiceAssembler invoiceAssembler;

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {
		InvoiceDTO dto = service.readOne(id);

		return new ResponseEntity<>(
				new Resource<InvoiceDTO>(dto),
				new HttpHeaders(),
				HttpStatus.OK);
	}

	@PostMapping("/{id}/reject")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> rejectInvoice(@PathVariable("id") Long id, @RequestBody InvoiceDTO dto) throws InvoiceNotFoundException, URISyntaxException {
		if (!service.isInvoiceExisting(id)) {
			throw new InvoiceNotFoundException(id);
		}
		final Invoice invoice = service.rejectInvoice(id);

		InvoiceDTO invoiceDTO = invoiceAssembler.toResource(invoice);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(new URI(invoiceDTO.getRequiredLink("self").getHref()));
		return new ResponseEntity<>(
				new Resource<InvoiceDTO>(invoiceDTO),
				headers,
				HttpStatus.OK);
	}

	@PostMapping("/{id}/accept")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> acceptInvoice(@PathVariable("id") Long id, @RequestBody RemittanceAdviceDTO dto) throws InvoiceNotFoundException, URISyntaxException {
		if (!service.isInvoiceExisting(id)) {
			throw new InvoiceNotFoundException(id);
		}
		final Invoice invoice = service.acceptInvoice(id, dto);

		InvoiceDTO invoiceDTO = invoiceAssembler.toResource(invoice);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(new URI(invoiceDTO.getRequiredLink("self").getHref()));
		return new ResponseEntity<>(
				new Resource<InvoiceDTO>(invoiceDTO),
				headers,
				HttpStatus.OK);
	}

	@PostMapping("/{id}/confirm")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> confirmInvoice(@PathVariable("id") Long id) throws InvoiceNotFoundException, URISyntaxException {
		if (!service.isInvoiceExisting(id)) {
			throw new InvoiceNotFoundException(id);
		}
		final Invoice invoice = service.confirmInvoice(id);

		InvoiceDTO invoiceDTO = invoiceAssembler.toResource(invoice);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(new URI(invoiceDTO.getRequiredLink("self").getHref()));
		return new ResponseEntity<>(
				new Resource<InvoiceDTO>(invoiceDTO),
				headers,
				HttpStatus.OK);
	}

	@ExceptionHandler(InvoiceNotFoundException.class)
	public ResponseEntity<Object> handPlantNotFoundException(InvoiceNotFoundException ex) {
		return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

}
