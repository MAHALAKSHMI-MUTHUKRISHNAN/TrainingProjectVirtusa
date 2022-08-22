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

    //get users if no user present
    @Test
    public void test00() throws Exception {
        String uri = "/getuser";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("No Users Available"));
    }

    //delete user if user doesn't exists
    @Test
    public void test01() throws Exception {
        String uri = "/deleteUser/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Couldn't delete: Invalid user") );
    }

    //edit user if user doesn't exists
    @Test
    public void test02() throws Exception{
        String uri = "/editUser";
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(1,"user","maha","mahalakshmi","8768766799","maha@gmail.com","test1234",bookings);
        String inputJson = super.mapToJson(users);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Couldn't edit : Invalid user"));
    }

    //register user with null values
    @Test
    public void test03() throws Exception {
        String uri = "/register";
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users();
        String inputJson = super.mapToJson(users);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Null value"));
    }

    //register as admin
    @Test
    public void test04() throws Exception {
        String uri = "/register";
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(1,"admin","admin","admin","8768767777","admin@gmail.com","test1234",bookings);
        String inputJson = super.mapToJson(users);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Welcome "+users.getName()+"-"+ users.getRole()+", you've registered successfully",content);
    }

    //register as user
    @Test
    public void test05() throws Exception {
        String uri = "/register";
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(2,"user","maha","mahalakshmi","8768766788","maha@gmail.com","test1234",bookings);
        String inputJson = super.mapToJson(users);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Welcome "+users.getName()+"-"+ users.getRole()+", you've registered successfully",content);
    }

    //register with existing email
    @Test
    public void test06() throws Exception {
        String uri = "/register";
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(3,"user","maha","abarna","7768765588","maha@gmail.com","test1234",bookings);
        String inputJson = super.mapToJson(users);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Email "+users.getEmail()+" already Exists"));
    }

    //registering with existing username
    @Test
    public void test07() throws Exception {
        String uri = "/register";
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(3,"user","maha","mahalakshmi","7768765588","abarna@gmail.com","test1234",bookings);
        String inputJson = super.mapToJson(users);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Username "+users.getUsername()+" already Exists, Choose unique one!"));
    }

    //registering with existing mobile number
    @Test
    public void test08() throws Exception {
        String uri = "/register";
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(3,"user","maha","kathir","8768766788","kathir@gmail.com","test1234",bookings);
        String inputJson = super.mapToJson(users);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Mobile Number "+users.getMobile()+" already Exists"));
    }

    //check login
    @Test
    public void test09() throws Exception{
        String uri = "/authenticate";
        JwtRequest request = new JwtRequest("mahalakshmi","test1234");
        String inputJson = super.mapToJson(request);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andExpect(status().isOk()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    //edit user
    @Test
    public void test10() throws Exception{
        String uri = "/editUser";
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(2,"user","maha","mahalakshmi","8768766799","maha@gmail.com","test1234",bookings);
        String inputJson = super.mapToJson(users);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("User updated successfully",content);
    }

    //get users
    @Test
    public void test11() throws Exception {
        String uri = "/getuser";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Users[] users = super.mapFromJson(content,Users[].class);
        assertEquals(1,users.length);
    }

    //edit user's username
    @Test
    public void test12() throws Exception{
        String uri = "/editUser";
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(2,"user","maha","abarna","7777766799","abarna@gmail.com","test1234",bookings);
        String inputJson = super.mapToJson(users);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Username Cannot be changed"));
    }

    //edit with already existing email
    @Test
    public void test13() throws Exception{
        String uri = "/editUser";
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(2,"user","maha","mahalakshmi","8768766799","admin@gmail.com","test1234",bookings);
        String inputJson = super.mapToJson(users);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("This email has already registered"));
    }

    //edit with already existing mobile number
    @Test
    public void test14() throws Exception{
        String uri = "/editUser";
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(2,"user","maha","mahalakshmi","8768767777","maha@gmail.com","test1234",bookings);
        String inputJson = super.mapToJson(users);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("This mobile number has already registered"));
    }

    //details with registered user
    @Test
    @WithMockUser(username = "mahalakshmi",password = "test1234")
    public void test15() throws Exception {
        String uri = "/mydetails";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Users user = super.mapFromJson(content,Users.class);
        assertEquals("mahalakshmi",user.getUsername());
    }

    //details of unregistered user
    @Test
    public void test16() throws Exception {
        String uri = "/mydetails";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(401, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("User not found"));
    }

    //delete user
    @Test
    public void test17() throws Exception {
        String uri = "/deleteUser/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("User deleted successfully",content );
    }
}
