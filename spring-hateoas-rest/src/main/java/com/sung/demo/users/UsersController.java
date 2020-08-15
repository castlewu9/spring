package com.sung.demo.users;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users", produces = MediaTypes.HAL_JSON_VALUE)
public class UsersController {

	UsersRepository usersRepository;
	
	public UsersController(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}
	
	@GetMapping
	public List<Users> getAll() {
		return usersRepository.findAll();
	}
	
	@GetMapping("/{bems}")
	public ResponseEntity<Users> getUserbyBems(@PathVariable String bems) {
		Optional<Users> user = usersRepository.findByBems(bems);
		return user
				.map(o -> o.add(linkTo(methodOn(UsersController.class).getUserbyBems(bems)).withSelfRel()))
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

}
