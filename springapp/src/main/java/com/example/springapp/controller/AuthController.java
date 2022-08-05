package com.example.springapp.controller;

import com.example.springapp.config.JwtUtility;
import com.example.springapp.config.SecurityUtils;
import com.example.springapp.dao.UserDao;
import com.example.springapp.dto.UserDto;
import com.example.springapp.entity.Users;
import com.example.springapp.model.JwtRequest;
import com.example.springapp.model.JwtResponse;
import com.example.springapp.service.JwtUserDetailsService;
import com.example.springapp.service.UserServices;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthController {

  Logger logger = LoggerFactory.getLogger(AuthController.class);

  @Autowired
  private UserServices userServices;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  public UserDao dao;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtility jwtTokenUtil;

  @Autowired
  private JwtUserDetailsService userDetailsService;


  @PostMapping(value = "/authenticate")
  public ResponseEntity<Object> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws AuthenticationException {
    logger.info("authenticate method accessed");
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
    final UserDetails userDetails = userDetailsService
        .loadUserByUsername(authenticationRequest.getUsername());
    final String token = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(token));
  }

  @GetMapping(value = "/mydetails")
  public Users getMyDetails() {
    Optional<String> user = SecurityUtils.getCurrentUserLogin();
    Users myUser = new Users();
    if(user.isPresent()) {
      myUser = dao.findByUsername(user.get());

    }
    return myUser;
  }

  @PostMapping(value = "/register")
  public ResponseEntity<Object> addUser(@RequestBody UserDto userDto){
    logger.info("register method accessed");
    Users userRequest = modelMapper.map(userDto,Users.class);
    return this.userServices.addUser(userRequest);
  }

  @GetMapping("/getuser")
  public List<Users> getUser(){
    logger.info("getUser method accessed");
    return this.userServices.getUser();
  }

  @PutMapping("/editUser")
  public ResponseEntity<Object> editUser(@RequestBody UserDto usersDto){
    logger.info("edit user method accessed");
    Users userRequest = modelMapper.map(usersDto,Users.class);
    return this.userServices.editUser(userRequest);
  }

  @DeleteMapping("/deleteUser/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable String id){
    logger.info("delete method accessed");
    return this.userServices.deleteUser(Long.parseLong(id));
  }

}
