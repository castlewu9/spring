package com.sung.demo.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sung.demo.restapi.domain.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
