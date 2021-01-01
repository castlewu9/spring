package com.sung.demo.hateoas.user;

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
public class UserController {

  UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping
  public List<User> list() {
    return userRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getById(@PathVariable String id) {
    Optional<User> user = userRepository.findById(id);
    return user.map(u -> u.add(linkTo(methodOn(UserController.class).getById(id)).withSelfRel()))
        .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

}
