package com.example.springapp.service;


import com.example.springapp.config.SecurityUtils;
import com.example.springapp.dao.BookingDao;
import com.example.springapp.dao.EventsDao;
import com.example.springapp.entity.Booking;
import com.example.springapp.entity.Events;
import com.example.springapp.exception.ValueExistsException;
import com.example.springapp.exception.ResourceNotFoundException;
import com.example.springapp.exception.UnAuthorizedException;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventsServicesImpl implements EventsServices{

    @Autowired
    private EventsDao eventsDao;

    @Autowired
    private BookingDao bookingDao;

    @Override
    public ResponseEntity<Object> addEvents(Events event) {


        List<Events> allEvents =eventsDao.findAll();
        for(Events x:allEvents){
            if(event.getName().equals(x.getName())){
                throw new ValueExistsException("Event with name "+event.getName()+" already available");
            }
        }
        val priceRange = event.getPriceRange();
        if(event.getDetails()==null || event.getName()==null || event.getImageurl()==null || priceRange==0) throw new NullPointerException();

        eventsDao.save(event);
        return new ResponseEntity<>("Event added successfully", HttpStatus.OK);
    }

    @Override
    public List<Events> viewAllEvents() {
        List<Events> allEvents = eventsDao.findAll();
        if(allEvents.isEmpty()) throw new ResourceNotFoundException("No Events Available");
        return allEvents;
    }

    @Override
    public List<Booking> viewEventBookings(long id) {
        if(eventsDao.findById(id).isEmpty()) throw new ResourceNotFoundException("Event Not found");
        List<Booking> bookings = eventsDao.findById(id).get().getBookings();
        if(bookings.isEmpty()) throw new ResourceNotFoundException("Bookings not available for the Event");
        return bookings;
    }

    @Override
    public ResponseEntity<Object> deleteEvent(long id) {
        Optional<Events> deleteEvent = eventsDao.findById(id);
        if(!deleteEvent.isPresent()) throw  new ResourceNotFoundException("Couldn't delete: Invalid Event id"+id);

        this.eventsDao.delete(deleteEvent.get());
        return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateEvent(Events event) {

        if(!(eventsDao.findById(event.getId()).isPresent())) throw new ResourceNotFoundException("Couldn't Edit :Invalid Event id"+event.getId());
        List<Events> allEvents =eventsDao.findAll();
        for(Events x:allEvents){
            if(event.getName().equals(x.getName())){
                throw new ValueExistsException("Event with name "+event.getName()+" already available");
            }
        }
        this.eventsDao.save(event);
        return new ResponseEntity<>("Event updated successfully", HttpStatus.OK);
    }


}
