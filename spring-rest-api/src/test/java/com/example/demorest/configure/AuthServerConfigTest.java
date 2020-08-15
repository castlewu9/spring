package com.example.demorest.configure;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demorest.controller.BaseControllerTest;
import com.example.demorest.domain.Account;
import com.example.demorest.domain.AccountRole;
import com.example.demorest.service.AccountService;

class AuthServerConfigTest extends BaseControllerTest {
	
	@Autowired
	AccountService accountService;
	
	@Test
	@DisplayName("Get auth token - admin")
	void getAuthToken_admin() throws Exception {
		this.mockMvc.perform(post("/oauth/token")
				.with(httpBasic(appProperites.getClientId(), appProperites.getClientSecret()))
				.param("username", appProperites.getAdminName())
				.param("password", appProperites.getAdminPassword())
				.param("grant_type", "password"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("access_token").exists());
	}

	@Test
	@DisplayName("Create new and Get auth token")
	void getAuthToken() throws Exception {
		String username = "sungwoo@email.com";
		String password = "pass";
		Account account = Account.builder()
				.email(username)
				.password(password)
				.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
				.build();
		accountService.saveAccount(account);
		
		this.mockMvc.perform(post("/oauth/token")
				.with(httpBasic(appProperites.getClientId(), appProperites.getClientSecret()))
				.param("username", username)
				.param("password", password)
				.param("grant_type", "password"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("access_token").exists());
	}

}
