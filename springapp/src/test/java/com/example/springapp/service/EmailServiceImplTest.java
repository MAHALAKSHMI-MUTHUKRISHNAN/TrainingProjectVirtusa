package com.example.springapp.service;

import com.example.springapp.entity.EmailDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    public void testSendMail(){
        EmailDetails emailDetails = new EmailDetails("maha@gmail.com","hello","alert");
        ResponseEntity<Object> expected = emailService.sendSimpleMail(emailDetails);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailSender.send(mailMessage);
        assertEquals(HttpStatus.OK,expected.getStatusCode());
    }

    @Test(expected = NullPointerException.class)
    public void testSendMailIfNull(){
        EmailDetails emailDetails = new EmailDetails();
        emailService.sendSimpleMail(emailDetails);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailSender.send(mailMessage);
    }
}
