package com.example.springapp.controllerTest;

import com.example.springapp.AbstractTest;
import com.example.springapp.entity.Booking;
import com.example.springapp.entity.Events;
import com.example.springapp.entity.Payment;
import com.example.springapp.entity.Users;
import com.example.springapp.model.JwtRequest;
import com.example.springapp.model.JwtResponse;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTests extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void test01() throws Exception {
        String uri = "/register";
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(1,"user","maha","mahalakshmi","8768766788","maha@gmail.com","test1234",bookings);
        String inputJson = super.mapToJson(users);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Welcome "+users.getName()+", you've registered successfully",content);
    }

    @Test
    public void test02() throws Exception{
        String uri = "/authenticate";
        JwtRequest request = new JwtRequest("mahalakshmi","test1234");
        String inputJson = super.mapToJson(request);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andExpect(status().isOk()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void test03() throws Exception{
        String uri = "/editUser";
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(1,"user","maha","mahalakshmi","8768766799","maha@gmail.com","test1234",bookings);
        String inputJson = super.mapToJson(users);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("User updated successfully",content);
    }

    @Test
    public void test04() throws Exception {
        String uri = "/getuser";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Users[] users = super.mapFromJson(content,Users[].class);
        assertTrue(users.length > 0);
    }

    @Test
    public void test05() throws Exception {
        String uri = "/deleteUser/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("User deleted successfully",content );
    }
}
