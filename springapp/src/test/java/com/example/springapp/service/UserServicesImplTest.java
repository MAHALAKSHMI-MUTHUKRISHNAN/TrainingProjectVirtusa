package com.example.springapp.service;

import com.example.springapp.dao.UserDao;
import com.example.springapp.entity.Booking;
import com.example.springapp.entity.Users;
import com.example.springapp.exception.ResourceNotFoundException;
import com.example.springapp.exception.ValueExistsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServicesImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceimpl userService;


    @Test
    public void testAddUsers() {
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(1,"user","maha","maha","8768766788","maha@gmail.com","test1234",bookings);
        given(userDao.existsUserByEmail(users.getEmail())).willReturn(false);
        given(userDao.existsUserByUsername(users.getUsername())).willReturn(false);
        given(userDao.existsUserByMobile(users.getMobile())).willReturn(false);
        given(passwordEncoder.encode(users.getPassword())).willReturn("encodedpassword");
        ResponseEntity<Object> created = userService.addUser(users);
        assertEquals(HttpStatus.OK,created.getStatusCode());
    }

    @Test(expected = ValueExistsException.class)
    public void testAddUsersWithSameEmail(){
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(1,"user","maha","maha","8768766788","maha@gmail.com","test1234",bookings);
        given(userDao.existsUserByEmail(users.getEmail())).willReturn(true);
        given(passwordEncoder.encode(users.getPassword())).willReturn("encodedpassword");
        userService.addUser(users);
    }

    @Test(expected = ValueExistsException.class)
    public void testAddUsersWithSameUsername(){
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(1,"user","maha","maha","8768766788","maha@gmail.com","test1234",bookings);
        given(userDao.existsUserByEmail(users.getEmail())).willReturn(false);
        given(userDao.existsUserByUsername(users.getUsername())).willReturn(true);
        given(userDao.existsUserByMobile(users.getMobile())).willReturn(false);
        given(passwordEncoder.encode(users.getPassword())).willReturn("encodedpassword");
        userService.addUser(users);
    }

    @Test(expected = ValueExistsException.class)
    public void testAddUsersWithSameMobile(){
        List<Booking> bookings = new ArrayList<>();
        Users users = new Users(1,"user","maha","maha","8768766788","maha@gmail.com","test1234",bookings);
        given(userDao.existsUserByEmail(users.getEmail())).willReturn(false);
        given(userDao.existsUserByUsername(users.getUsername())).willReturn(false);
        given(userDao.existsUserByMobile(users.getMobile())).willReturn(true);
        given(passwordEncoder.encode(users.getPassword())).willReturn("encodedpassword");
        userService.addUser(users);
    }

    @Test(expected = NullPointerException.class)
    public void testAddUsersWithNullValues() {
        Users users = new Users();
        users.setId(1);
        userService.addUser(users);
    }

    @Test
    public void testDeleteUserIfExists(){
        List<Booking> allBookings = new ArrayList<>();
        Users users = new Users(1,"user","maha","maha","8768766788","maha@gmail.com","test1234",allBookings);
        given(userDao.findById(users.getId())).willReturn(java.util.Optional.of(users));
        ResponseEntity<Object> created = userService.deleteUser(users.getId());
        assertEquals(HttpStatus.OK,created.getStatusCode() );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteEventsIfEventDoesntExists(){
        List<Booking> allBookings = new ArrayList<>();
        Users users = new Users(1,"user","maha","maha","8768766788","maha@gmail.com","test1234",allBookings);
        given(userDao.findById(anyLong())).willReturn(java.util.Optional.ofNullable(null));
        userService.deleteUser(users.getId());
    }

    @Test
    public void testUpdateUserIfExists(){
        List<Booking> allBookings = new ArrayList<>();
        Users users1 = new Users(1,"user","maha","maha","8768766788","maha@gmail.com","test1234",allBookings);
        Users users2 = new Users(1,"user","mahalakshmi","maha","8768766788","maha@gmail.com","test1234",allBookings);
        given(userDao.findById(users2.getId())).willReturn(java.util.Optional.of(users1));
        ResponseEntity<Object> created = userService.editUser(users2);
        assertEquals(HttpStatus.OK,created.getStatusCode());
    }

    @Test(expected = ValueExistsException.class)
    public void testUpdateEventsIfUsernameNotSame(){
        List<Booking> allBookings = new ArrayList<>();
        Users users1 = new Users(1,"user","maha","maha","8768766788","maha@gmail.com","test1234",allBookings);
        Users users2 = new Users(1,"user","mahalakshmi","mahalak","8768766788","maha@gmail.com","test",allBookings);
        given(userDao.findById(users2.getId())).willReturn(java.util.Optional.of(users1));
        userService.editUser(users2);
    }

    @Test(expected = ValueExistsException.class)
    public void testUpdateEventsIfMobileNotSame(){
        List<Booking> allBookings = new ArrayList<>();
        Users users1 = new Users(1,"user","maha","maha","8768766788","maha@gmail.com","test1234",allBookings);
        Users users2 = new Users(2,"user","mahalakshmi","maha","8768768988","mahalad@gmail.com","test1234",allBookings);
        Users users3 = new Users(1,"user","abi","maha","8768768988","maha@gmail.com","test1234",allBookings);
        List<Users> allUsers = new ArrayList<>();
        allUsers.add(users1);
        allUsers.add(users2);
        given(userDao.findById(users3.getId())).willReturn(java.util.Optional.of(users1));
        given(userDao.findAll()).willReturn(allUsers);
        userService.editUser(users3);
    }

    @Test(expected = ValueExistsException.class)
    public void testUpdateEventsIfEmailNotSame(){
        List<Booking> allBookings = new ArrayList<>();
        Users users1 = new Users(1,"user","maha","maha","8768766788","maha@gmail.com","test1234",allBookings);
        Users users2 = new Users(2,"user","mahalakshmi","maha","8768768988","mahalad@gmail.com","test1234",allBookings);
        Users users3 = new Users(1,"user","abi","maha","8768766788","mahalad@gmail.com","test1234",allBookings);
        List<Users> allUsers = new ArrayList<>();
        allUsers.add(users1);
        allUsers.add(users2);
        given(userDao.findById(users3.getId())).willReturn(java.util.Optional.of(users1));
        given(userDao.findAll()).willReturn(allUsers);
        userService.editUser(users3);
    }
}
