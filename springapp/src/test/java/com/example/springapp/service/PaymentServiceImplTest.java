package com.example.springapp.service;

import com.example.springapp.dao.BookingDao;
import com.example.springapp.dao.PaymentDao;
import com.example.springapp.entity.Booking;
import com.example.springapp.entity.Payment;
import com.example.springapp.exception.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceImplTest {

    @Mock
    private PaymentDao paymentDao;

    @Mock
    private BookingDao bookingDao;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test(expected = NullPointerException.class)
    public void updateChargesWithNullValue(){
        Payment payment = new Payment();
        ResponseEntity<Object> created = paymentService.updateCharges(payment);
    }

    @Test
    public void updateCharges(){
        Payment payment = new Payment(1,false,false,25000);
        Booking book1 = new Booking();
        book1.setBookId(1);
        List<Booking> allbookings = new ArrayList<>();
        allbookings.add(book1);
        given(bookingDao.findById(payment.getBookId())).willReturn(java.util.Optional.of(book1));
        ResponseEntity<Object> created = paymentService.updateCharges(payment);
        assertEquals(HttpStatus.OK,created.getStatusCode() );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateChargesIfBookIdMismatch(){
        Payment payment = new Payment(1,false,false,25000);
        given(bookingDao.findById(payment.getBookId())).willReturn(java.util.Optional.ofNullable(null));
        ResponseEntity<Object> created = paymentService.updateCharges(payment);
    }

    @Test
    public void updateInitialPay(){
        Payment payment = new Payment(1,false,false,25000);

        given(paymentDao.findById(payment.getBookId())).willReturn(java.util.Optional.of(payment));
        ResponseEntity<Object> created = paymentService.updateInitialPay(payment.getBookId());
        assertEquals(HttpStatus.OK,created.getStatusCode() );
    }

    @Test
    public void updateFinalPay(){
        Payment payment = new Payment(1,false,false,25000);

        given(paymentDao.findById(payment.getBookId())).willReturn(java.util.Optional.of(payment));
        ResponseEntity<Object> created = paymentService.updateFinalPay(payment.getBookId());
        assertEquals(HttpStatus.OK,created.getStatusCode() );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateFinalPayIfPaymentNotExists(){
        given(paymentDao.findById(anyLong())).willReturn(java.util.Optional.ofNullable(null));
        ResponseEntity<Object> created = paymentService.updateFinalPay(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateInitialPayIfPaymentNotExists(){
        given(paymentDao.findById(anyLong())).willReturn(java.util.Optional.ofNullable(null));
        ResponseEntity<Object> created = paymentService.updateInitialPay(1L);
    }



}
