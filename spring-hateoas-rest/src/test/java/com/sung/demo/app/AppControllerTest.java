package com.sung.demo.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;

import com.sung.demo.common.BaseControllerTest;

class AppControllerTest extends BaseControllerTest {

	@Test
	@DisplayName("Get /app/info")
	void appInfo() throws Exception {
		mockMvc.perform(get("/app/info"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
			.andExpect(jsonPath("version").value(appProperties.getVersion()))
			.andExpect(jsonPath("title").value(appProperties.getTitle()))
			.andExpect(jsonPath("_links.self").exists())
			.andExpect(jsonPath("_links.self.href", Matchers.containsString("/app/info")));
	}

}
