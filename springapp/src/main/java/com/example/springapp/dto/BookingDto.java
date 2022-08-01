package com.example.springapp.dto;
import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingDto {
    private long bookId;
    private long uId;
    private long eId;
    private String name;
    private String mobileNumber;
    private LocalDate eventDate;
    private String eventPlace;
    private String locationUrl;
    private String eventTiming;
    private int peopleCount;

    private String confirmStatus;
}
