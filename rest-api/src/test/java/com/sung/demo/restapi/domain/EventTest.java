package com.sung.demo.restapi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class EventTest {

  @Test
  void builder() {
    Event event = Event.builder().name("Event-name").description("Event-description").build();

    assertThat(event).isNotNull();
  }

  @Test
  void bean() {
    Event event = new Event();
    event.setName("Event-name");
    event.setDescription("Event-description");

    assertThat(event.getName()).isEqualTo("Event-name");
    assertThat(event.getDescription()).isEqualTo("Event-description");
  }

  @ParameterizedTest
  @DisplayName("check the free with prices")
  @CsvSource({"0, 0, true", "100, 0, false", "0, 100, false", "100, 200, false"})
  void testFree(int base, int max, boolean free) {
    Event event = Event.builder().basePrice(base).maxPrice(max).build();
    event.update();
    assertThat(event.isFree()).isEqualTo(free);
  }

  @ParameterizedTest
  @DisplayName("check the offline with location")
  @MethodSource("isOffline")
  void testOffline(String location, boolean offline) {
    Event event = Event.builder().location(location).build();
    event.update();
    assertThat(event.isOffline()).isEqualTo(offline);
  }

  private static Stream<Arguments> isOffline() {
    return Stream.of(Arguments.of("Seoul", true), Arguments.of("", false),
        Arguments.of("    ", false), Arguments.of(null, false));
  }
}
