package com.example.springapp.service;

import com.example.springapp.entity.Booking;
import com.example.springapp.entity.Events;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventsServices {

    ResponseEntity<Object> addEvents(Events event);

    List<Events> viewAllEvents();

    List<Booking> viewEventBookings(long id);

    ResponseEntity<Object> deleteEvent(long id);

    ResponseEntity<Object> updateEvent(Events event);
}
