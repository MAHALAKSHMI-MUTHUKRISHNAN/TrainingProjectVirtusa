package com.example.springapp.controllerTest;

import com.example.springapp.AbstractTest;
import com.example.springapp.entity.*;
import com.example.springapp.exception.MailNotSendException;
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmailControllerTests extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void test01() throws Exception {
        String uri = "/sendMail";
        EmailDetails emailDetails = new EmailDetails("wordsofdoru@gmail.com","hello","alert");
        String inputJson = super.mapToJson(emailDetails);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Mail sent successfully",content);
    }

    @Test
    public void test02() throws Exception {
        String uri = "/sendMail";
        EmailDetails emailDetails = new EmailDetails("","hello","alert");
        String inputJson = super.mapToJson(emailDetails);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(406, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Sorry"));
    }

    @Test
    public void test03() throws Exception {
        String uri = "/sendMail";
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setSubject("subject");
        emailDetails.setMsgBody("message");
        String inputJson = super.mapToJson(emailDetails);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Null"));
    }
}
