package com.example.springapp.service;

import com.example.springapp.dao.EventsDao;
import com.example.springapp.entity.Booking;
import com.example.springapp.entity.Events;
import com.example.springapp.entity.Payment;
import com.example.springapp.exception.ResourceNotFoundException;
import com.example.springapp.exception.ValueExistsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventsServiceImplTest {

    @Mock
    private EventsDao eventsDao;

    @InjectMocks
    private EventsServicesImpl eventsServices;

    @Test
    public void testViewAllEvents(){
        List<Events> events = new ArrayList<>();
        events.add(new Events());
        events.add(new Events());
        given(eventsDao.findAll()).willReturn(events);
        List<Events> expected = eventsServices.viewAllEvents();
        assertEquals(expected,events);
        assertEquals(2,eventsServices.viewAllEvents().size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testViewAllEventsIfNoEvents(){
        List<Events> events = new ArrayList<>();
        given(eventsDao.findAll()).willReturn(events);
        eventsServices.viewAllEvents();
    }

    @Test
    public void testAddEvents() {
        List<Booking> allBookings = new ArrayList<>();
        Events events = new Events(1, "Birthday", "h", 34000, "details", allBookings);
        ResponseEntity<Object> created = eventsServices.addEvents(events);
        assertEquals(HttpStatus.OK,created.getStatusCode() );
    }

    @Test(expected = NullPointerException.class)
    public void testAddEventsWithNullValues() {
      Events events = new Events();
      events.setId(1);
      events.setName("Birthday");
      eventsServices.addEvents(events);
    }

    @Test(expected = ValueExistsException.class)
    public void testAddEventsWithSameName() {
        List<Booking> allBookings = new ArrayList<>();
        Events events = new Events(1, "Birthday", "h", 34000, "details", allBookings);
        List<Booking> allBookings1 = new ArrayList<>();
        List<Events> allEvents = new ArrayList<>();
        allEvents.add(events);
        Events events1 = new Events(2, "Birthday", "http://", 20000, "detailswedding", allBookings1);
        given(eventsDao.findAll()).willReturn(allEvents);
        ResponseEntity<Object> created = eventsServices.addEvents(events1);
    }

    @Test
    public void testDeleteEventsIfEventExists(){
        List<Booking> allBookings = new ArrayList<>();
        Events events = new Events(1, "Birthday", "h", 34000, "details", allBookings);
        given(eventsDao.findById(events.getId())).willReturn(java.util.Optional.of(events));
        ResponseEntity<Object> created = eventsServices.deleteEvent(events.getId());
        assertEquals(HttpStatus.OK,created.getStatusCode() );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteEventsIfEventDoesntExists(){
        List<Booking> allBookings = new ArrayList<>();
        Events events = new Events(1, "Birthday", "h", 34000, "details", allBookings);
        given(eventsDao.findById(anyLong())).willReturn(java.util.Optional.ofNullable(null));
        eventsServices.deleteEvent(events.getId());
    }

    @Test
    public void testUpdateEventsIfEventExists(){
        List<Booking> allBookings = new ArrayList<>();
        Events events1 = new Events(1, "Birthday", "h", 34000, "details", allBookings);
        Events events2 = new Events(1, "Birthday", "h", 20000, "details", allBookings);
        given(eventsDao.findById(events2.getId())).willReturn(java.util.Optional.of(events1));
        ResponseEntity<Object> created = eventsServices.updateEvent(events2);
        assertEquals(HttpStatus.OK,created.getStatusCode());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateEventsIfEventDoesntExists(){
        List<Booking> allBookings = new ArrayList<>();
        Events events = new Events(1, "Birthday", "h", 34000, "details", allBookings);
        given(eventsDao.findById(anyLong())).willReturn(java.util.Optional.ofNullable(null));
         eventsServices.updateEvent(events);
    }

    @Test
    public void testViewEventBookings(){
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        List<Booking> allBookings = new ArrayList<>();
        allBookings.add(book1);
        Events events1 = new Events(1, "Birthday", "h", 34000, "details", allBookings);
        given(eventsDao.findById(events1.getId())).willReturn(java.util.Optional.ofNullable(events1));
        List<Booking> created = eventsServices.viewEventBookings(events1.getId());
        assertEquals(created,allBookings);
        assertEquals(1,created.size());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void testViewEventBookingsIfNoBookings(){
        List<Booking> allBookings = new ArrayList<>();
        Events events1 = new Events(1, "Birthday", "h", 34000, "details", allBookings);
        given(eventsDao.findById(events1.getId())).willReturn(java.util.Optional.of(events1));
        eventsServices.viewEventBookings(events1.getId());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void testViewEventBookingsIfEventDoesntExists(){
        List<Booking> allBookings = new ArrayList<>();
        Events events1 = new Events(1, "Birthday", "h", 34000, "details", allBookings);
        given(eventsDao.findById(anyLong())).willReturn(java.util.Optional.ofNullable(null));
        eventsServices.viewEventBookings(events1.getId());
    }


}
