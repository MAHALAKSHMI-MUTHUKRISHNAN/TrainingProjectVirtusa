package com.example.springapp.service;

import com.example.springapp.dao.BookingDao;
import com.example.springapp.dao.PaymentDao;
import com.example.springapp.entity.Payment;
import com.example.springapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private BookingDao bookingDao;

    @Override
    public ResponseEntity<Object> updateCharges(Payment payment) {
        var charges = payment.getCharges();
        if(charges==0) throw new NullPointerException("Charges cannot be null");
        if(bookingDao.findById(payment.getBookId()).isPresent()) {
            payment.setInitialPay(false);
            payment.setFinalPay(false);
            this.paymentDao.save(payment);
        }else throw new ResourceNotFoundException("Charges couldn't be updated Booking id mismatch");
        return new ResponseEntity<>("Charges"+payment.getCharges()+" updated successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateInitialPay(long id) {
        Optional<Payment> updatePay = paymentDao.findById(id);
        if(!updatePay.isPresent()) throw  new ResourceNotFoundException("Couldn't update Initial pay"+id);
        Payment pay = updatePay.get();
        pay.setInitialPay(true);
        this.paymentDao.save(pay);
        return new ResponseEntity<>("Payment Done, Thanks!",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateFinalPay(long id) {
        Optional<Payment> updatePay = paymentDao.findById(id);
        if(!updatePay.isPresent()) throw  new ResourceNotFoundException("Couldn't update Final pay"+id);
        Payment pay = updatePay.get();
        pay.setFinalPay(true);
        this.paymentDao.save(pay);
        return new ResponseEntity<>("Payment Done, Thanks!",HttpStatus.OK);
    }


}
