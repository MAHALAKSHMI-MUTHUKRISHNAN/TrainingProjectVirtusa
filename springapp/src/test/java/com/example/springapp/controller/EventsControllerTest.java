package com.example.springapp.controller;

import com.example.springapp.config.JwtAuthenticationEntryPoint;
import com.example.springapp.config.JwtUtility;
import com.example.springapp.config.JwtFilter;
import com.example.springapp.dao.EventsDao;
import com.example.springapp.entity.Booking;
import com.example.springapp.entity.Events;
import com.example.springapp.entity.Payment;
import com.example.springapp.exception.ResourceNotFoundException;
import com.example.springapp.service.EventsServices;
import com.example.springapp.service.EventsServicesImpl;
import com.example.springapp.service.JwtUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EventsController.class)
public class EventsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventsServices eventsServices;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private EventsDao eventsDao;

    @MockBean
    private JwtUtility jwtUtility;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @InjectMocks
    private EventsController eventsController;

    @Test
    public void testViewAllEvents() throws Exception {
        Payment pay1 = new Payment(1,false,false,2500);
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",pay1);
        List<Booking> allbookings = new ArrayList<>();
        allbookings.add(book1);
        Events events1 = new Events(1, "Birthday", "http://birthday", 25000, "Best quality", allbookings);
        List<Events> allEvents = new ArrayList<>();
        allEvents.add(events1);
        given(eventsServices.viewAllEvents()).willReturn(allEvents);
        mvc.perform(get("/viewAllEvents").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testviewEventBooking() throws Exception {
        Payment pay1 = new Payment(1,false,false,2500);
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",pay1);
        List<Booking> allbookings = new ArrayList<>();
        allbookings.add(book1);
        Events events1 = new Events(1, "Birthday", "http://birthday", 25000, "Best quality", allbookings);
        List<Events> allEvents = new ArrayList<>();
        allEvents.add(events1);
        given(eventsServices.viewEventBookings(events1.getId())).willReturn(allbookings);
        mvc.perform(get("/viewEventBooking/"+events1.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testviewEventBookingIfDontExists() throws Exception {
        Payment pay1 = new Payment(1,false,false,2500);
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",pay1);
        List<Booking> allbookings = new ArrayList<>();
        allbookings.add(book1);
        Events events1 = new Events(1, "Birthday", "http://birthday", 25000, "Best quality", allbookings);
        List<Events> allEvents = new ArrayList<>();
        allEvents.add(events1);
        //given(eventsServices.viewEventBookings(events1.getId())).willThrow(ResourceNotFoundException.class);
        mvc.perform(get("/viewEventBooking/"+events1.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
