package com.example.springapp.service;


import com.example.springapp.entity.Booking;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {

    ResponseEntity<Object> addBooking(Booking booking);

    List<Booking> allBookings();
    List<Booking> getUserBookings();
    ResponseEntity<Object> editBooking(Booking booking);
    ResponseEntity<Object> deleteBooking(long id);
    List<Booking> searchByName(String query);
    List<Booking> sortByUpcomingEvent();
    ResponseEntity<Object> editConfirmStatus(Booking booking);


}
