package com.example.springapp.controller;

import com.example.springapp.dto.BookingDto;
import com.example.springapp.entity.Booking;
import com.example.springapp.service.BookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@SecurityRequirement(name = "event-docs")
public class BookingController {

    Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BookingService bookingService;

    @PostMapping("/booking")
    public ResponseEntity<Object> addBooking(@RequestBody BookingDto bookingDto){
        logger.info("addBooking method accessed");
        Booking bookingRequest = modelMapper.map(bookingDto,Booking.class);
        return this.bookingService.addBooking(bookingRequest);

    }

    @GetMapping("/getAllBookings")
    public List<Booking> getAllBookings(){
        logger.info("getAllBookings method accessed");
        return this.bookingService.allBookings();
    }

    @GetMapping("/getUserBookings")
    public List<Booking> getUserBookings() {
        logger.info("getUserBooking method accessed");
        return this.bookingService.getUserBookings();
    }

    @PutMapping("/editBooking")
    public ResponseEntity<Object> editBooking(@RequestBody BookingDto bookingDto)  {
        logger.info("editBooking method accessed");
        Booking bookingRequest = modelMapper.map(bookingDto,Booking.class);
        return this.bookingService.editBooking(bookingRequest);
    }

    @PutMapping("/confirmStatus")
    public ResponseEntity<Object> editConfirmStatus(@RequestBody BookingDto bookingDto){
        logger.info("editConfirmStatus method accessed");
        Booking bookingRequest = modelMapper.map(bookingDto,Booking.class);
        return this.bookingService.editConfirmStatus(bookingRequest);
    }

    @DeleteMapping("/deleteBooking/{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable String id){
        logger.info("deleteBooking method accessed");
        return this.bookingService.deleteBooking(Long.parseLong(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Booking>> searchByName(@RequestParam("query") String query){
        logger.info("search method accessed");
        return ResponseEntity.ok(bookingService.searchByName(query));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Booking>> sortByUpcomingEvent(){
        logger.info("sort method accessed");
        return ResponseEntity.ok(bookingService.sortByUpcomingEvent());
    }
}
