package com.example.springapp.service;

import com.example.springapp.config.SecurityUtils;
import com.example.springapp.dao.BookingDao;
import com.example.springapp.dao.EventsDao;
import com.example.springapp.dao.UserDao;
import com.example.springapp.entity.Booking;
import com.example.springapp.entity.Events;
import com.example.springapp.entity.Payment;
import com.example.springapp.entity.Users;
import com.example.springapp.exception.DateBookedException;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceImplTest {

    @Mock
    private BookingDao bookingDao;

    @Mock
    private EventsDao eventsDao;

    @Mock
    private UserDao userDao;


    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    public void testViewAllBookings(){
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        bookings.add(new Booking());
        given(bookingDao.findAll()).willReturn(bookings);
        List<Booking> expected = bookingService.allBookings();
        assertEquals(expected,bookings);
        assertEquals(2,expected.size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testViewAllEventsIfNoEvents(){
        List<Booking> bookings = new ArrayList<>();
        given(bookingDao.findAll()).willReturn(bookings);
        bookingService.allBookings();
    }

    @Test
    public void testAddBookings() {
        Booking book1 = new Booking(1,1,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        Events events = new Events();
        events.setId(1);
        List<Events> allEvents = new ArrayList<>();
        allEvents.add(events);
        Optional<Users> user = Optional.of(new Users());
        user.get().setId(1);
        given(eventsDao.findAll()).willReturn(allEvents);
        given(userDao.findById(book1.getUId())).willReturn(user);
        ResponseEntity<Object> created = bookingService.addBooking(book1);
        assertEquals(HttpStatus.OK,created.getStatusCode() );
    }

    @Test(expected = DateBookedException.class)
    public void testAddBookingsWithSameDate() {
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        Booking book2 = new Booking(2,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        List<Booking> allBookings = new ArrayList<>();
        allBookings.add(book1);
        Events events = new Events();
        events.setId(1);
        List<Events> allEvents = new ArrayList<>();
        allEvents.add(events);
        Users user = new Users();
        user.setId(1);
        List<Users> allUsers = new ArrayList<>();
        allUsers.add(user);
        given(bookingDao.findAll()).willReturn(allBookings);
        ResponseEntity<Object> created = bookingService.addBooking(book2);
    }

    @Test(expected = NullPointerException.class)
    public void testAddBookingsWithNullValue(){
        Booking booking = new Booking();
        Events events = new Events();
        events.setId(1);
        List<Events> allEvents = new ArrayList<>();
        allEvents.add(events);
        Users user = new Users();
        user.setId(1);
        List<Users> allUsers = new ArrayList<>();
        allUsers.add(user);
        ResponseEntity<Object> created = bookingService.addBooking(booking);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void testAddBookingsIfNoUsersFound() {
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        Events events = new Events();
        events.setId(1);
        List<Events> allEvents = new ArrayList<>();
        allEvents.add(events);
        given(eventsDao.findAll()).willReturn(allEvents);
        given(userDao.findById(anyLong())).willReturn(java.util.Optional.ofNullable(null));
        ResponseEntity<Object> created = bookingService.addBooking(book1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testAddBookingsIfUserIdNotFound() {
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        Events events = new Events();
        events.setId(1);
        List<Events> allEvents = new ArrayList<>();
        allEvents.add(events);
        Optional<Users> user = Optional.of(new Users());
        user.get().setId(1);
        given(eventsDao.findAll()).willReturn(allEvents);
        given(userDao.findById(book1.getUId())).willReturn(java.util.Optional.ofNullable(null));
        ResponseEntity<Object> created = bookingService.addBooking(book1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testAddBookingsIfNoEventsFound() {
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        List<Events> allEvents = new ArrayList<>();
        Users user = new Users();
        user.setId(1);
        List<Users> allUsers = new ArrayList<>();
        allUsers.add(user);
        given(eventsDao.findAll()).willReturn(allEvents);
        ResponseEntity<Object> created = bookingService.addBooking(book1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testAddBookingsIfEventIdNotFound() {
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        Events events = new Events();
        events.setId(2);
        List<Events> allEvents = new ArrayList<>();
        allEvents.add(events);
        Users user = new Users();
        user.setId(1);
        List<Users> allUsers = new ArrayList<>();
        allUsers.add(user);
        given(eventsDao.findAll()).willReturn(allEvents);
        bookingService.addBooking(book1);
    }

    @Test
    public void testUpdateBookingIfBookingExists(){
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        Booking book2 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Guindy","http://fhgshf","19.08",250,"pending",new Payment());
        given(bookingDao.findById(book2.getBookId())).willReturn(java.util.Optional.of(book1));
        ResponseEntity<Object> created = bookingService.editBooking(book2);
        assertEquals(HttpStatus.OK,created.getStatusCode());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateBookingIfBookingDoesntExists(){
       Booking book2 = new Booking(2,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Guindy","http://fhgshf","19.08",250,"pending",new Payment());
        given(bookingDao.findById(book2.getBookId())).willReturn(java.util.Optional.ofNullable(null));
        ResponseEntity<Object> created = bookingService.editBooking(book2);
    }

    @Test(expected = DateBookedException.class)
    public void testUpdateBookingIfDatexists(){
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        List<Booking> allBooking = new ArrayList<>();
        allBooking.add(book1);
        Booking book2 = new Booking(2,2,1,"maha","8678999537", LocalDate.of(2022,9,11),"Guindy","http://fhgshf","19.08",250,"pending",new Payment());
        allBooking.add(book2);
        Booking book3 = new Booking(2,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        given(bookingDao.findAll()).willReturn(allBooking);
        given(bookingDao.findById(book3.getBookId())).willReturn(java.util.Optional.of(book2));
        ResponseEntity<Object> created = bookingService.editBooking(book3);
    }

    @Test
    public void testDeleteBookingIfBookingExists(){
        Booking book = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        given(bookingDao.findById(book.getBookId())).willReturn(java.util.Optional.of(book));
        ResponseEntity<Object> created = bookingService.deleteBooking(book.getBookId());
        assertEquals(HttpStatus.OK,created.getStatusCode() );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteBookingIfBookingDoesntExists(){
        Booking book = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        given(bookingDao.findById(book.getBookId())).willReturn(java.util.Optional.ofNullable(null));
        ResponseEntity<Object> created = bookingService.deleteBooking(book.getBookId());
    }

    @Test
    public void testUpdateConfirmStatusIfConfirmed(){
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        Booking book2 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Guindy","http://fhgshf","19.08",250,"Confirmed",new Payment());
        given(bookingDao.findById(book2.getBookId())).willReturn(java.util.Optional.of(book1));
        ResponseEntity<Object> created = bookingService.editConfirmStatus(book2);
        assertEquals(HttpStatus.OK,created.getStatusCode());

    }

    @Test
    public void testUpdateConfirmStatusIfRejected(){
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        Booking book2 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Guindy","http://fhgshf","19.08",250,"Rejected",new Payment());
        given(bookingDao.findById(book2.getBookId())).willReturn(java.util.Optional.of(book1));
        ResponseEntity<Object> created = bookingService.editConfirmStatus(book2);
        assertEquals(HttpStatus.OK,created.getStatusCode());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateConfirmStatusIfBookingNotFound(){
        Booking book2 = new Booking(2,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Guindy","http://fhgshf","19.08",250,"Rejected",new Payment());
        given(bookingDao.findById(book2.getBookId())).willReturn(java.util.Optional.ofNullable(null));
        ResponseEntity<Object> created = bookingService.editConfirmStatus(book2);

    }

    @Test(expected = DateBookedException.class)
    public void testUpdateConfirmStatusIfDateAlreadyBooked(){
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        List<Booking> allBooking = new ArrayList<>();
        allBooking.add(book1);
        Booking book2 = new Booking(2,2,1,"maha","8678999537", LocalDate.of(2022,9,11),"Guindy","http://fhgshf","19.08",250,"pending",new Payment());
        allBooking.add(book2);
        Booking book3 = new Booking(2,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"Confirmed",new Payment());
        given(bookingDao.findAll()).willReturn(allBooking);
        given(bookingDao.findById(book3.getBookId())).willReturn(java.util.Optional.of(book2));
        ResponseEntity<Object> created = bookingService.editConfirmStatus(book3);

    }

    @Test
    public void testSearch(){
        Booking book1 = new Booking(1,2,1,"maha","8678999537", LocalDate.of(2022,8,11),"Chennai","http://fhgshf","19.08",250,"pending",new Payment());
        List<Booking> bookings = new ArrayList<>();
        bookings.add(book1);
        given(bookingDao.searchByName("1")).willReturn(bookings);
        List<Booking> created = bookingService.searchByName("1");
        assertEquals(1,created.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testSearcWithhNoBookingsReturned(){
        List<Booking> bookings = new ArrayList<>();
        given(bookingDao.searchByName("1")).willReturn(bookings);
        List<Booking> created = bookingService.searchByName("1");
    }





}
