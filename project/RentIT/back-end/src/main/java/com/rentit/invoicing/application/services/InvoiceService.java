package com.rentit.invoicing.application.services;

import com.rentit.invoicing.application.dto.InvoiceDTO;
import com.rentit.invoicing.application.dto.RemittanceAdviceDTO;
import com.rentit.invoicing.domain.model.Invoice;
import com.rentit.invoicing.domain.model.InvoiceStatus;
import com.rentit.invoicing.domain.model.RemittanceAdvice;
import com.rentit.invoicing.domain.repository.InvoiceRepository;
import com.rentit.invoicing.domain.repository.RemittanceAdviceRepository;
import com.rentit.sales.application.dto.POCallbackDTO;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class InvoiceService {

	@Value("${buildItUrl}")
	String buildItUrl;

	@Autowired
	InvoiceRepository repository;

	@Autowired
	PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
	RemittanceAdviceRepository remittanceAdviceRepository;

	@Autowired
	InvoiceAssembler assembler;

	public InvoiceDTO readOne(Long id) {
		Optional<Invoice> maybeInvoice = repository.findById(id);

		if (!maybeInvoice.isPresent()) {
			throw new IllegalArgumentException("No invoice found by ID: " + id);
		}

		return assembler.toResource(maybeInvoice.get());
	}

	public Invoice findInvoice(Long id) {
		return repository.getOne(id);
	}

	public Boolean isInvoiceExisting(Long id) { return  repository.getOne(id) == null;}

	public Invoice rejectInvoice(Long invoiceId){
		Invoice invoice = findInvoice(invoiceId);
		invoice.setStatus(InvoiceStatus.REJECTED_BY_CUSTOMER);
		return  repository.save(invoice);
	}

	public Invoice confirmInvoice(Long invoiceId){
		Invoice invoice = findInvoice(invoiceId);
		invoice.setStatus(InvoiceStatus.PAID);
		return  repository.save(invoice);
	}

	public Invoice acceptInvoice(Long invoiceId, RemittanceAdviceDTO dto){
		Invoice invoice = findInvoice(invoiceId);
		RemittanceAdvice remittanceAdvice = new RemittanceAdvice();
		remittanceAdvice.setInvoice(invoice);
		remittanceAdvice.setNote(dto.getNote());
		final RemittanceAdvice savedRem = remittanceAdviceRepository.save(remittanceAdvice);
		invoice.setRemittanceAdvice(savedRem);
		return repository.save(invoice);
	}

	public void createInvoice(Long poId){

		final PurchaseOrder purchaseOrder = purchaseOrderRepository.getOne(poId);
		Invoice invoice = new Invoice();
		invoice.setPurchaseOrderId(purchaseOrder.getId());
		invoice.setDueDate(LocalDate.now().plusWeeks(2));
		invoice.setPayableAmount(purchaseOrder.getTotal());
		invoice.setStatus(InvoiceStatus.PENDING);

		InvoiceDTO invoiceDTO = assembler.toResource(repository.save(invoice));

		//POCallbackDTO callback = POCallbackDTO.of("http://localhost:8090/api/orders/" + po.get_id(), po.getStatus());

		HttpHeaders headers = new HttpHeaders();

		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<InvoiceDTO> entity = new HttpEntity<>(invoiceDTO, headers);

		RestTemplate restTemplate = new RestTemplate();

		checkBuildItUrl();

		restTemplate.exchange(buildItUrl + "/callbacks/submitInvoice",
				HttpMethod.POST,
				entity,
				new ParameterizedTypeReference<String>() {}
		);
	}

	private void checkBuildItUrl() {
		requireNonNull(buildItUrl);
		if (buildItUrl.length() < 10) throw new IllegalArgumentException("Configure buildItUrl properly: " + buildItUrl);
	}
}
