package com.sung.demo.restapi.domain.validator;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import com.sung.demo.restapi.domain.dto.EventDto;

@Component
public class EventValidator {

  public void validate(EventDto event, Errors errors) {
    if (event.getBasePrice() > event.getMaxPrice() && event.getMaxPrice() != 0) {
      errors.reject("Invalid Value", "Matching prices are invalid");
    }

    LocalDateTime endDate = event.getEndEventDateTime();
    if (endDate.isBefore(event.getBeginEnrollmentDateTime())
        || endDate.isBefore(event.getBeginEventDateTime())) {
      errors.rejectValue("endEventDateTime", "Invalid Value", "EndEventDateTime is invalid");
    }
  }
}
