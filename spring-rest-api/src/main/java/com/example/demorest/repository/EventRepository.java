package com.example.demorest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demorest.domain.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
