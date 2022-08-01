package com.example.springapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "bookId")
    private long bookId;

    @Transient
    private long uId;

    @Transient
    private long eId;

    private String name;
    private String mobileNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;
    private String eventPlace;
    private String locationUrl;
    private String eventTiming;
    private int peopleCount;

    private String confirmStatus;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name="bookId")
    private Payment payment;

}
