package com.sung.demo.app;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sung.demo.configure.AppProperties;

@RestController
@RequestMapping(value = "/app", produces = MediaTypes.HAL_JSON_VALUE)
public class AppController {

	AppProperties appProperties;
	
	public AppController(AppProperties appProperties) {
		this.appProperties = appProperties;
	}
	
	@GetMapping("/info")
	public ResponseEntity<App> info() {
		App app = App.builder()
				.version(appProperties.getVersion())
				.title(appProperties.getTitle())
				.build();
		
		app.add(linkTo(methodOn(AppController.class).info()).withSelfRel());
		return ResponseEntity.ok(app);
	}
}
