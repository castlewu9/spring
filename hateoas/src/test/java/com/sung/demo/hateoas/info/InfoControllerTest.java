package com.sung.demo.hateoas.info;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import com.sung.demo.hateoas.common.BaseControllerTest;


class InfoControllerTest extends BaseControllerTest {

  @Test
  @DisplayName("GET /info")
  void getInfo() throws Exception {
    mockMvc.perform(get("/info")).andExpect(status().isOk()).andDo(print())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
        .andExpect(jsonPath("title").value(properties.getTitle()))
        .andExpect(jsonPath("version").value(properties.getVersion()));
  }

}
