package com.example.demorest.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demorest.domain.Account;
import com.example.demorest.domain.CurrentUser;
import com.example.demorest.domain.Event;
import com.example.demorest.domain.dto.EventDto;
import com.example.demorest.domain.validator.EventValidator;
import com.example.demorest.repository.EventRepository;


@RestController
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

	private final EventRepository eventRepository;
	
	private final ModelMapper modelMapper;
	
	private final EventValidator eventValidator;

	public EventController(EventRepository eventRepository, ModelMapper modelMapper, EventValidator eventValidator) {
		this.eventRepository = eventRepository;
		this.modelMapper = modelMapper;
		this.eventValidator = eventValidator;
	}

	@PostMapping
	public ResponseEntity<?> createEvent(@RequestBody @Valid EventDto eventDto,
											Errors errors,
											@CurrentUser Account currentUser) {
		if (errors.hasErrors()) {
			return badRequest(errors);
		}
		
		eventValidator.validate(eventDto, errors);
		if (errors.hasErrors()) {
			return badRequest(errors);
		}
		
		Event mappedEvent = modelMapper.map(eventDto, Event.class);
		mappedEvent.update();
		mappedEvent.setManager(currentUser);
		
		Event event = eventRepository.save(mappedEvent);
		
		WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(event.getId());
		URI location = selfLinkBuilder.toUri();

		EntityModel<Event> model = EntityModel.of(event);
		model.add(linkTo(EventController.class).withRel("query-events"));
		model.add(selfLinkBuilder.withRel("update-event"));
		model.add(Link.of("/docs/index.html#resources-events-create").withRel("profile"));
		model.add(linkTo(EventController.class).slash(event.getId()).withSelfRel());

		return ResponseEntity.created(location).body(model);
	}
	
	@GetMapping
	public ResponseEntity<?> queryEvents(Pageable pageable,
											PagedResourcesAssembler<Event> assembler,
											@CurrentUser Account currentUser) {
		Page<Event> page = this.eventRepository.findAll(pageable);
		var pagedModel = assembler.toModel(page, e -> {
			EntityModel<Event> m = EntityModel.of(e);
			m.add(linkTo(EventController.class).slash(e.getId()).withSelfRel());
			return m;
		});
		pagedModel.add(Link.of("/docs/index.html#resources-events-list").withRel("profile"));
		if (currentUser != null) {
			pagedModel.add(linkTo(EventController.class).withRel("create-event"));
		}
		return ResponseEntity.ok(pagedModel);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Event>> getEvent(@PathVariable Integer id,
														@CurrentUser Account currentUser) {
		Optional<Event> optionalEvent = this.eventRepository.findById(id);
		if (optionalEvent.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Event event = optionalEvent.get();
		EntityModel<Event> model = EntityModel.of(event);
		model.add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
		model.add(Link.of("/docs/index.html#resources-events-get").withRel("profile"));
		
		if (event.getManager().equals(currentUser)) {
			model.add(linkTo(EventController.class).slash(event.getId()).withRel("update-event"));
		}
		return ResponseEntity.ok(model);
	}
	
	private ResponseEntity<EntityModel<Errors>> badRequest(Errors errors) {
		EntityModel<Errors> model = EntityModel.of(errors);
		model.add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
		return ResponseEntity.badRequest().body(model);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> putEvent(@PathVariable Integer id,
										@RequestBody @Valid EventDto eventDto, 
										Errors errors,
										@CurrentUser Account currentUser) {
		Optional<Event> optionalEvent = this.eventRepository.findById(id);
		if (optionalEvent.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		if (errors.hasErrors()) {
			return badRequest(errors);
		}
		
		eventValidator.validate(eventDto, errors);
		if (errors.hasErrors()) {
			return badRequest(errors);
		}
		
		Event existingEvent = optionalEvent.get();
		if (existingEvent.getManager() == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		else if (!existingEvent.getManager().equals(currentUser)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		this.modelMapper.map(eventDto, existingEvent);
		
		Event event = eventRepository.save(existingEvent);
		event.update();
		WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(event.getId());
		URI location = selfLinkBuilder.toUri();

		EntityModel<Event> model = EntityModel.of(event);
//		model.add(linkTo(EventController.class).withRel("query-events"));
//		model.add(selfLinkBuilder.withRel("update-event"));
		model.add(Link.of("/docs/index.html#resources-events-update").withRel("profile"));
		model.add(linkTo(EventController.class).slash(event.getId()).withSelfRel());

		return ResponseEntity.ok(model);
	}
}
