package com.buildit.procurement.web.controller;

import com.buildit.common.application.dto.StringDTO;
import com.buildit.procurement.application.dto.CommentDTO;
import com.buildit.procurement.application.dto.CreatePlantHireRequestDTO;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.application.services.CommentService;
import com.buildit.procurement.application.services.PlantHireRequestService;
import com.buildit.procurement.web.controller.dev.controllers.EmployeeController;
import com.buildit.procurement.web.controller.dev.controllers.SupplierController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.buildit.procurement.domain.enums.PHRStatus;
import com.buildit.procurement.application.dto.ExtensionRequestDTO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@CrossOrigin()
@RequestMapping("/api/requests")
public class PlantHireRequestController {

	@Autowired
	PlantHireRequestService service;

	@Autowired
	CommentService commentService;

	@GetMapping
	public ResponseEntity<Resources<PlantHireRequestDTO>> readAll(@PathVariable PHRStatus status) {
		List<PlantHireRequestDTO> requests = service.getAll(status);

		return transformIntoResponse(requests);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Resource<PlantHireRequestDTO>> readOne(@PathVariable Long id) {
		return transformIntoResponse(service.readOne(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Resource<PlantHireRequestDTO>> update(@PathVariable Long id, @RequestBody CreatePlantHireRequestDTO updateRequest) {
		PlantHireRequestDTO updatedPHR = service.updateRequest(
				id,
				updateRequest.getConstructionSiteId(),
				updateRequest.getSupplierId(),
				updateRequest.getPlantHref(),
				updateRequest.getRentalPeriod().toModel());

		return transformIntoResponse(updatedPHR, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Resource<PlantHireRequestDTO>> create(@RequestBody @Valid CreatePlantHireRequestDTO createRequest) {
		PlantHireRequestDTO newlyCreatedPHR = service.addRequest(
				createRequest.getConstructionSiteId(),
				createRequest.getSupplierId(),
				createRequest.getPlantHref(),
				createRequest.getRentalPeriod().toModel());

		return transformIntoResponse(newlyCreatedPHR, HttpStatus.CREATED);
	}

	@PostMapping("/{id}/addComment")
	public CommentDTO addComment(@PathVariable Long id, @RequestBody StringDTO textDTO) {
		CommentDTO comment = commentService.create(id, textDTO.getValue());

		comment.removeLinks();

		return comment;
	}

	@PostMapping("/{id}/accept")
	public ResponseEntity<Resource<PlantHireRequestDTO>> accept(@PathVariable Long id) {
		PlantHireRequestDTO acceptedRequest = service.accept(id);

		return transformIntoResponse(acceptedRequest, HttpStatus.OK);
	}

	@PostMapping("/{id}/cancel")
	public ResponseEntity<Resource<PlantHireRequestDTO>> cancel(@PathVariable Long id) throws Exception {
		PlantHireRequestDTO cancelledRequest = service.cancel(id);

		return transformIntoResponse(cancelledRequest, HttpStatus.OK);
	}

	@PostMapping("/{id}/reject")
	public ResponseEntity<Resource<PlantHireRequestDTO>> reject(@PathVariable Long id) {
		PlantHireRequestDTO rejectedRequest = service.reject(id);

		return transformIntoResponse(rejectedRequest, HttpStatus.OK);
	}

    @PostMapping("/{id}/requestExtension")
    public ResponseEntity<Resource<PlantHireRequestDTO>> extend(@PathVariable Long id,
                                                                @RequestBody @Valid ExtensionRequestDTO extensionRequestDTO) {
        PlantHireRequestDTO extendRequest = service.extend(id, extensionRequestDTO);

        return transformIntoResponse(extendRequest, HttpStatus.OK);
    }

	private ResponseEntity<Resources<PlantHireRequestDTO>> transformIntoResponse(Collection<PlantHireRequestDTO> requestDTOs) {
		Collection<PlantHireRequestDTO> requestDTOsWithLinksFixed =
				requestDTOs
						.stream()
						.map(dto -> fixLinks(dto))
						.collect(Collectors.toList());

		return new ResponseEntity<>(
				new Resources<PlantHireRequestDTO>(requestDTOsWithLinksFixed),
				new HttpHeaders(),
				HttpStatus.OK
		);
	}

	private ResponseEntity<Resource<PlantHireRequestDTO>> transformIntoResponse(PlantHireRequestDTO requestDTO, HttpStatus status) {
		PlantHireRequestDTO requestDTOWithLinksFixed = fixLinks(requestDTO);

		return new ResponseEntity<Resource<PlantHireRequestDTO>>(
				new Resource<PlantHireRequestDTO>(requestDTOWithLinksFixed),
				new HttpHeaders(),
				status
		);
	}

	private PlantHireRequestDTO fixLinks(PlantHireRequestDTO requestDTO) {
		requestDTO.removeLinks();
		requestDTO.getComments().forEach(c -> c.removeLinks());
		requestDTO.getSite().removeLinks();
		requestDTO.getSite().getLinks().add(linkTo(
				methodOn(ConstructionSiteController.class)
						.readOne(requestDTO.getSite().get_id()))
				.withSelfRel());
		requestDTO.getSupplier().removeLinks();
		requestDTO.getSupplier().getLinks().add(linkTo(
				methodOn(SupplierController.class)
						.readOne(requestDTO.getSupplier().get_id()))
				.withSelfRel());
		requestDTO.getRequestingSiteEngineer().removeLinks();
		requestDTO.getRequestingSiteEngineer().getLinks().add(linkTo(
				methodOn(EmployeeController.class)
						.readOne(requestDTO.getRequestingSiteEngineer().get_id()))
				.withSelfRel());

		requestDTO.getPlant().removeLinks();

		if (!isNull(requestDTO.getPurchaseOrder())) {
			requestDTO.getPurchaseOrder().removeLinks();
		}

		Link selfRel = linkTo(
				methodOn(PlantHireRequestController.class)
						.readOne(requestDTO.get_id()))
				.withSelfRel();

		Link acceptRel = linkTo(
				methodOn(PlantHireRequestController.class)
						.accept(requestDTO.get_id()))
				.withRel("accept");

		Link rejectRel = linkTo(
				methodOn(PlantHireRequestController.class)
						.reject(requestDTO.get_id()))
				.withRel("reject");

		Link addCommentRel = linkTo(
				methodOn(PlantHireRequestController.class)
						.addComment(requestDTO.get_id(), null))
				.withRel("addComment");

		requestDTO.getLinks().add(selfRel);
		requestDTO.getLinks().add(acceptRel);
		requestDTO.getLinks().add(rejectRel);
		requestDTO.getLinks().add(addCommentRel);

		return requestDTO;
	}

}