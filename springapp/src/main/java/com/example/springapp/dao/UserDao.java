package com.example.springapp.dao;


import com.example.springapp.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<Users,Long>{
    boolean existsUserByEmail(String email);
    boolean existsUserByMobile(String mobile);
    boolean existsUserByUsername(String username);
    Users findByUsername(String username);
    Users findByEmail(String email);


}
