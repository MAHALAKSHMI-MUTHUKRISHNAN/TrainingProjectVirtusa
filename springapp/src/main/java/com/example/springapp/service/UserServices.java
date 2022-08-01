package com.example.springapp.service;



import com.example.springapp.entity.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserServices {
	public List<Users> getUser();

	public ResponseEntity<Object> addUser(Users user);

    ResponseEntity<Object> editUser(Users user);

	ResponseEntity<Object> deleteUser(long id);
}
