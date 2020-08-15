package com.example.demorest.configure;

import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "my-app")
@Getter @Setter
public class AppProperties {

	@NotEmpty
	String adminName;
	
	@NotEmpty
	String adminPassword;
	
	@NotEmpty
	String userName;
	
	@NotEmpty
	String userPassword;
	
	@NotEmpty
	String clientId;
	
	@NotEmpty
	String clientSecret;
}
