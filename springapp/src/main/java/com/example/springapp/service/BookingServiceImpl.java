package com.example.springapp.service;

import com.example.springapp.config.SecurityUtils;
import com.example.springapp.dao.BookingDao;
import com.example.springapp.dao.EventsDao;
import com.example.springapp.dao.UserDao;
import com.example.springapp.entity.Booking;
import com.example.springapp.entity.Events;
import com.example.springapp.entity.Users;
import com.example.springapp.exception.DateBookedException;
import com.example.springapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private EventsDao eventsDao;

    @Autowired
    private UserDao userDao;

    @Override
    public ResponseEntity<Object> addBooking(Booking booking) {
        booking.setConfirmStatus("pending");
        List<Booking> allBookings = bookingDao.findAll();
        for(Booking x:allBookings){
            if(booking.getEventDate().isEqual(x.getEventDate())){
                throw new DateBookedException("Date"+booking.getEventDate()+" already Booked !");
            }
        }
        this.bookingDao.save(booking);

        var eId = booking.getEId();
        var uId = booking.getUId();
        if( eId==0 || uId==0  || booking.getEventTiming()==null|| booking.getEventDate()==null || booking.getLocationUrl()==null||booking.getEventPlace()==null||booking.getMobileNumber()==null) {
            throw new NullPointerException("Null values are not accepted");
        }

        //adding to events
        List<Events> events = this.eventsDao.findAll();
        for(Events x:events){
            if(Objects.equals(x.getId(),booking.getEId())){
                x.getBookings().add(booking);
                this.eventsDao.save(x);
            }
            else{
                throw new ResourceNotFoundException("No event found with id"+booking.getEId());
            }
        }
        if(events.isEmpty()) throw new ResourceNotFoundException("No events found");

        //adding to user
     //   List<Users> users = this.userDao.findAll();
     //   for(Users y:users){
      //      if(Objects.equals(y.getId(),booking.getUId())){
      //          y.getBookings().add(booking);
    //            this.userDao.save(y);
      //      }else {
      //          throw new ResourceNotFoundException("No user found with id"+y.getId());
      //      }
        //}
        Optional<Users> user = userDao.findById(booking.getUId());
        if(user.isPresent()){
          user.get().getBookings().add(booking);
           userDao.save(user.get());
        }

        return new ResponseEntity<>("Booking done successfully", HttpStatus.OK);
    }

    @Override
    public List<Booking> allBookings() {
        List<Booking> allBookings = bookingDao.findAll();
        if(allBookings.isEmpty()) throw new ResourceNotFoundException("No Bookings Available");
        return this.bookingDao.findAll();
    }

    @Override
    public List<Booking> getUserBookings() {
        Optional<String> user = SecurityUtils.getCurrentUserLogin();
        List<Booking> bookings = new ArrayList<>();
        if(user.isPresent()) {
            Users userbook = userDao.findByUsername(user.get());
            List<Booking> userBookings = userbook.getBookings();
            for(Booking x:userBookings){
                bookings.add(x);
            }
        }
        if(bookings.isEmpty()) throw new ResourceNotFoundException("No Bookings Available for this user");
        return bookings;
    }

    @Override
    public ResponseEntity<Object> editBooking(Booking booking) {
        if(!(bookingDao.findById(booking.getBookId()).isPresent())) throw new ResourceNotFoundException("Couldn't edit : Invalid Boooking id"+booking.getBookId());
        booking.setConfirmStatus("pending");

        List<Booking> allBookings = bookingDao.findAll();
        for(Booking x:allBookings){
            if((booking.getBookId()!=x.getBookId()) && booking.getEventDate().isEqual(x.getEventDate())){
                throw new DateBookedException("Date"+booking.getEventDate()+" not available");
            }
        }
        this.bookingDao.save(booking);
        return new ResponseEntity<>("Booking updated successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteBooking(long id) {
        Optional<Booking> deleteBook = bookingDao.findById(id);
        if(!deleteBook.isPresent()) throw  new ResourceNotFoundException("Couldn't delete: Invalid Boooking id"+id);

        this.bookingDao.delete(deleteBook.get());
        return new ResponseEntity<>("Booking deleted successfully", HttpStatus.OK);
    }

    @Override
    public List<Booking> searchByName(String query) {
        List<Booking> search = bookingDao.searchByName(query);
        if(search.isEmpty())
            throw new NoSuchElementException("No search item");
        return search;
    }

    @Override
    public List<Booking> sortByUpcomingEvent() {
        return bookingDao.sortByUpcomingEvent();

    }

    @Override
    public ResponseEntity<Object> editConfirmStatus(Booking booking) {
        if(!(bookingDao.findById(booking.getBookId()).isPresent())) throw new ResourceNotFoundException("Invalid Boooking id"+booking.getBookId());
        if(booking.getConfirmStatus().equals("Confirmed")) {
            List<Booking> allBookings = bookingDao.findAll();
            for (Booking x : allBookings) {
                if ((booking.getBookId()!=x.getBookId()) && booking.getEventDate().isEqual(x.getEventDate())) {
                    throw new DateBookedException("You have another event on" + booking.getEventDate() + ". Choose another!");
                }
                else{
                    bookingDao.save(booking);
                }
            }
        }
        if(booking.getConfirmStatus().equals("Rejected")){
            bookingDao.save(booking);
        }

        return new ResponseEntity<>("Confirm Status updated", HttpStatus.OK);

    }


}
