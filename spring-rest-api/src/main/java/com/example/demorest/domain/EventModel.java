package com.example.demorest.domain;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;

import com.example.demorest.controller.EventController;

//public class EventModel {
//	
//	EntityModel<Event> model;
//	
//	public EventModel(Event event) {
//		this.model = EntityModel.of(event);
//		model.add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
////		model.add(linkTo(EventController.class).withRel("event"));
////		model.add(linkTo(methodOn(EventController.class).getEvent(event.getId(), null)).withSelRel());
//	}
//	
//	public EntityModel<Event> getModel() {
//		return model;
//	}
//}

public class EventModel extends RepresentationModel<EventModel> {
	
	private Integer id;
	
	public EventModel() {
		add(linkTo(EventController.class).slash(id).withSelfRel());
//		super(event, links);
//		this.model = EntityModel.of(event);
//		model.add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
//		model.add(linkTo(EventController.class).withRel("event"));
//		model.add(linkTo(methodOn(EventController.class).getEvent(event.getId(), null)).withSelRel());
	}
}