package com.sung.demo.users;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;

import com.sung.demo.common.BaseControllerTest;

class UsersControllerTest extends BaseControllerTest {
	
	@Autowired
	UsersRepo usersRepo;
	
	@BeforeEach
	void setup() {
		usersRepo.clear();
	}

	@Test
	@DisplayName("Get /user/{bems}, whose role is the admin")
	void getUserByBems() throws Exception {
		Users user = usersRepo.addAdmin();
		mockMvc.perform(get("/users/" + user.getBems()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
			.andExpect(jsonPath("bems").value(user.getBems()))
			.andExpect(jsonPath("fname").value(user.getFname()))
			.andExpect(jsonPath("lname").value(user.getLname()))
			.andExpect(jsonPath("role").value(user.getRole()))
			.andExpect(jsonPath("admin").value(true))
			.andExpect(jsonPath("_links.self").exists())
			.andExpect(jsonPath("_links.self.href", Matchers.containsString("/users/" + user.getBems())));
	}
	
	@Test
	@DisplayName("Get /user/{bems}, whose role is the user")
	void getUserByUserBems() throws Exception {
		Users user = usersRepo.addUser();
		mockMvc.perform(get("/users/" + user.getBems()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
			.andExpect(jsonPath("bems").value(user.getBems()))
			.andExpect(jsonPath("fname").value(user.getFname()))
			.andExpect(jsonPath("lname").value(user.getLname()))
			.andExpect(jsonPath("role").value(user.getRole()))
			.andExpect(jsonPath("admin").value(false))
			.andExpect(jsonPath("_links.self").exists())
			.andExpect(jsonPath("_links.self.href", Matchers.containsString("/users/" + user.getBems())));
	}

}
