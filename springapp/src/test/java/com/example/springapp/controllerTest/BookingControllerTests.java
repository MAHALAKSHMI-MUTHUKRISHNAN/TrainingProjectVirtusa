package com.example.springapp.controllerTest;

import com.example.springapp.AbstractTest;
import com.example.springapp.dao.EventsDao;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookingControllerTests  extends AbstractTest {

    @Autowired
    private EventsDao eventsDao;

    @Autowired
    private UserDao userDao;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    //get bookings while no bookings
    @Test
    public void test00() throws Exception {
        String uri = "/getAllBookings";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Bookings Available"));
    }

    //add booking with user not available
    @Test
    public void test01() throws Exception {
        String uri = "/booking";
        Booking book1 = new Booking(1,1,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        String inputJson = super.mapToJson(book1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("No users found"));
    }

    //add booking with event not available
    @Test
    public void test02() throws Exception {
        String uri = "/booking";
        Booking book1 = new Booking(1,1,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        Users users = new Users();
        users.setId(1);
        users.setUsername("maha");
        userDao.save(users);
        String inputJson = super.mapToJson(book1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("No events found"));
    }

    //get user booking if no booking for user
    @Test
    @WithMockUser(username = "maha",password = "test1234")
    public void test03() throws Exception {
        String uri = "/getUserBookings";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("No Bookings Available for this user"));
    }

    //edit when no booking available
    @Test
    public void test04() throws Exception {
        String uri = "/editBooking";
        Booking book1 = new Booking(1,1,1,"maha","8678999537", LocalDate.of(2022,8,11),"Guindy","http://fhgshf","19.08",250,"pending",new Payment());
        String inputJson = super.mapToJson(book1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Couldn't edit : Invalid Boooking id"));
    }

    //confirm status edit if booking not available
    @Test
    public void test05() throws Exception {
        String uri = "/confirmStatus";
        Booking book1 = new Booking(1,1,1,"maha","8678999537", LocalDate.of(2022,8,11),"Guindy","http://fhgshf","19.08",250,"Confirmed",new Payment());
        String inputJson = super.mapToJson(book1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Invalid Boooking id"));
    }

    //add booking
    @Test
    public void test06() throws Exception {
        String uri = "/booking";
        Booking book1 = new Booking(1,1,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        Events events = new Events();
        events.setId(1);
        eventsDao.save(events);
        Users users = new Users();
        users.setId(1);
        users.setUsername("maha");
        userDao.save(users);
        String inputJson = super.mapToJson(book1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Booking done successfully",content);
    }

    //edit booking
    @Test
    public void test07() throws Exception {
        String uri = "/editBooking";
        Booking book1 = new Booking(1,1,1,"maha","8678999537", LocalDate.of(2022,8,11),"Guindy","http://fhgshf","19.08",250,"pending",new Payment());
        String inputJson = super.mapToJson(book1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Booking updated successfully",content);
    }

    //confirm status update
    @Test
    public void test08() throws Exception {
        String uri = "/confirmStatus";
        Booking book1 = new Booking(1,1,1,"maha","8678999537", LocalDate.of(2022,8,11),"Guindy","http://fhgshf","19.08",250,"Confirmed",new Payment());
        String inputJson = super.mapToJson(book1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Confirm Status updated",content);
    }

    //search
    @Test
    public void test09() throws Exception {
        String uri = "/search?query=maha";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Booking[] bookings = super.mapFromJson(content,Booking[].class);
        assertEquals(1,bookings.length);
    }

    //search if search element not found
    @Test
    public void test10() throws Exception {
        String uri = "/search?query=abi";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    //get all bookings
    @Test
    public void test11() throws Exception {
        String uri = "/getAllBookings";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Booking[] events = super.mapFromJson(content,Booking[].class);
        assertTrue(events.length > 0);
    }

    //get user bookings
    @Test
    @WithMockUser(username = "maha",password = "test1234")
    public void test12() throws Exception {
        String uri = "/getUserBookings";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Booking[] bookings = super.mapFromJson(content,Booking[].class);
        assertEquals(1,bookings.length);
    }

    //delete booking
    @Test
    public void test13() throws Exception {
        String uri = "/deleteBooking/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Booking deleted successfully",content );
    }

    //adding booking
    @Test
    public void test14() throws Exception {
        String uri = "/booking";
        Booking book1 = new Booking(1,1,1,"maha","8678999537", LocalDate.of(2022,8,11),"London","http://fhgshf","19.08",250,"pending",new Payment());
        Events events = new Events();
        events.setId(1);
        eventsDao.save(events);
        Users users = new Users();
        users.setId(1);
        users.setUsername("maha");
        userDao.save(users);
        String inputJson = super.mapToJson(book1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Booking done successfully",content);
    }

    //adding with same date - error date already booked
    @Test
    public void test15() throws Exception {
        String uri = "/booking";
        Booking book1 = new Booking(1,1,1,"maha","8678999537", LocalDate.of(2022,8,11),"London","http://fhgshf","19.08",250,"pending",new Payment());
        Events events = new Events();
        events.setId(1);
        eventsDao.save(events);
        Users users = new Users();
        users.setId(1);
        users.setUsername("maha");
        userDao.save(users);
        String inputJson = super.mapToJson(book1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Date"+book1.getEventDate()+" already Booked !"));
    }

    //book with null values
    @Test
    public void test16() throws Exception {
        String uri = "/booking";
        Booking book1 = new Booking();
        Events events = new Events();
        events.setId(1);
        eventsDao.save(events);
        Users users = new Users();
        users.setId(1);
        users.setUsername("maha");
        userDao.save(users);
        String inputJson = super.mapToJson(book1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Null value"));
    }

    //delete if booking not available
    @Test
    public void test17() throws Exception {
        String uri = "/deleteBooking/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Couldn't delete: Invalid Boooking id"));
    }



}
