package com.example.springapp.controllerTest;

import com.example.springapp.AbstractTest;
import com.example.springapp.dao.BookingDao;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PaymentControllerTests extends AbstractTest {

    @Autowired
    private BookingDao bookingDao;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    //update initial pay with no payment id found
    @Test
    public void test00() throws Exception {
        String uri = "/updateInitialPay/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Couldn't update Initial pay"));
    }

    //update final pay with no payment id found
    @Test
    public void test01() throws Exception {
        String uri = "/updateFinalPay/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Couldn't update Final pay"));
    }

    //update charges with no booking id found
    @Test
    public void test02() throws Exception {
        String uri = "/updateCharges";
        Payment payment = new Payment(1,false,false,25000);
        String inputJson = super.mapToJson(payment);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("couldn't be updated Booking id mismatch"));
    }

    //update charges with null values
    @Test
    public void test03() throws Exception {
        String uri = "/updateCharges";
        Payment payment = new Payment();
        payment.setBookId(1);
        Booking booking = new Booking();
        booking.setBookId(1);
        bookingDao.save(booking);
        String inputJson = super.mapToJson(payment);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Null"));
    }

    //update charges
    @Test
    public void test04() throws Exception {
        String uri = "/updateCharges";
        Payment payment = new Payment(1,false,false,25000);
        Booking booking = new Booking();
        booking.setBookId(1);
        bookingDao.save(booking);
        String inputJson = super.mapToJson(payment);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Charges"+payment.getCharges()+" updated successfully",content);
    }

    //update initial pay
    @Test
    public void test05() throws Exception {
        String uri = "/updateInitialPay/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Payment Done, Thanks!",content);
    }

    //update final pay
    @Test
    public void test06() throws Exception {
        String uri = "/updateFinalPay/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Payment Done, Thanks!",content);
    }




}
