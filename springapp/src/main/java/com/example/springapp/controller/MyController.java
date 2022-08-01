package com.example.springapp.controller;

import com.example.springapp.dto.UserDto;
import com.example.springapp.entity.Users;
import com.example.springapp.service.UserServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@SecurityRequirement(name = "event-docs")
public class MyController {


	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserServices userServices;
	
	@GetMapping("/getuser")
	public List<Users> getUser(){
		return this.userServices.getUser();
	}

	@GetMapping("/getOnlyUser")
	public List<Users> getOnlyUser(){
		List<Users> allUser = getUser();
		List<Users> onlyUser = new ArrayList<>();
		for(Users u:allUser){
			if(u.getRole().equals("admin")){
				continue;
			}
			onlyUser.add(u);
		}
		return onlyUser;
	}

	
	@PutMapping("/editUser")
	public ResponseEntity<Object> editUser(@RequestBody UserDto usersDto){
		Users userRequest = modelMapper.map(usersDto,Users.class);
		return this.userServices.editUser(userRequest);
	}

	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable String id){

		return this.userServices.deleteUser(Long.parseLong(id));
	}

}
