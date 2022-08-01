package com.example.springapp.dao;

import com.example.springapp.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsDao extends JpaRepository<Events,Long> {
}
