package com.sung.demo.security.user;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import com.sung.demo.security.common.BaseFormTest;

class SignUpControllerTest extends BaseFormTest {

  @Test
  void testSignForm() throws Exception {
    mockMvc.perform(get("/signup")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("_csrf")));
  }

  @Test
  void testProcessSingup() throws Exception {
    mockMvc.perform(
        post("/signup").param("username", "test-man").param("password", "hello world").with(csrf()))
        .andDo(print()).andExpect(status().is3xxRedirection());
  }

}
