package com.example.springapp.controller;
import com.example.springapp.config.SecurityUtils;
import com.example.springapp.dto.EventsDto;
import com.example.springapp.entity.Booking;
import com.example.springapp.entity.Events;
import com.example.springapp.exception.UnAuthorizedException;
import com.example.springapp.service.EventsServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@SecurityRequirement(name = "event-docs")
public class EventsController {
    Logger logger = LoggerFactory.getLogger(EventsController.class);
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventsServices eventsServices;

    @PostMapping("/addEvents")
    public ResponseEntity<Object> addEvents(@RequestBody EventsDto eventsDto) {
        logger.info("addEvent method accessed");
            Events eventRequest = modelMapper.map(eventsDto,Events.class);
        Optional<String> username = SecurityUtils.getCurrentUserLogin();
        if(username.isEmpty() || !(username.get().equals("admin"))) {
            throw new UnAuthorizedException("Only Admins can add Events");
        }
            return  this.eventsServices.addEvents(eventRequest);
    }


    @GetMapping("/viewAllEvents")
    public List<Events> viewAllEvents(){
        return eventsServices.viewAllEvents();
    }

    @GetMapping("/viewEventBooking/{id}")
    public List<Booking> viewEventBooking(@PathVariable String id){
        return eventsServices.viewEventBookings(Long.parseLong(id));

    }

    @DeleteMapping("/deleteEvent/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable String id){
        return  this.eventsServices.deleteEvent(Long.parseLong(id));

    }

    @PutMapping("/updateEvent")
    public ResponseEntity<Object> updateEvent(@RequestBody EventsDto eventsDto){
        Events eventRequest = modelMapper.map(eventsDto,Events.class);
        return this.eventsServices.updateEvent(eventRequest);
    }
}
