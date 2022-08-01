package com.example.springapp.entity;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {

    @Id
    private long bookId;
    private Boolean initialPay;
    private Boolean finalPay;
    private int charges;

}
