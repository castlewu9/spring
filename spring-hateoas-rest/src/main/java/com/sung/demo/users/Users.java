package com.sung.demo.users;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Users extends RepresentationModel<Users> {

	@Id
	private String bems;

	private String fname;
	private String lname;
	private String status;
	private String role;
	
	@JsonProperty("admin")
	private boolean isAdmin() {
		return role.equalsIgnoreCase("admin");
	}
}
