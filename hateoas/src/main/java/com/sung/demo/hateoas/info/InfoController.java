package com.sung.demo.hateoas.info;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sung.demo.hateoas.configure.AppProperties;

@RestController
@RequestMapping(value = "/info", produces = MediaTypes.HAL_JSON_VALUE)
public class InfoController {

  AppProperties properties;

  public InfoController(AppProperties properties) {
    this.properties = properties;
  }

  @GetMapping
  public ResponseEntity<Info> get() {
    Info info =
        Info.builder().title(properties.getTitle()).version(properties.getVersion()).build();
    info.add(linkTo(methodOn(InfoController.class).get()).withSelfRel());
    return ResponseEntity.ok(info);
  }

}
