package com.sung.demo.restapi.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import com.sung.demo.restapi.domain.Account;
import com.sung.demo.restapi.domain.AccountRole;
import com.sung.demo.restapi.domain.Event;
import com.sung.demo.restapi.domain.EventStatus;
import com.sung.demo.restapi.domain.dto.EventDto;
import com.sung.demo.restapi.repository.AccountRepository;
import com.sung.demo.restapi.repository.EventRepository;
import com.sung.demo.restapi.service.AccountService;

class EventControllerTest extends BaseControllerTest {

  @Autowired
  EventRepository eventRepository;

  @Autowired
  AccountService accountService;

  @Autowired
  AccountRepository accountRepository;

  @BeforeEach
  public void setup() {
    eventRepository.deleteAll();
    accountRepository.deleteAll();
  }

  String getBearerToken() throws Exception {
    Account account = Account.builder().email(appProperites.getAdminName())
        .password(appProperites.getAdminPassword())
        .roles(Set.of(AccountRole.ADMIN, AccountRole.USER)).build();
    accountService.saveAccount(account);

    var body = mockMvc
        .perform(post("/oauth/token")
            .with(httpBasic(appProperites.getClientId(), appProperites.getClientSecret()))
            .param("username", appProperites.getAdminName())
            .param("password", appProperites.getAdminPassword()).param("grant_type", "password"))
        .andReturn().getResponse().getContentAsString();

    Jackson2JsonParser parser = new Jackson2JsonParser();
    return "Bearer " + parser.parseMap(body).get("access_token").toString();
  }

  @Test
  @DisplayName("POST /api/events: created")
  void postEvent() throws Exception {
    EventDto event = EventDto.builder().name("Spring-Rest-Event").description("Event-Description")
        .beginEnrollmentDateTime(LocalDateTime.of(2020, 5, 25, 11, 59))
        .closeEnrollmentDateTime(LocalDateTime.of(2020, 5, 29, 11, 59))
        .beginEventDateTime(LocalDateTime.of(2020, 6, 1, 8, 0))
        .endEventDateTime(LocalDateTime.of(2020, 6, 5, 18, 0)).basePrice(100).maxPrice(200)
        .limitOfEnrollment(200).location("Seoul").build();

    mockMvc
        .perform(post("/api/events").header(HttpHeaders.AUTHORIZATION, this.getBearerToken())
            .contentType(MediaType.APPLICATION_JSON).accept(MediaTypes.HAL_JSON)
            .content(objectMapper.writeValueAsString(event)))
        .andDo(print()).andExpect(status().isCreated())
        .andExpect(header().exists(HttpHeaders.LOCATION))
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
        .andExpect(jsonPath("id").exists()).andExpect(jsonPath("offline").value(true))
        .andExpect(jsonPath("free").value(false))
        .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
        .andExpect(jsonPath("_links.self").exists())
        .andExpect(jsonPath("_links.update-event").exists())
        .andExpect(jsonPath("_links.query-events").exists())
        .andDo(document("create-event",
            links(linkWithRel("self").description("link to self"),
                linkWithRel("query-events").description("link to query events"),
                linkWithRel("update-event").description("link to update event"),
                linkWithRel("profile").description("link to the profile")),
            requestHeaders(headerWithName(HttpHeaders.ACCEPT).description("header: accept"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("header: content type")),
            requestFields(fieldWithPath("name").description("Event name"),
                fieldWithPath("description").description("Event description"),
                fieldWithPath("beginEnrollmentDateTime")
                    .description("Begin date time of the enrollment"),
                fieldWithPath("closeEnrollmentDateTime")
                    .description("Close date time of the enrollment"),
                fieldWithPath("beginEventDateTime").description("Event start date time"),
                fieldWithPath("endEventDateTime").description("Event end date time"),
                fieldWithPath("location").description("Location of the event will held"),
                fieldWithPath("basePrice").description("Base price of the enrollment"),
                fieldWithPath("maxPrice").description("Max price of the enrollment"),
                fieldWithPath("limitOfEnrollment")
                    .description("The number of people for the event")),
            responseHeaders(headerWithName(HttpHeaders.LOCATION).description("Location header"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type hal+json")),
            relaxedResponseFields(fieldWithPath("id").description("Event Id"),
                fieldWithPath("name").description("Event name"),
                fieldWithPath("description").description("Event description"),
                fieldWithPath("beginEnrollmentDateTime")
                    .description("Begin date time of the enrollment"),
                fieldWithPath("closeEnrollmentDateTime")
                    .description("Close date time of the enrollment"),
                fieldWithPath("beginEventDateTime").description("Event start date time"),
                fieldWithPath("endEventDateTime").description("Event end date time"),
                fieldWithPath("location").description("Location of the event will held"),
                fieldWithPath("basePrice").description("Base price of the enrollment"),
                fieldWithPath("maxPrice").description("Max price of the enrollment"),
                fieldWithPath("limitOfEnrollment")
                    .description("The number of people for the event"),
                fieldWithPath("free").description("It tells if the event is free or not"),
                fieldWithPath("offline").description("It tells the event held online or offline"),
                fieldWithPath("eventStatus").description("Event status"),
                fieldWithPath("manager").description("Manager"),
                fieldWithPath("_links.self.href").description("HAL: self"),
                fieldWithPath("_links.query-events.href").description("HAL: query-events"),
                fieldWithPath("_links.update-event.href").description("HAL: update-event"),
                fieldWithPath("_links.profile.href").description("HAL: profile"))));
  }

  @Test
  @DisplayName("POST /api/events: bad request due to null params")
  void postEvent_Bad_Request() throws Exception {
    EventDto event = EventDto.builder().build();

    mockMvc.perform(post("/api/events").header(HttpHeaders.AUTHORIZATION, this.getBearerToken())
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(event)))
        .andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("POST /api/events: bad request due to invalid params")
  void postEvent_Bad_Request_Invalid_Params() throws Exception {
    EventDto event = EventDto.builder().name("Spring-Rest-Event").description("Event-Description")
        .beginEnrollmentDateTime(LocalDateTime.of(2020, 5, 25, 11, 59))
        .closeEnrollmentDateTime(LocalDateTime.of(2020, 5, 20, 11, 59))
        .beginEventDateTime(LocalDateTime.of(2020, 6, 1, 8, 0))
        .endEventDateTime(LocalDateTime.of(2020, 5, 5, 18, 0)).basePrice(1000).maxPrice(200)
        .limitOfEnrollment(200).location("Seoul").build();

    mockMvc
        .perform(post("/api/events").header(HttpHeaders.AUTHORIZATION, this.getBearerToken())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(event)))
        .andDo(print()).andExpect(status().isBadRequest())
        .andExpect(jsonPath("content[0].objectName").exists())
        .andExpect(jsonPath("content[0].field").exists())
        .andExpect(jsonPath("content[0].defaultMessage").exists())
        .andExpect(jsonPath("content[0].code").exists())
        .andExpect(jsonPath("content[0].rejectedValue").exists())
        .andExpect(jsonPath("_links.index").exists());
  }

  @Test
  @DisplayName("GET /api/events")
  public void queryEvents() throws Exception {
    IntStream.range(0, 30).forEach(this::createTestEvent);

    mockMvc
        .perform(
            get("/api/events").param("page", "1").param("size", "10").param("sort", "name,DESC"))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
        .andExpect(jsonPath("page").exists()).andExpect(jsonPath("_links.self").exists())
        .andExpect(jsonPath("_links.profile").exists()).andDo(document("query-events"));
  }

  @Test
  @DisplayName("GET /api/events with Authentication")
  public void queryEvents_withAutentication() throws Exception {
    IntStream.range(0, 30).forEach(this::createTestEvent);

    mockMvc
        .perform(get("/api/events").header(HttpHeaders.AUTHORIZATION, this.getBearerToken())
            .param("page", "1").param("size", "10").param("sort", "name,DESC"))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
        .andExpect(jsonPath("page").exists()).andExpect(jsonPath("_links.self").exists())
        .andExpect(jsonPath("_links.profile").exists())
        .andExpect(jsonPath("_links.create-event").exists()).andDo(document("query-events"));
  }

  private Event createTestEvent(int index) {
    Event event = Event.builder().name("event" + index).description("test event: " + index)
        .beginEnrollmentDateTime(LocalDateTime.of(2020, 5, 25, 11, 59))
        .closeEnrollmentDateTime(LocalDateTime.of(2020, 5, 29, 11, 59))
        .beginEventDateTime(LocalDateTime.of(2020, 6, 1, 8, 0))
        .endEventDateTime(LocalDateTime.of(2020, 6, 5, 18, 0)).basePrice(100).maxPrice(200)
        .limitOfEnrollment(200).location("Seoul").free(false).offline(true).build();

    return eventRepository.save(event);
  }

  private Event createTestEvent2(int index) {
    Event event = Event.builder().name("event" + index).description("test event: " + index)
        .beginEnrollmentDateTime(LocalDateTime.of(2020, 5, 25, 11, 59))
        .closeEnrollmentDateTime(LocalDateTime.of(2020, 5, 29, 11, 59))
        .beginEventDateTime(LocalDateTime.of(2020, 6, 1, 8, 0))
        .endEventDateTime(LocalDateTime.of(2020, 6, 5, 18, 0)).basePrice(100).maxPrice(200)
        .limitOfEnrollment(200).location("Seoul").free(false).offline(true).build();

    Account account = Account.builder().email(appProperites.getAdminName())
        .password(appProperites.getAdminPassword())
        .roles(Set.of(AccountRole.ADMIN, AccountRole.USER)).build();
    accountService.saveAccount(account);

    event.setManager(account);

    return eventRepository.save(event);
  }

  @Test
  @DisplayName("GET /api/events/{id}")
  public void getEvent() throws Exception {
    Event event = this.createTestEvent2(100);
    this.mockMvc.perform(get("/api/events/{id}", event.getId())).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("id").exists())
        .andExpect(jsonPath("name").exists()).andExpect(jsonPath("_links.self").exists())
        .andExpect(jsonPath("_links.profile").exists()).andDo(document("get-an-event"));
  }

  @Test
  @DisplayName("GET /api/events/{id} if the id does not exist")
  public void getEvent_without_Id() throws Exception {
    this.mockMvc.perform(get("/api/events/12345")).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("PUT /api/events/{id}")
  public void putEvent() throws Exception {
    EventDto event = EventDto.builder().name("Spring-Rest-Event").description("Event-Description")
        .beginEnrollmentDateTime(LocalDateTime.of(2020, 5, 25, 11, 59))
        .closeEnrollmentDateTime(LocalDateTime.of(2020, 5, 29, 11, 59))
        .beginEventDateTime(LocalDateTime.of(2020, 6, 1, 8, 0))
        .endEventDateTime(LocalDateTime.of(2020, 6, 5, 18, 0)).basePrice(100).maxPrice(200)
        .limitOfEnrollment(200).location("Seoul").build();

    String token = this.getBearerToken();

    var body = this.mockMvc
        .perform(post("/api/events").header(HttpHeaders.AUTHORIZATION, token)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaTypes.HAL_JSON)
            .content(objectMapper.writeValueAsString(event)))
        .andDo(print()).andReturn().getResponse().getContentAsString();

    Jackson2JsonParser parser = new Jackson2JsonParser();
    String eventId = parser.parseMap(body).get("id").toString();

    EventDto eventDto = this.modelMapper.map(this.createTestEvent(100), EventDto.class);

    String name = "PUT: updated event";
    eventDto.setName(name);

    this.mockMvc
        .perform(put("/api/events/{id}", eventId).header(HttpHeaders.AUTHORIZATION, token)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(eventDto)))
        .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("name").value(name))
        .andExpect(jsonPath("_links.self").exists()).andDo(document("update-event"));
  }

  @Test
  @DisplayName("PUT /api/events/{id} with Unauthorized")
  public void putEvent_Unauth() throws Exception {
    Event event = this.createTestEvent(100);
    EventDto eventDto = this.modelMapper.map(event, EventDto.class);

    String name = "PUT: updated event";
    eventDto.setName(name);

    this.mockMvc
        .perform(put("/api/events/{id}", event.getId())
            .header(HttpHeaders.AUTHORIZATION, this.getBearerToken())
            .contentType(MediaType.APPLICATION_JSON).accept(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(eventDto)))
        .andDo(print()).andExpect(status().isUnauthorized());
  }

  @Test
  @DisplayName("PUT /api/events/{id} with empty body")
  public void putEvent_with_emptyBody() throws Exception {
    Event event = this.createTestEvent(100);
    EventDto eventDto = new EventDto();

    this.mockMvc
        .perform(put("/api/events/{id}", event.getId())
            .header(HttpHeaders.AUTHORIZATION, this.getBearerToken())
            .contentType(MediaType.APPLICATION_JSON).accept(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(eventDto)))
        .andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("PUT /api/events/{id} with invalid data")
  public void putEvent_with_invalidData() throws Exception {
    Event event = this.createTestEvent(100);
    EventDto eventDto = this.modelMapper.map(event, EventDto.class);
    eventDto.setBasePrice(2000);
    eventDto.setMaxPrice(1000);

    this.mockMvc
        .perform(put("/api/events/{id}", event.getId())
            .header(HttpHeaders.AUTHORIZATION, this.getBearerToken())
            .contentType(MediaType.APPLICATION_JSON).accept(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(eventDto)))
        .andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("PUT /api/events/{id} with invalid id")
  public void putEvent_with_invalidId() throws Exception {
    Event event = this.createTestEvent(100);
    EventDto eventDto = this.modelMapper.map(event, EventDto.class);

    this.mockMvc
        .perform(put("/api/events/12345").header(HttpHeaders.AUTHORIZATION, this.getBearerToken())
            .contentType(MediaType.APPLICATION_JSON).accept(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(eventDto)))
        .andDo(print()).andExpect(status().isNotFound());
  }
}
