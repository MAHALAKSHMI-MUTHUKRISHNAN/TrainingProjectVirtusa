package com.example.springapp.service;

import com.example.springapp.entity.Payment;
import org.springframework.http.ResponseEntity;


public interface PaymentService {

    ResponseEntity<Object> updateCharges(Payment payment);

    ResponseEntity<Object> updateInitialPay(long id);

    ResponseEntity<Object> updateFinalPay(long id);


}
