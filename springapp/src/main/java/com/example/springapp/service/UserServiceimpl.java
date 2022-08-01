package com.example.springapp.service;



import com.example.springapp.dao.UserDao;
import com.example.springapp.entity.Users;
import com.example.springapp.exception.ResourceNotFoundException;
import com.example.springapp.exception.ValueExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceimpl implements com.example.springapp.service.UserServices {
	List<Users> list;


	@Autowired
	public UserDao dao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public List<Users> getUser() {
		List<Users> allUsers = dao.findAll();
		if(allUsers.isEmpty()) throw new ResourceNotFoundException("No Users Available");
		return this.dao.findAll();
	}
	@Override
	public ResponseEntity<Object> addUser(Users user) {
		if(user.getUsername() == null || user.getEmail() == null || user.getMobile() == null || user.getPassword()==null || user.getName()== null){
			throw new NullPointerException();
		}
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		if(user.getName().equals("admin")){
			user.setRole("admin");
		}else{
			user.setRole("user");
		}

		boolean emailAlreadyExists = dao.existsUserByEmail(user.getEmail());
		boolean userNameAlreadyExists = dao.existsUserByUsername(user.getUsername());
        boolean mobileAlreadyExists = dao.existsUserByMobile(user.getMobile());

        if (emailAlreadyExists) {
            throw new ValueExistsException("Email "+user.getEmail()+" already Exists" );
        }
        if (mobileAlreadyExists) {
            throw new ValueExistsException("Mobile Number "+user.getMobile()+" already Exists");
        }
		if (userNameAlreadyExists){
			throw new ValueExistsException("Username "+user.getUsername()+" already Exists, Choose unique one!");
		}

		dao.save(user);
		return new ResponseEntity<>("Welcome "+user.getName()+", you've registered successfully", HttpStatus.OK);

	}

	@Override
	public ResponseEntity<Object> editUser(Users user) {
		Optional<Users> old = dao.findById(user.getId());
		if(!(old).isPresent()) {
			throw new ResourceNotFoundException("Couldn't edit : Invalid user");
		}
		if(!(old.get().getUsername().equals(user.getUsername()))) throw new ValueExistsException("Username Cannot be changed");
		if(!(old.get().getEmail().equals(user.getEmail()))) throw new ValueExistsException("Email Cannot be changed");
		if(!(old.get().getMobile().equals(user.getMobile()))) throw new ValueExistsException("Mobile number Cannot be changed");
		this.dao.save(user);
		return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> deleteUser(long id) {
		Optional<Users> deleteUser = dao.findById(id);
		if(!deleteUser.isPresent()) throw  new ResourceNotFoundException("Couldn't delete: Invalid user");
		this.dao.delete(deleteUser.get());
		return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
	}

}
