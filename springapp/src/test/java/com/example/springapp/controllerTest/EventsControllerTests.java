package com.example.springapp.controllerTest;
import com.example.springapp.AbstractTest;
import com.example.springapp.dao.UserDao;
import com.example.springapp.entity.Booking;
import com.example.springapp.entity.Events;
import com.example.springapp.entity.Payment;
import com.example.springapp.entity.Users;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventsControllerTests extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Autowired
    private UserDao userDao;

    //view all events with no events found
    @Test
    public void test() throws Exception {
        String uri = "/viewAllEvents";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("No Events Available"));
    }

    //view one event booking with no events
    @Test
    public void test01() throws Exception {
        String uri = "/viewEventBooking/2";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Event Not found"));
    }

    //edit events if event doesn't exists
    @Test
    public void test02() throws Exception {
        String uri = "/updateEvent";
        List<Booking> allBookings = new ArrayList<>();
        Events events = new Events(1, "Birthday", "h", 20000, "details", allBookings);
        String inputJson = super.mapToJson(events);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Couldn't Edit :Invalid Event id"));
    }

    //delete event if event doesn't exists
    @Test
    public void test04() throws Exception {
        String uri = "/deleteEvent/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Couldn't delete: Invalid Event id") );
    }

    //to add events with null values
    @Test
    @WithMockUser(username = "admin",password = "test1234")
    public void test05() throws Exception {
        String uri = "/addEvents";
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        List<Booking> allBookings = new ArrayList<>();
        allBookings.add(book1);
        Users users = new Users();
        users.setUsername("admin");
        users.setId(1);
        userDao.save(users);
        Events events = new Events();
        String inputJson = super.mapToJson(events);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Null value not accepted"));
    }

    //to add events with empty booking
    @Test
    @WithMockUser(username = "admin",password = "test1234")
    public void test06() throws Exception {
        String uri = "/addEvents";
        List<Booking> allBookings = new ArrayList<>();
        Users users = new Users();
        users.setUsername("admin");
        users.setId(1);
        userDao.save(users);
        Events events = new Events(1, "Birthday", "h", 34000, "details", allBookings);
        String inputJson = super.mapToJson(events);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Event added successfully",content);
    }

    //view events by id if empty booking
    @Test
    public void test07() throws Exception {
        String uri = "/viewEventBooking/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Bookings not available for the Event"));
    }

    //to add events with same event name
    @Test
    @WithMockUser(username = "admin",password = "test1234")
    public void test08() throws Exception {
        String uri = "/addEvents";
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        List<Booking> allBookings = new ArrayList<>();
        allBookings.add(book1);
        Users users = new Users();
        users.setUsername("admin");
        users.setId(1);
        userDao.save(users);
        Events events = new Events(2, "Birthday", "h", 34000, "details", allBookings);
        String inputJson = super.mapToJson(events);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Event with name "+events.getName()+" already available"));
    }

    //edit events with adding booking
    @Test
    public void test09() throws Exception {
        String uri = "/updateEvent";
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        List<Booking> allBookings = new ArrayList<>();
        allBookings.add(book1);
        Events events = new Events(1, "Birthday", "h", 20000, "details", allBookings);
        String inputJson = super.mapToJson(events);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Event updated successfully",content);
    }

    //view events by id
    @Test
    public void test10() throws Exception {
        String uri = "/viewEventBooking/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Booking[] bookings = super.mapFromJson(content,Booking[].class);
        assertEquals(1,bookings.length);
    }



    //view all events
    @Test
    public void test11() throws Exception {
        String uri = "/viewAllEvents";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Events[] events = super.mapFromJson(content,Events[].class);
        assertTrue(events.length > 0);
    }

    //delete event
    @Test
    public void test12() throws Exception {
        String uri = "/deleteEvent/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Event deleted successfully",content );
    }

    //only add admins can add exception
    @Test
    @WithMockUser(username = "user",password = "test1234")
    public void test13() throws Exception {
        String uri = "/addEvents";
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        List<Booking> allBookings = new ArrayList<>();
        allBookings.add(book1);
        Users users = new Users();
        users.setUsername("admin");
        users.setId(1);
        userDao.save(users);
        Events events = new Events(1, "Anniversary", "h", 34000, "details", allBookings);
        String inputJson = super.mapToJson(events);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(401, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Only Admins can add Events"));
    }


}
