package com.example.springapp.dao;

import com.example.springapp.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBDao extends JpaRepository<FileDB, String> {
}
