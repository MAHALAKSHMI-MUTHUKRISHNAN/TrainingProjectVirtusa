package com.example.springapp.service;

import com.example.springapp.entity.EmailDetails;
import com.example.springapp.exception.MailNotSendException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public ResponseEntity<Object> sendSimpleMail(EmailDetails emailDetails) {

        if(emailDetails.getRecipient() == null) throw new NullPointerException("Recipient found to be null");
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setSubject(emailDetails.getSubject());
            mailMessage.setText(emailDetails.getMsgBody());
            mailSender.send(mailMessage);
            return new ResponseEntity<>("Mail sent successfully", HttpStatus.OK);
        }
        catch (Exception e){
            throw new MailNotSendException("Sorry, couldn't send mail to you!");
        }

    }
}
