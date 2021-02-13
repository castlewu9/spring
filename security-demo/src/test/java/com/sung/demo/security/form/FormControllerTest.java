package com.sung.demo.security.form;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class FormControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  @WithAnonymousUser
  void testIndex_Anonymous() throws Exception {
    mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "user", roles = "USER")
  void testIndex_User() throws Exception {
    mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "user", roles = "USER")
  void testAdmin_User() throws Exception {
    mockMvc.perform(get("/admin")).andDo(print()).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username = "user", roles = "ADMIN")
  void testAdmin_Admin() throws Exception {
    mockMvc.perform(get("/admin")).andDo(print()).andExpect(status().isOk());
  }

}
