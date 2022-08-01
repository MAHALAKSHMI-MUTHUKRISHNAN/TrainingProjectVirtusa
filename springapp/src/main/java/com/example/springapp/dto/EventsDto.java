package com.example.springapp.dto;

import com.example.springapp.entity.Booking;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EventsDto {
    private long id;
    private String name;
    private String imageurl;
    private int priceRange;
    private String details;
    List<Booking> bookings = new ArrayList<>();
}
