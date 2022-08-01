package com.example.springapp.dao;

import com.example.springapp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingDao extends JpaRepository<Booking,Long> {

    @Query("SELECT b FROM Booking b WHERE " + "b.name LIKE CONCAT('%',:query,'%')" + "Or b.confirmStatus LIKE CONCAT('%', :query, '%')" + "Or b.bookId LIKE CONCAT('%', :query, '%')")
    List<Booking> searchByName(String query);

    @Query("SELECT b FROM Booking b ORDER BY b.eventDate, b.eventTiming, b.id")
    List<Booking> sortByUpcomingEvent();


}
