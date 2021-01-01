package com.sung.demo.hateoas.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "demo.api")
@Getter
@Setter
public class AppProperties {

  private String title;

  private String version;

}
