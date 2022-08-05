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

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Autowired
    private EventsDao eventsDao;

    @Autowired
    private UserDao userDao;

    @Test
    public void test01() throws Exception {
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

    @Test
    public void test02() throws Exception {
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

    @Test
    public void test03() throws Exception {
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

    @Test
    public void test04() throws Exception {
        String uri = "/search?query=maha";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Booking[] bookings = super.mapFromJson(content,Booking[].class);
        assertEquals(1,bookings.length);
    }

    @Test
    public void test05() throws Exception {
        String uri = "/getAllBookings";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Booking[] events = super.mapFromJson(content,Booking[].class);
        assertTrue(events.length > 0);
    }

    @Test
    @WithMockUser(username = "maha",password = "test1234")
    public void test06() throws Exception {
        String uri = "/getUserBookings";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Booking[] bookings = super.mapFromJson(content,Booking[].class);
        assertEquals(1,bookings.length);
    }

    @Test
    public void test07() throws Exception {
        String uri = "/deleteBooking/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Booking deleted successfully",content );
    }

}
