package com.sung.demo.hateoas.user;

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
import com.sung.demo.hateoas.common.BaseControllerTest;

class UserControllerTest extends BaseControllerTest {

  @Autowired
  UserRepository userRepo;

  @BeforeEach
  void setUp() {
    userRepo.deleteAll();
  }

  @Test
  @DisplayName("GET /user/{id}")
  void getUserById() throws Exception {
    User user =
        User.builder().id("1").fname("Sung").lname("Park").role("admin").status("enabled").build();
    userRepo.save(user);

    mockMvc.perform(get("/users/" + user.getId())).andExpect(status().isOk()).andDo(print())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
        .andExpect(jsonPath("id").value(user.getId()))
        .andExpect(jsonPath("fname").value(user.getFname()))
        .andExpect(jsonPath("lname").value(user.getLname()))
        .andExpect(jsonPath("status").value(user.getStatus()))
        .andExpect(jsonPath("role").value(user.getRole())).andExpect(jsonPath("admin").value(true))
        .andExpect(jsonPath("_links.self").exists())
        .andExpect(jsonPath("_links.self.href", Matchers.containsString("/users/" + user.getId())));
  }

}
