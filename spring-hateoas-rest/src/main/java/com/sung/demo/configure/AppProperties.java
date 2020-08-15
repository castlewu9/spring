package com.sung.demo.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "sung.api")
@Getter @Setter
public class AppProperties {

	private String version;
	
	private String title;
}
