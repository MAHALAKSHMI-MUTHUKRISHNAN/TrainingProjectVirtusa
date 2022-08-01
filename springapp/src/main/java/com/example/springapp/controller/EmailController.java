package com.example.springapp.controller;

import com.example.springapp.entity.EmailDetails;
import com.example.springapp.service.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@SecurityRequirement(name = "event-docs")
public class EmailController {

    @Autowired
    private EmailService emailService;

    Logger logger = LoggerFactory.getLogger(EmailController.class);

    @PostMapping("/sendMail")
    public ResponseEntity<Object> sendMail(@RequestBody EmailDetails emailDetails){
        logger.info("Mail method accessed");
        return this.emailService.sendSimpleMail(emailDetails);
    }
}
