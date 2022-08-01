package com.example.springapp.service;

import com.example.springapp.entity.EmailDetails;
import org.springframework.http.ResponseEntity;

public interface EmailService {

    ResponseEntity<Object> sendSimpleMail(EmailDetails emailDetails);

}
