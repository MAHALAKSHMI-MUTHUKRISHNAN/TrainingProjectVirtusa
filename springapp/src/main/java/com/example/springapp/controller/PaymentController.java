package com.example.springapp.controller;
import com.example.springapp.dto.PaymentDto;
import com.example.springapp.entity.Payment;
import com.example.springapp.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@SecurityRequirement(name = "event-docs")
public class PaymentController {

    Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/updateCharges")
    public ResponseEntity<Object> updateCharges(@RequestBody PaymentDto paymentDto){
        Payment paymentRequest = modelMapper.map(paymentDto,Payment.class);
        logger.info("updateCharges method accessed");
        return paymentService.updateCharges(paymentRequest);
    }

    @PutMapping("/updateInitialPay/{id}")
    public ResponseEntity<Object> updateInitialPay(@PathVariable String id){
        logger.info("updateInitialPay method accessed");
        return paymentService.updateInitialPay(Long.parseLong(id));
    }

    @PutMapping("/updateFinalPay/{id}")
    public ResponseEntity<Object> updateFinalPay(@PathVariable String id){
        logger.info("updateFinalPay method accessed");
        return paymentService.updateFinalPay(Long.parseLong(id));
    }
}
