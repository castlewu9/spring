package com.example.demorest.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@GetMapping("/api")
	public RepresentationModel<?> index() {
		RepresentationModel<?> index = RepresentationModel.of(null);
		index.add(linkTo(EventController.class).withRel("events"));
		return index;
	}
}
